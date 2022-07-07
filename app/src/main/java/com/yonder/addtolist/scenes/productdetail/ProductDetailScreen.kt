package com.yonder.addtolist.scenes.productdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.yonder.addtolist.R
import com.yonder.addtolist.common.enums.ProductUnitType
import com.yonder.addtolist.common.utils.OnLifecycleEvent
import com.yonder.addtolist.core.extensions.orFalse
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.scenes.activity.HomeViewModel
import com.yonder.addtolist.scenes.productdetail.row.AddToFavoriteButton
import com.yonder.addtolist.scenes.productdetail.row.DeleteProductButton
import com.yonder.addtolist.scenes.productdetail.row.DropDownTextField
import com.yonder.addtolist.scenes.productdetail.row.UnitSegmentedControlView
import com.yonder.addtolist.theme.padding_16
import com.yonder.addtolist.theme.padding_8

@Composable
fun ProductDetailScreen(navController: NavController) {

    val viewModel = hiltViewModel<ProductDetailViewModel>()

    val mainViewModel = hiltViewModel<HomeViewModel>()

    val arguments = navController
        .previousBackStackEntry
        ?.arguments

    val productUIModel =
        arguments?.getParcelable<UserListProductUiModel>(ProductDetail.PRODUCT_UI_MODEL)

    val listUUID = arguments?.getString(ProductDetail.LIST_UUID).orEmpty()

    val modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = padding_16)
        .padding(
            top = padding_16,
            bottom = padding_8
        )

    val options = arrayListOf(
        stringResource(id = R.string.unit_piece),
        stringResource(id = R.string.unit_package),
        stringResource(id = R.string.unit_kg),
        stringResource(id = R.string.unit_lt)
    )

    val state by viewModel.uiState.collectAsState()

    OnLifecycleEvent { _, _event ->
        when (_event) {
            Lifecycle.Event.ON_CREATE -> {
                val product = productUIModel ?: return@OnLifecycleEvent
                viewModel.fetchProduct(productId = product.id.orZero())
                viewModel.fetchList(listUUID)
            }
            Lifecycle.Event.ON_PAUSE -> {
                val product = productUIModel ?: return@OnLifecycleEvent
                val note = viewModel.note
                val name = viewModel.name
                val price = viewModel.price
                val categoryUiModel = viewModel.selectedCategory ?: return@OnLifecycleEvent
                mainViewModel.updateProduct(
                    product = product,
                    name = name,
                    categoryImage = categoryUiModel.image,
                    categoryName = categoryUiModel.name,
                    price = price,
                    note = note,
                )
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(
            color = colorResource(
                id =
                state.userList?.appColor?.colorResId ?: R.color.white
            ).copy(alpha = 0.1f)
        )
    ) {

        OutlinedTextField(
            value = viewModel.name,
            textStyle = MaterialTheme.typography.bodyLarge,
            onValueChange = viewModel::onChangeName,
            label = { Text(stringResource(id = R.string.product_name)) },
            modifier = modifier
                .background(color = colorResource(id = R.color.white)),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.product_name),
                    color = colorResource(id = R.color.primaryTextColor)
                )
            }
        )

        DropDownTextField(
            value = viewModel.categoryName,
            categories = state.categories,
            modifier = modifier.background(color = colorResource(id = R.color.white)),
            onItemClick = { category ->
                val product = productUIModel ?: return@DropDownTextField
                viewModel.onChangeCategory(product,category)
            }
        )

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = state.product?.quantity.orEmpty(),
                textStyle = MaterialTheme.typography.bodyLarge,
                enabled = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledLabelColor = colorResource(id = R.color.secondaryTextColor),
                    disabledTextColor = colorResource(id = R.color.primaryTextColor),
                    containerColor = colorResource(id = R.color.white)
                ),
                onValueChange = {

                },
                label = { Text(stringResource(id = R.string.product_quantity)) },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.product_quantity),
                        color = colorResource(id = R.color.primaryTextColor)
                    )
                }
            )

            IconButton(onClick = {
                val product = productUIModel ?: return@IconButton
                viewModel.increaseQuantity(product)
            }) {
                Image(
                    painter = painterResource(R.drawable.ic_baseline_add_circle_outline_32),
                    contentDescription = "Increase quantity",
                    colorFilter = ColorFilter.tint(colorResource(R.color.colorGreen))
                )
            }

            IconButton(onClick = {
                val product = productUIModel ?: return@IconButton
                viewModel.decreaseQuantity(product)
            }) {
                Image(
                    painter = painterResource(R.drawable.ic_baseline_remove_circle_32),
                    contentDescription = "Decrease quantity",
                    colorFilter = ColorFilter.tint(colorResource(R.color.colorRed))
                )
            }
        }

        UnitSegmentedControlView(
            options = options,
            unitType = state.product?.unitType ?: ProductUnitType.NoChoice,
            onClickUnit = {
                val product = productUIModel ?: return@UnitSegmentedControlView
                viewModel.updateUnit(product, it)
            })

        OutlinedTextField(
            value = viewModel.price,
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            onValueChange = viewModel::onChangePrice,
            label = { Text(stringResource(id = R.string.product_unit_price)) },
            modifier = modifier
                .background(color = colorResource(id = R.color.white)),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.product_unit_price),
                    color = colorResource(id = R.color.secondaryTextColor)
                )
            }
        )

        OutlinedTextField(
            value = viewModel.note,
            textStyle = MaterialTheme.typography.bodyLarge,
            onValueChange = viewModel::onChangeNote,
            label = { Text(stringResource(id = R.string.product_note)) },
            modifier = modifier
                .background(color = colorResource(id = R.color.white)),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.product_note),
                    color = colorResource(id = R.color.secondaryTextColor)
                )
            }
        )

        AddToFavoriteButton(isFavorite = state.product?.isFavorite.orFalse(), onClick = {
            val product = productUIModel ?: return@AddToFavoriteButton
            viewModel.toggleFavorite(product)
        })

        DeleteProductButton(onClick = {
            val product = productUIModel ?: return@DeleteProductButton
            mainViewModel.delete(product)
            navController.popBackStack()
        })

    }


}

object ProductDetail {
    const val PRODUCT_UI_MODEL = "PRODUCT_UI_MODEL"
    const val LIST_UUID = "LIST_UUID"

}