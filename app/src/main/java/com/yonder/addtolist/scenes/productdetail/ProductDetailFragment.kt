package com.yonder.addtolist.scenes.productdetail

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButtonToggleGroup
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.compatColor
import com.yonder.addtolist.common.ui.extensions.compatDrawable
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.common.utils.formatter.currency.CurrencyProvider
import com.yonder.addtolist.databinding.FragmentProductDetailBinding
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.home.domain.model.CategoryUiModel
import com.yonder.addtolist.scenes.productdetail.model.enums.ProductUnitType
import com.yonder.uicomponent.adapter.MaterialSpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

private const val MAX_LINE_LENGTH_PRICE = 8

@Suppress("TooManyFunctions")
@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {

    private val args: ProductDetailFragmentArgs by navArgs()

    val viewModel: ProductDetailViewModel by viewModels()

    private val product get() = args.userListProduct

    private val listId get() = args.listId

    var adapterSpinner: MaterialSpinnerAdapter<String>? = null

    override fun initBinding(inflater: LayoutInflater) =
        FragmentProductDetailBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = product.id
        if (productId != null) {
            viewModel.listId = listId
            viewModel.fetchProduct(productId)
        } else {
            closeFragment()
        }
    }

    override fun initObservers() {
        viewModel.event.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ProductDetailViewEvent.Load -> {
                    setProduct(viewState.product)
                    initSpinner(
                        categories = viewState.categories.map { it.formattedName },
                        categoryOfProduct = viewState.categoryOfProduct
                    )
                }
                is ProductDetailViewEvent.ProductNotFound -> {
                    closeFragment()
                }
                else -> Unit
            }
        }

    }

    private fun initSpinner(
        categories: List<String>,
        categoryOfProduct: CategoryUiModel?
    ) = with(binding.etAutoComplete) {
        if (adapterSpinner == null) {
            adapterSpinner =
                MaterialSpinnerAdapter(context, R.layout.item_material_spinner, categories)
            setText(categoryOfProduct?.formattedName)
            setAdapter(adapterSpinner)
        }
    }

    override fun initViews() {
        initPriceEditText()
        setClickListeners()
        initTextChangedListeners()
    }

    private fun initPriceEditText() = with(binding.etPrice) {
        setCurrencySymbol(CurrencyProvider.DEFAULT_CURRENCY_SIGN)
        filters = arrayOf(InputFilter.LengthFilter(MAX_LINE_LENGTH_PRICE))
    }

    private fun setProduct(product: UserListProductUiModel) = with(binding) {
        if (etProductName.text?.isBlank() == true) {
            etProductName.setText(product.name)
        }

        if (etNote.text?.isBlank() == true) {
            etNote.setText(product.note)
        }

        if (etPrice.text?.isBlank() == true) {
            etPrice.setText(product.price)
        }

        etQuantity.setText(product.quantity)
        setUnit(toggleButton, product.unit)
        setFavorite(product.isFavorite)

        // If user marked item as done
        // Product detail page closing automatically
        if (product.isDone) {
            closeFragment()
        }
    }

    private fun initTextChangedListeners() = with(binding) {

        etNote.addTextChangedListener {
            viewModel.updateProductNote(product, note = it.toString())
        }

        etProductName.addTextChangedListener {
            viewModel.updateProductName(product, name = it.toString())
        }

        etPrice.addTextChangedListener {
            viewModel.updateProductPrice(
                product = product,
                price = etPrice.getNumericValue()
            )
        }

    }

    private fun setClickListeners() = with(binding) {

        etAutoComplete.setOnItemClickListener { _, _, position, _ ->
            viewModel.updateCategory(
                product = product,
                categoryPosition = position
            )
        }

        btnDeleteItem.setSafeOnClickListener {
            viewModel.delete(product)
        }

        toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when (checkedId) {
                button1.id -> {
                    viewModel.updateUnit(product, unit = ProductUnitType.Piece)
                }
                button2.id -> {
                    viewModel.updateUnit(product, unit = ProductUnitType.Package)
                }
                button3.id -> {
                    viewModel.updateUnit(product, unit = ProductUnitType.Kg)
                }
                button4.id -> {
                    viewModel.updateUnit(product, unit = ProductUnitType.Lt)
                }
            }
        }

        btnDecrease.setOnClickListener {
            viewModel.decreaseQuantity(product)
        }

        btnIncrease.setOnClickListener {
            viewModel.increaseQuantity(product)
        }

        btnAddFavorite.setSafeOnClickListener {
            viewModel.toggleFavorite(product)
        }

        btnDone.setSafeOnClickListener {
            viewModel.toggleDone(product)
        }

    }

    private fun setFavorite(isFavorite: Boolean) = with(binding.btnAddFavorite) {
        val context = context ?: return@with
        if (isFavorite) {
            text = getString(R.string.remove_favorites)
            icon = context.compatDrawable(R.drawable.ic_baseline_favorite_24)
            setTextColor(context.compatColor(R.color.colorGray))
            setIconTintResource(R.color.colorGray)
        } else {
            text = getString(R.string.add_favorites)
            icon = context.compatDrawable(R.drawable.ic_baseline_favorite_border_24)
            setTextColor(context.compatColor(R.color.colorOrange))
            setIconTintResource(R.color.colorOrange)
        }
    }

    private fun setUnit(group: MaterialButtonToggleGroup, unit: String?) {
        when (unit) {
            ProductUnitType.Piece.value -> {
                group.check(R.id.button1)
            }
            ProductUnitType.Package.value -> {
                group.check(R.id.button2)
            }
            ProductUnitType.Kg.value -> {
                group.check(R.id.button3)
            }
            ProductUnitType.Lt.value -> {
                group.check(R.id.button4)
            }
        }
    }

}
