package com.yonder.addtolist.scenes.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.component.items.ItemOperationListener
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.common.utils.keyboard.KeyboardVisibilityEvent
import com.yonder.addtolist.common.utils.keyboard.hideKeyboardFor
import com.yonder.addtolist.core.extensions.EMPTY_STRING
import com.yonder.addtolist.databinding.FragmentListDetailBinding
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.statelayout.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@AndroidEntryPoint
class ListDetailFragment : BaseFragment<FragmentListDetailBinding>() {

  private val args: ListDetailFragmentArgs by navArgs()

  private val viewModel: ListDetailViewModel by viewModels()

  private val listId get() = args.userList.id.toString()

  private val listUUID get() = args.userList.uuid

  override fun initBinding(inflater: LayoutInflater) =
    FragmentListDetailBinding.inflate(inflater)

  override fun onResume() {
    super.onResume()
    KeyboardVisibilityEvent.registerEventListener(activity) { isKeyboardOpened: Boolean ->
      binding.yoFilteredItemsView.setVisibility(isVisible = isKeyboardOpened)
      binding.btnCancel.isVisible = isKeyboardOpened
      binding.yoProductListView.isGone = isKeyboardOpened
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
          ListDetailViewState.Loading -> {
            binding.stateLayout.setState(State.LOADING)
          }
          is ListDetailViewState.ShowContent -> {
            binding.stateLayout.setState(State.CONTENT)
          }
          is ListDetailViewState.UserListContent -> {
            onUserListContent(
              viewState.userListWithProducts,
              viewState.list,
              viewState.query
            )
          }
          is ListDetailViewState.Error -> {
            binding.stateLayout.setState(State.ERROR)
          }
          is ListDetailViewState.Initial -> {
            binding.stateLayout.setState(State.CONTENT)
          }
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

  private fun resetEditText() {
    binding.etSearch.text?.clear()
    context.hideKeyboardFor(binding.etSearch)
    binding.etSearch.clearFocus()
  }

  private fun initEditText() = with(binding.etSearch) {
    addTextChangedListener { editable ->
      val query = editable.toString()
      viewModel.fetchProducts(listUUID, query)
    }
  }

  private val iProductOperation = object : ItemOperationListener {
    override fun decreaseProductQuantity(productEntity: UserListProductEntity) {
      viewModel.decreaseQuantity(listId, productEntity)
    }

    override fun increaseProductQuantity(productEntity: UserListProductEntity) {
      viewModel.increaseQuantity(listId, productEntity)
    }

    override fun removeProductEntity(productEntity: UserListProductEntity) {
      viewModel.removeProduct(productEntity)
    }

    override fun addProduct(productName: String) {
      viewModel.addProduct(
        listId = listId,
        userListUUID = listUUID,
        productName = productName
      )
    }

  }

  private fun onUserListContent(
    userListWithProducts: UserListWithProducts,
    filteredProducts: List<ProductEntitySummary>,
    query: String
  ) = with(binding) {
    yoProductListView.bind(userListWithProducts)
    yoFilteredItemsView.bind(
      userListWithProducts,
      filteredProducts,
      query,
      iProductOperation
    )
  }

}