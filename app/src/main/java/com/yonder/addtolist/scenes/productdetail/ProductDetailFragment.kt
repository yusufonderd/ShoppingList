package com.yonder.addtolist.scenes.productdetail

import android.text.InputFilter
import android.view.LayoutInflater
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButtonToggleGroup
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.component.adapter.MaterialSpinnerAdapter
import com.yonder.addtolist.common.ui.extensions.compatColor
import com.yonder.addtolist.common.ui.extensions.compatDrawable
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.common.utils.formatter.currency.CurrencyProvider
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.databinding.FragmentProductDetailBinding
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.productdetail.model.enums.ProductUnitType
import com.yonder.addtolist.scenes.productdetail.model.mapper.CategoryNameMapper
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

private const val MAX_LINE_LENGTH_PRICE = 8

@Suppress("TooManyFunctions")
@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {

  private val args: ProductDetailFragmentArgs by navArgs()

  private val product get() = args.userListProduct

  @Inject
  lateinit var userPreferencesDataStore: UserPreferenceDataStore

  val viewModel: ProductDetailViewModel by viewModels()

  var adapterSpinner: MaterialSpinnerAdapter<String>? = null

  override fun initBinding(inflater: LayoutInflater) =
    FragmentProductDetailBinding.inflate(inflater)

  override fun initObservers() {
    viewModel.state.observe(viewLifecycleOwner) { viewState ->
      when (viewState) {
        is ProductDetailViewState.Load -> {
          setProduct(viewState.product)
          initSpinner(
            categories = viewState.categories,
            productCategory = viewState.categoryOfProduct
          )
        }
        else -> Unit
      }
    }

  }

  private fun initSpinner(
    categories: List<CategoryEntity>,
    productCategory: CategoryEntity?
  ) = with(binding.etAutoComplete) {
    if (adapterSpinner == null) {
      val categoryNames = CategoryNameMapper().map(categories)
      val categoryList = categoryNames.map { it.wrappedName }
      adapterSpinner = MaterialSpinnerAdapter(context, R.layout.item_material_spinner, categoryList)
      setText(productCategory?.wrappedName())
      setAdapter(adapterSpinner)
    }
  }

  override fun onResume() {
    super.onResume()
    viewModel.fetchProductId(product.id)
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

  private fun setProduct(product: UserListProductEntity) {

    if (binding.etProductName.text?.isBlank() == true) {
      binding.etProductName.setText(product.name)
    }

    if (binding.etNote.text?.isBlank() == true) {
      binding.etNote.setText(product.note)
    }

    if (binding.etPrice.text?.isBlank() == true) {
      binding.etPrice.setText(product.price.orZero().toString())
    }

    binding.etQuantity.setText(product.wrappedQuantityWith(requireContext()))
    setUnit(binding.toggleButton, product.unit)
    setFavorite(product.wrappedFavorite())
  }

  private fun initTextChangedListeners() {

    binding.etNote.addTextChangedListener {
      viewModel.updateProductNote(product = product, note = it.toString())
    }

    binding.etProductName.addTextChangedListener {
      viewModel.updateProductName(product = product, name = it.toString())
    }

    binding.etPrice.addTextChangedListener {
      viewModel.updateProductPrice(
        product = product,
        price = binding.etPrice.getNumericValue()
      )
    }

  }

  private fun setClickListeners() = with(binding) {

    etAutoComplete.setOnItemClickListener { _, _, position, _ ->
      viewModel.updateCategory(product = product, categoryPosition = position)
    }

    btnDeleteItem.setSafeOnClickListener {
      viewModel.delete(product)
      closeFragment()
    }

    toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
      if (!isChecked) return@addOnButtonCheckedListener
      when (checkedId) {
        binding.button1.id -> {
          viewModel.updateUnit(product = product, unit = ProductUnitType.Piece)
        }
        binding.button2.id -> {
          viewModel.updateUnit(product = product, unit = ProductUnitType.Package)
        }
        binding.button3.id -> {
          viewModel.updateUnit(product = product, unit = ProductUnitType.Kg)
        }
        binding.button4.id -> {
          viewModel.updateUnit(product = product, unit = ProductUnitType.Lt)
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
      viewModel.done(product)
      closeFragment()
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
