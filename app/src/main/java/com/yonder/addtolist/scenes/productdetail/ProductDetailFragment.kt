package com.yonder.addtolist.scenes.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButtonToggleGroup
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.compatColor
import com.yonder.addtolist.common.ui.extensions.compatDrawable
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.databinding.FragmentProductDetailBinding
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.productdetail.adapter.MaterialSpinnerAdapter
import com.yonder.addtolist.scenes.productdetail.model.ProductUnit
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {

  private val args: ProductDetailFragmentArgs by navArgs()

  private val product get() = args.userListProduct

  val viewModel: ProductDetailViewModel by viewModels()

  var adapterSpinner: MaterialSpinnerAdapter<String>? = null

  override fun initBinding(inflater: LayoutInflater) =
    FragmentProductDetailBinding.inflate(inflater)

  override fun initObservers() {
    lifecycleScope.launchWhenResumed {
      viewModel.state.observe(viewLifecycleOwner) { viewState ->
        when (viewState) {
          is ProductDetailViewState.Load -> {
            setProduct(viewState.userListProduct)
            initSpinner(viewState.categories, product)
          }
          else -> Unit
        }
      }
    }
  }

  private fun initSpinner(
    categories: List<CategoryEntity>,
    userListProduct: UserListProductEntity
  ) = with(binding.etAutoComplete) {
    if (adapterSpinner == null) {
      val userListProductCategory = categories.find { it.image == userListProduct.categoryImage }
      val sortedCategories = ArrayList<CategoryEntity>(categories.sortedBy { it.name })
      val categoryNameList = sortedCategories.map { it.wrappedFormattedName() }
      adapterSpinner = MaterialSpinnerAdapter(context, R.layout.item_material_spinner, categoryNameList)
      with(binding.etAutoComplete){
        setText(userListProductCategory?.wrappedFormattedName().orEmpty())
        setAdapter(adapterSpinner)
        setOnItemClickListener { _, _, position, _ ->
          val category = sortedCategories[position]
          viewModel.updateCategory(product = product, category = category)
        }
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.fetchProductId(product.id)
  }

  override fun initViews() {
    setClickListeners()
    initTextChangedListeners()
  }

  private fun setProduct(product: UserListProductEntity) {
    if (binding.etProductName.text?.isBlank() == true) {
      binding.etProductName.setText(product.name)
    }
    if (binding.etNote.text?.isBlank() == true) {
      binding.etNote.setText(product.note)
    }
    binding.etPrice.setText(product.wrappedPrice())
    binding.etQuantity.setText(product.wrappedQuantityWith(requireContext()))
    setUnit(binding.toggleButton, product.unit)
    setFavorite(product.wrappedFavorite())
  }

  private fun initTextChangedListeners() {
    binding.etNote.addTextChangedListener {
      viewModel.updateProductNote(product = product, note = it.toString())
    }
    binding.etProductName.addTextChangedListener{
      viewModel.updateProductName(product = product,name = it.toString())
    }
  }

  private fun setClickListeners() = with(binding) {
    btnDeleteItem.setSafeOnClickListener {
      viewModel.delete(product)
      closeFragment()
    }

    toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
      if (!isChecked) return@addOnButtonCheckedListener
      when (checkedId) {
        binding.button1.id -> {
          viewModel.updateUnit(product = product, unit = ProductUnit.Piece)
        }
        binding.button2.id -> {
          viewModel.updateUnit(product = product, unit = ProductUnit.Package)
        }
        binding.button3.id -> {
          viewModel.updateUnit(product = product, unit = ProductUnit.Kg)
        }
        binding.button4.id -> {
          viewModel.updateUnit(product = product, unit = ProductUnit.Lt)
        }
      }
    }
    btnDecrease.setSafeOnClickListener {
      viewModel.decreaseQuantity(product)
    }
    btnIncreate.setSafeOnClickListener {
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
      ProductUnit.Piece.value -> {
        group.check(R.id.button1)
      }
      ProductUnit.Package.value -> {
        group.check(R.id.button2)
      }
      ProductUnit.Kg.value -> {
        group.check(R.id.button3)
      }
      ProductUnit.Lt.value -> {
        group.check(R.id.button4)
      }
    }

  }


}