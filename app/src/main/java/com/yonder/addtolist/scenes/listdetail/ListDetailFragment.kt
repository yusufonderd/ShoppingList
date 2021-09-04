package com.yonder.addtolist.scenes.listdetail

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.openWithKeyboard
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.common.utils.keyboard.KeyboardVisibilityEvent
import com.yonder.addtolist.common.utils.keyboard.hideKeyboardFor
import com.yonder.addtolist.databinding.FragmentListDetailBinding
import com.yonder.addtolist.scenes.home.domain.model.UserListProductUiModel
import com.yonder.addtolist.scenes.listdetail.domain.model.ProductEntityUiModel
import com.yonder.addtolist.scenes.listdetail.items.ItemOperationListener
import com.yonder.addtolist.scenes.listdetail.productlist.UserListProductOperationListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */
@Suppress("TooManyFunctions")
@AndroidEntryPoint
class ListDetailFragment : BaseFragment<FragmentListDetailBinding>(), UserListProductOperationListener,
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
              products = viewState.userList.products,
              filteredProducts = viewState.list,
              query = viewState.query
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

  override fun decreaseQuantity(item: UserListProductUiModel) {
    viewModel.decreaseQuantity(item)
  }

  override fun increaseQuantity(item: UserListProductUiModel) {
    viewModel.increaseQuantity(item)
  }

  override fun removeProduct(item: UserListProductUiModel) {
    viewModel.deleteProduct(item)
  }

  override fun addProduct(itemName: String) {
    viewModel.addProduct(
      listId = listId,
      userListUUID = listUUID,
      productName = itemName
    )
  }

  override fun edit(item: UserListProductUiModel) {
    findNavController().navigate(
      ListDetailFragmentDirections.actionShoppingListDetailToProductDetail(
        item
      )
    )
  }

  override fun toggleDone(item: UserListProductUiModel) {
    viewModel.toggleDone(item)
  }

  private fun openKeyboard() {
    binding.btnCancel.isVisible = true
    binding.etSearch.openWithKeyboard(requireContext())
  }

  private fun loadListContent(
    products: List<UserListProductUiModel>,
    filteredProducts: List<ProductEntityUiModel>,
    query: String
  ) = with(binding) {

    yoProductListView.bind(
      products = products,
      productOperationListenerListener = this@ListDetailFragment
    )

    yoFilteredItemsView.bind(
      products = products,
      list = filteredProducts,
      query = query.trimStart(),
      itemOperationListener = this@ListDetailFragment
    )

  }

}
