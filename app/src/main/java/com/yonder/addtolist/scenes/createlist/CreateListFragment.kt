package com.yonder.addtolist.scenes.createlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.extensions.showToastMessage
import com.yonder.addtolist.common.utils.decider.ColorDecider
import com.yonder.addtolist.theme.padding_16
import com.yonder.addtolist.theme.padding_8
import com.yonder.uicomponent.compose.ColorPicker
import com.yonder.uicomponent.compose.HorizontalChipView
import com.yonder.uicomponent.compose.LoadingView
import com.yonder.uicomponent.compose.buttons.SubmitButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject



@ExperimentalMaterialApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class CreateListFragment : Fragment() {

    val viewModel: CreateListViewModel by viewModels()

    @Inject
    lateinit var colorDecider: ColorDecider

    private val listNames get() = requireContext().resources.getStringArray(R.array.list_names)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MainContent()
            }
        }
    }

    @Composable
    fun MainContent() {
        val state by viewModel.uiState.collectAsState()
        val event by viewModel.effect.collectAsState(initial = CreateListViewModel.UiEvent.Initial)

        val textState = remember { mutableStateOf(TextFieldValue()) }
        val selectedColorIndex: MutableState<Int> = remember { mutableStateOf(0) }

        when (event) {
            is CreateListViewModel.UiEvent.ListCreated -> {
                findNavController().popBackStack()
            }
            is CreateListViewModel.UiEvent.Error -> {
                context?.showToastMessage((event as CreateListViewModel.UiEvent.Error).errorMessage)
            }
        }

        if (state.isLoading) {
            LoadingView()
        } else {
            Column {
                TextField(
                    value = textState.value,
                    textStyle = MaterialTheme.typography.body1,
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
                        ),
                    placeholder = {
                        Text(text = stringResource(id = R.string.list_name))
                    }
                )
                if (state.shouldShowBlankListNameError) {
                    Text(
                        text = stringResource(id = R.string.list_name_should_not_be_empty),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = padding_16)
                    )
                }

                HorizontalChipView(
                    listNames = listNames,
                    modifier = Modifier.padding(horizontal = padding_8),
                    onClickListItem = { listName ->
                        textState.value = TextFieldValue(listName)
                    }
                )

                ColorPickerBorderView(
                    listColors = ListProvider.listColors,
                    selectedIndex = selectedColorIndex.value,
                    onClickColorIndex = {
                        selectedColorIndex.value = it
                    }
                )

                SubmitButton(textResId = R.string.create, onClick = {
                    viewModel.createList(
                        listName = textState.value.text,
                        listColorName = ListProvider.listNames[selectedColorIndex.value]
                    )
                })

            }
        }
    }

    @Composable
    fun ColorPickerBorderView(
        listColors: List<Int>,
        onClickColorIndex: (Int) -> Unit,
        selectedIndex: Int? = null
    ) {
        Surface(
            modifier = Modifier.padding(padding_8),
            elevation = 0.dp,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                width = 1.dp,
                color = colorResource(R.color.gray_100)
            )
        ) {
            ColorPicker(
                listColors = listColors,
                modifier = Modifier.padding(padding_8),
                onClickColor = onClickColorIndex,
                selectedIndex = selectedIndex
            )
        }
    }

}
