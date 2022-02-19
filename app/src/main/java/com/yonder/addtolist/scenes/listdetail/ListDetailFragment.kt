package com.yonder.addtolist.scenes.listdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yonder.addtolist.R
import com.yonder.addtolist.core.extensions.EMPTY_STRING
import com.yonder.addtolist.domain.decider.CurrencyDecider
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.scenes.listdetail.row.ATLDivider
import com.yonder.addtolist.scenes.listdetail.row.ProductRow
import com.yonder.addtolist.scenes.listdetail.row.UserListProductRow
import com.yonder.addtolist.theme.padding_16
import com.yonder.addtolist.theme.padding_8
import com.yonder.addtolist.uicomponent.LoadingView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
@OptIn(ExperimentalComposeUiApi::class)
@Suppress("TooManyFunctions")
@AndroidEntryPoint
class ListDetailFragment : Fragment() {

    private val args: ListDetailFragmentArgs by navArgs()

    private val viewModel: ListDetailViewModel by viewModels()

    private val listId get() = args.userList.id

    private val listUUID get() = args.userList.uuid

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

    override fun onStart() {
        super.onStart()
        viewModel.listId = listId
        viewModel.fetchProducts(listUUID)
    }

    @Composable
    fun MainContent() {
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

        if (state.isLoading) {
            LoadingView()
        } else if (state.userList != null) {

            Column {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = textState.value,
                        textStyle = MaterialTheme.typography.body1,
                        interactionSource = interactionSource,
                        onValueChange = {
                            textState.value = it
                            showPrediction.value = it.text.isNotBlank()
                            viewModel.fetchProducts(listUUID, it.text)
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
                        Button(onClick = {
                            textState.value = TextFieldValue(EMPTY_STRING)
                            showPrediction.value = false
                            keyboardController?.hide()
                        }
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel)
                            )
                        }
                    }
                }

                if (showPrediction.value) {
                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(state.items, itemContent = { item ->
                            ProductRow(item = item, onIncreaseQuantityClicked = { product ->
                                viewModel.increaseQuantity(product)
                            }, onAddProductClicked = { productName ->
                                viewModel.addProduct(
                                    listId = listId,
                                    userListUUID = listUUID,
                                    productName = productName
                                )
                            }, onDecreaseProductClicked = { product ->
                                viewModel.decreaseQuantity(product)
                            })
                            ATLDivider()
                        })
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(state.userList?.products.orEmpty(), itemContent = { product ->
                            UserListProductRow(product = product, onEditClicked = {
                                navigateProductDetail(userListProduct = product)
                            }, onDoneClicked = {
                                viewModel.toggleDone(product = product)
                            }, onRemoveFavoriteClicked = {
                                viewModel.removeFromFavorites(product = product)
                            })
                            ATLDivider()
                        })
                    }
                }
            }
        }
    }

    private fun navigateProductDetail(userListProduct: UserListProductUiModel) {
        findNavController().navigate(
            ListDetailFragmentDirections.actionShoppingListDetailToProductDetail(
                userListProduct = userListProduct, listId = listId
            )
        )
    }

}
