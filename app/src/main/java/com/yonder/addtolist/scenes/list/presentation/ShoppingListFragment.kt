package com.yonder.addtolist.scenes.list.presentation

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.addVerticalDivider
import com.yonder.addtolist.common.ui.extensions.removeAnimator
import com.yonder.addtolist.common.ui.extensions.setLinearLayoutManager
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.databinding.ShoppingListFragmentBinding
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.addtolist.scenes.list.presentation.adapter.UserListAdapter
import com.yonder.statelayout.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingListFragment : BaseFragment<ShoppingListFragmentBinding>() {

  val viewModel: ShoppingListItemsViewModel by viewModels()

  override fun initBinding(inflater: LayoutInflater) =
    ShoppingListFragmentBinding.inflate(inflater)


  override fun onResume() {
    super.onResume()
    viewModel.getShoppingItems()
  }

  override fun initViews() {
    binding.fabAdd.setSafeOnClickListener {
      findNavController().navigate(R.id.action_shopping_list_to_create_list)
    }
    initRecyclerView()
  }

  private fun initRecyclerView() = with(binding.rvUserList) {
    setLinearLayoutManager()
    addVerticalDivider()
    removeAnimator()
  }

  override fun initObservers() {
    lifecycleScope.launchWhenResumed {
      viewModel.state.collect { viewState ->
        when (viewState) {
          is ShoppingListItemsViewState.CreateNewListContent -> {
            onCreateListContent()
          }
          is ShoppingListItemsViewState.Result -> {
            onListLoaded(viewState.userLists)
          }
          is ShoppingListItemsViewState.Loading -> {
            onLoading()
          }
        }
      }
    }
  }

  private fun onLoading(){
    binding.stateLayout.setState(State.LOADING)

  }
  private fun onCreateListContent() = with(binding){
    stateLayout.setState(State.CONTENT)
    ytCreateList.isVisible = true
    fabAdd.isVisible = false
    ytCreateList.initView(R.string.create_your_first_list, R.string.create_list) {
      findNavController().navigate(R.id.action_shopping_list_to_create_list)
    }
  }
  private fun onClickUserList(userListWithProduct: UserListWithProducts) {
    findNavController().navigate(
      ShoppingListFragmentDirections.actionShoppingListToListDetail(
        userList = userListWithProduct.userList,
        title = userListWithProduct.userList.name
      )
    )
  }

  private fun onListLoaded(userLists: List<UserListWithProducts>) = with(binding) {
    stateLayout.setState(State.CONTENT)
    binding.fabAdd.isVisible = true
    binding.ytCreateList.isVisible = false
    rvUserList.adapter = UserListAdapter(::onClickUserList).apply {
      submitList(userLists)
    }
  }

}