package com.yonder.addtolist.scenes.listdetail

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.uicomponent.items.ItemOperationListener
import com.yonder.uicomponent.productlist.IProductOperation
import com.yonder.addtolist.common.ui.extensions.openWithKeyboard
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.common.utils.keyboard.KeyboardVisibilityEvent
import com.yonder.addtolist.common.utils.keyboard.hideKeyboardFor
import com.yonder.addtolist.core.mapper.ListMapperImpl
import com.yonder.addtolist.databinding.FragmentListDetailBinding
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.scenes.listdetail.domain.mapper.UserListProductMapper
import com.yonder.addtolist.scenes.listdetail.domain.mapper.UserListProductSummaryToUiModelMapper
import com.yonder.addtolist.scenes.listdetail.domain.mapper.UserListProductToUiModelMapper
import com.yonder.uicomponent.base.model.UserListProductUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
@Suppress("TooManyFunctions")
@AndroidEntryPoint
class ListDetailFragment : BaseFragment<FragmentListDetailBinding>(), IProductOperation,
  ItemOperationListener {

  private val args: ListDetailFragmentArgs by navArgs()

  private val viewModel: ListDetailViewModel by viewModels()

  private val listId get() = args.userList.id

  private val listUUID get() = args.userList.uuid

  override fun initBinding(inflater: LayoutInflater) =
    FragmentListDetailBinding.inflate(inflater)

  override fun onResume() {
    super.onResume()
    KeyboardVisibilityEvent.registerEventListener(activity) { isKeyboardOpened: Boolean ->
      binding.yoFilteredItemsView.setVisibility(isVisible = isKeyboardOpened)
      binding.yoProductListView.setVisible(isVisible = !isKeyboardOpened)
      binding.btnCancel.isVisible = isKeyboardOpened
      if (!isKeyboardOpened) {
        binding.btnCancel.performClick()
      }
    }
  }

  override fun onStart() {
    super.onStart()
    viewModel.fetchProducts(listUUID)
  }

  override fun onStop() {
    super.onStop()
    KeyboardVisibilityEvent.unRegisterEventListener(activity)
  }

  override fun initObservers() {
    lifecycleScope.launchWhenResumed {
      viewModel.state.collect { viewState ->
        when (viewState) {
          is ListDetailViewState.UserListContent -> {
            loadListContent(
              viewState.userListWithProducts.products,
              viewState.list,
              viewState.query
            )
          }
          is ListDetailViewState.OpenKeyboard -> {
            openKeyboard()
          }
          else -> Unit
        }
      }
    }
  }


  override fun initViews() {
    initEditText()
    binding.btnCancel.setSafeOnClickListener {
      resetEditText()
    }
  }

  private fun resetEditText() = with(binding.etSearch) {
    context.hideKeyboardFor(this)
    clearFocus()
    text?.clear()
  }

  private fun initEditText() = with(binding.etSearch) {
    addTextChangedListener { editable ->
      val query = editable.toString()
      viewModel.fetchProducts(listUUID, query)
    }
  }

  override fun decreaseProductQuantity(productEntity: UserListProductUiModel) {
    viewModel.decreaseQuantity(productEntity)
  }

  override fun increaseProductQuantity(productEntity: UserListProductUiModel) {
    viewModel.increaseQuantity(productEntity)
  }

  override fun removeProductEntity(productEntity: UserListProductUiModel) {
    viewModel.removeProduct(productEntity)
  }

  override fun addProduct(productName: String) {
    viewModel.addProduct(
      listId = listId,
      userListUUID = listUUID,
      productName = productName
    )
  }

  override fun edit(product: UserListProductUiModel) {
    findNavController().navigate(
      ListDetailFragmentDirections.actionShoppingListDetailToProductDetail(
        product
      )
    )
  }

  override fun toggleDone(product: UserListProductUiModel) {
    viewModel.toggleDone(product)
  }

  private fun openKeyboard() {
    binding.btnCancel.isVisible = true
    binding.etSearch.openWithKeyboard(requireContext())
  }

  private fun loadListContent(
    products: List<UserListProductEntity>,
    filteredProducts: List<ProductEntitySummary>,
    query: String
  ) = with(binding) {
    val productsUIModel = ListMapperImpl(UserListProductToUiModelMapper()).map(products)

    val productsSummaryUIModel =
      ListMapperImpl(UserListProductSummaryToUiModelMapper()).map(filteredProducts)
    yoProductListView.bind(
      products = productsUIModel,
      productOperationListener = this@ListDetailFragment
    )

    yoFilteredItemsView.bind(
      products = productsUIModel,
      list = productsSummaryUIModel,
      query = query.trimStart(),
      itemOperationListener = this@ListDetailFragment
    )
  }

}
