package com.yonder.addtolist.scenes.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
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
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * @author yusuf.onder
 * Created on 24.07.2021
 */

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {


  private val args: ProductDetailFragmentArgs by navArgs()

  private val product get() = args.userListProduct

  val viewModel: ProductDetailViewModel by viewModels()

  override fun initBinding(inflater: LayoutInflater) =
    FragmentProductDetailBinding.inflate(inflater)

  override fun initObservers() {
    viewModel.combinedLiveData.observe(viewLifecycleOwner) { viewState ->
      when (viewState) {
        is ProductDetailViewState.Load -> {
          viewState.userListProduct?.let { product ->
            setProduct(product)
            initSpinner(viewState.categories, product)
          }
        }
      }
    }
  }

  private fun initSpinner(
    categories: List<CategoryEntity>,
    userListProduct: UserListProductEntity
  ) = with(binding.etAutoComplete) {
    val userListProductCategory = categories.find { it.image == userListProduct.categoryImage }
    val list =
      ArrayList<CategoryEntity>(categories.sortedBy { it.name }).map { it.wrappedFormattedName() }
    val adapter = MaterialSpinnerAdapter(context, R.layout.item_material_spinner, list)
    binding.etAutoComplete.setText(userListProductCategory?.wrappedFormattedName().orEmpty())
    binding.etAutoComplete.setAdapter(adapter)
    binding.etAutoComplete.setOnItemClickListener { adapterView, view, position, id ->
      if (position < list.size) {
        val category = list[position]
        //  viewModel.updateCategory(category, userListProduct)
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.getProductById(productId = product.id)
  }

  override fun initViews() {
    setClickListeners()
  }


  private fun setProduct(product: UserListProductEntity) {
    binding.etProductName.setText(product.name)
    binding.etPrice.setText(product.wrappedPrice())
    binding.etQuantity.setText(product.wrappedQuantityWith(requireContext()))
    setUnit(binding.toggleButton, product.unit)
    setFavorite(product.wrappedFavorite())
  }

  private fun setClickListeners() = with(binding) {
    btnDeleteItem.setSafeOnClickListener {

    }

    btnAddFavorite.setSafeOnClickListener {
      viewModel.toggleFavorite(product)
    }
    btnDone.setSafeOnClickListener {

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
      PIECE -> {
        group.check(R.id.button1)
      }
      PACKAGE -> {
        group.check(R.id.button2)
      }
      KG -> {
        group.check(R.id.button3)
      }
      LT -> {
        group.check(R.id.button4)
      }
    }

  }

  companion object {
    const val VIBRATE_MILLISECOND: Long = 50
    const val KG = "kg"
    const val LT = "lt"
    const val PACKAGE = "package"
    const val PIECE = "piece"
  }


}