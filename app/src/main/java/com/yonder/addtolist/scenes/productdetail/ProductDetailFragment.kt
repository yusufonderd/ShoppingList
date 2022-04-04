package com.yonder.addtolist.scenes.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yonder.addtolist.R
import com.yonder.addtolist.common.enums.ProductUnitType
import com.yonder.addtolist.common.ui.extensions.showToastMessage
import com.yonder.addtolist.core.extensions.orFalse
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.scenes.activity.MainViewModel
import com.yonder.addtolist.theme.padding_16
import com.yonder.addtolist.theme.padding_8
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

@Suppress("TooManyFunctions")
@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

  //  private val args: ProductDetailFragmentArgs by navArgs()

    private val viewModel: ProductDetailViewModel by viewModels()

    private val mainViewModel: MainViewModel by activityViewModels()
/*
    private val product get() = args.userListProduct

    private val listId get() = args.listId
*/
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
        // viewModel.fetchProduct(listId = listId, productId = product.id.orZero())
    }

    override fun onPause() {
        super.onPause()
        val note = viewModel.note
        val name = viewModel.name
        val price = viewModel.price
        val categoryUiModel = viewModel.selectedCategory ?: return
       /* mainViewModel.updateProduct(
            product = product,
            listId = listId,
            name = name,
            categoryImage = categoryUiModel.image,
            categoryName = categoryUiModel.name,
            price = price,
            note = note,
        )*/
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MainContent() {

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

        Column {

            OutlinedTextField(
                value = viewModel.name,
                textStyle = MaterialTheme.typography.body1,
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
                onItemClick = viewModel::onChangeCategory
            )

            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.product?.quantity.orEmpty(),
                    textStyle = MaterialTheme.typography.body1,
                    enabled = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledLabelColor = colorResource(id = R.color.secondaryTextColor),
                        disabledTextColor = colorResource(id = R.color.primaryTextColor)
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
                 //   viewModel.increaseQuantity(product)
                }) {
                    Image(
                        painter = painterResource(R.drawable.ic_baseline_add_circle_outline_32),
                        contentDescription = "Increase quantity",
                        colorFilter = ColorFilter.tint(colorResource(R.color.colorGreen))
                    )
                }

                IconButton(onClick = {
                  //  viewModel.decreaseQuantity(product)
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
              //      viewModel.updateUnit(product, it)
                })

            OutlinedTextField(
                value = viewModel.price,
                textStyle = MaterialTheme.typography.body1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done,keyboardType = KeyboardType.Number),
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
                textStyle = MaterialTheme.typography.body1,
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
               // viewModel.toggleFavorite(product)
            })

            DeleteProductButton(onClick = {
              //  mainViewModel.delete(product)
                findNavController().popBackStack()
                context?.showToastMessage(R.string.product_deleted)
            })

        }

    }
}
