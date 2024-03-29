package com.yonder.addtolist.scenes.listdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.yonder.addtolist.R
import com.yonder.addtolist.common.enums.AppColor
import com.yonder.addtolist.common.utils.OnLifecycleEvent
import com.yonder.addtolist.core.extensions.EMPTY_STRING
import com.yonder.addtolist.scenes.activity.HomeViewModel
import com.yonder.addtolist.scenes.activity.Screen
import com.yonder.addtolist.scenes.listdetail.row.ProductRow
import com.yonder.addtolist.uicomponent.ThinDivider
import com.yonder.addtolist.scenes.listdetail.row.UserListProductRow
import com.yonder.addtolist.scenes.productdetail.ProductDetail
import com.yonder.addtolist.theme.padding_16
import com.yonder.addtolist.theme.padding_8
import com.yonder.addtolist.uicomponent.LoadingView
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ListDetailScreen(homeViewModel: HomeViewModel, navController: NavController, listUUID: String) {
    val viewModel = hiltViewModel<ListDetailViewModel>()

    val state by viewModel.uiState.collectAsState()
    val event by viewModel.effect.collectAsState(initial = ListDetailViewModel.UiEvent.Initial)

    val showPrediction = remember { mutableStateOf(false) }
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val focusRequester = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    when (event) {
        is ListDetailViewModel.UiEvent.ShowKeyboard -> {
            DisposableEffect(Unit) {
                focusRequester.requestFocus()
                onDispose { }
            }
        }
    }

    OnLifecycleEvent { _, _event ->
        when (_event) {
            Lifecycle.Event.ON_START -> {
                viewModel.setSelectedList(listUUID)
                viewModel.fetchProducts(listUUID)
                homeViewModel.getSelectedList(listUUID)
            }
            else -> Unit
        }
    }

    when {
        state.isLoading -> {
            LoadingView()
        }
        state.userList != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color =
                        colorResource(
                            id = R.color.white
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(id = state.userList?.appColor?.colorResId ?: AppColor.Blue.colorResId),
                            containerColor = colorResource(id = state.userList?.appColor?.colorResId ?: AppColor.Blue.colorResId).copy(alpha = 0.2f),
                        ),
                        value = textState.value,
                        interactionSource = interactionSource,
                        maxLines = 1,
                        onValueChange = { textField ->
                            textState.value = textField
                            showPrediction.value = textField.text.isNotBlank()
                            viewModel.fetchProducts(
                                listUUID = listUUID,
                                query = textField.text
                            )
                        },
                        modifier = Modifier
                            .then(
                                if (showPrediction.value) {
                                    Modifier.wrapContentWidth()
                                } else {
                                    Modifier.fillMaxWidth()
                                }
                            )
                            .padding(horizontal = padding_16)
                            .padding(
                                top = padding_16,
                                bottom = padding_8
                            )
                            .background(color = colorResource(id = R.color.white))
                            .focusRequester(focusRequester)
                            .onFocusChanged {
                                if (isFocused) {
                                    keyboardController?.show()
                                }
                            },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.what_would_you_like_to_buy),
                                color = colorResource(id = R.color.primaryTextColor)
                            )
                        }
                    )
                    if (showPrediction.value) {
                        TextButton(
                            modifier = Modifier.padding(end = 8.dp),
                            onClick = {
                                textState.value = TextFieldValue(EMPTY_STRING)
                                showPrediction.value = false
                                keyboardController?.hide()
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                maxLines = 1,
                                color = colorResource(id = R.color.colorGray)
                            )
                        }
                    }
                }
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    if (showPrediction.value) {
                        items(state.items, itemContent = { item ->
                            ProductRow(
                                item = item,
                                userList = state.userList,
                                onIncreaseQuantityClicked = viewModel::increaseQuantity,
                                onAddProductClicked = { productName ->
                                    textState.value = TextFieldValue(EMPTY_STRING)
                                    showPrediction.value = false
                                    viewModel.addProduct(
                                        userListUUID = listUUID,
                                        productName = productName
                                    )
                                },
                                onDecreaseProductClicked = viewModel::decreaseQuantity
                            )
                            ThinDivider()
                        })
                    } else {
                        items(state.userListProducts, itemContent = { product ->
                            UserListProductRow(
                                product = product,
                                userList = state.userList,
                                onEditClicked = {
                                    navController.currentBackStackEntry?.arguments?.putParcelable(
                                        ProductDetail.PRODUCT_UI_MODEL,
                                        product
                                    )
                                    navController.currentBackStackEntry?.arguments?.putString(
                                        ProductDetail.LIST_UUID,
                                        listUUID
                                    )
                                    navController.navigate(Screen.ProductDetail.route)
                                },
                                onDoneClicked = {
                                    viewModel.toggleDone(product = product)
                                },
                                onRemoveFavoriteClicked = {
                                    viewModel.removeFromFavorites(product = product)
                                })
                            ThinDivider()
                        })
                    }
                }
            }
        }
        else -> {
            Text("Error Occurred")
        }
    }
}
