package com.yonder.addtolist.scenes.createlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yonder.addtolist.R
import com.yonder.addtolist.common.enums.AppColor
import com.yonder.addtolist.common.ui.extensions.showToastMessage
import com.yonder.addtolist.scenes.activity.Route
import com.yonder.addtolist.scenes.activity.Screen
import com.yonder.addtolist.scenes.splash.SplashViewModel
import com.yonder.addtolist.theme.padding_16
import com.yonder.addtolist.theme.padding_8
import com.yonder.addtolist.uicomponent.ColorPicker
import com.yonder.addtolist.uicomponent.HorizontalChipView
import com.yonder.addtolist.uicomponent.LoadingView
import com.yonder.addtolist.uicomponent.SubmitButton
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateNewList(navController: NavController) {

    val context = LocalContext.current
    val resources = context.resources

    val viewModel = hiltViewModel<CreateListViewModel>()

    val state by viewModel.uiState.collectAsState()

    val textState = remember { mutableStateOf(TextFieldValue()) }
    val selectedColorIndex: MutableState<Int> = remember { mutableStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        viewModel.eventsFlow.collectLatest { event ->
            when(event) {
                CreateListViewModel.Event.PopBackStack -> {
                    navController.popBackStack(route = Route.LIST.key, inclusive = false)
                }
                is CreateListViewModel.Event.Message -> {
                    context.showToastMessage(event.message)
                }
            }
        }
    }

    if (state.isLoading) {
        LoadingView()
    } else {
        Column(
            Modifier
                .background(
                    colorResource(id = R.color.gray_50)
                )
        ) {
            TextField(
                value = textState.value,
                textStyle = MaterialTheme.typography.bodyMedium,
                isError = state.shouldShowBlankListNameError,
                onValueChange = {
                    textState.value = it
                    viewModel.setBlankListState(it.text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = padding_16)
                    .padding(
                        top = padding_16,
                        bottom = padding_8
                    )
                    .background(color = colorResource(id = R.color.white)),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.list_name),
                        color = colorResource(id = R.color.primaryTextColor)
                    )
                }
            )
            if (state.shouldShowBlankListNameError) {
                Text(
                    text = stringResource(id = R.string.list_name_should_not_be_empty),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = padding_16)
                )
            }

            HorizontalChipView(
                listNames = resources.getStringArray(R.array.list_names),
                modifier = Modifier.padding(horizontal = padding_8),
                onClickListItem = { listName ->
                    textState.value = TextFieldValue(listName)
                }
            )

            ColorPickerBorderView(
                listColors = AppColor.values().map { it.colorResId },
                selectedIndex = selectedColorIndex.value,
                onClickColorIndex = {
                    selectedColorIndex.value = it
                }
            )

            SubmitButton(textResId = R.string.create, onClick = {
                viewModel.createList(
                    listName = textState.value.text,
                    listColorName = AppColor.values()
                        .map { it.colorName }[selectedColorIndex.value]
                )
            })
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColorPickerBorderView(
    listColors: List<Int>,
    onClickColorIndex: (Int) -> Unit,
    selectedIndex: Int? = null
) {
    Surface(
        modifier = Modifier.padding(padding_8),
        shadowElevation = 0.dp,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(R.color.gray_100)
        )
    ) {
        ColorPicker(
            listColors = listColors,
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.white),
                    shape = RectangleShape
                )
                .padding(padding_8),
            onClickColor = onClickColorIndex,
            selectedIndex = selectedIndex
        )
    }
}