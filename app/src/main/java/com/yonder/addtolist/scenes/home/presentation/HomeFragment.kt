package com.yonder.addtolist.scenes.home.presentation

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
import com.yonder.addtolist.databinding.FragmentHomeBinding
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.addtolist.scenes.home.presentation.adapter.UserListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

  val viewModel: ShoppingListItemsViewModel by viewModels()

  override fun initBinding(inflater: LayoutInflater) =
    FragmentHomeBinding.inflate(inflater)

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
    viewModel.state.observe(viewLifecycleOwner){ viewState ->
      when (viewState) {
        is ShoppingListItemsViewState.SetLayoutState ->{
          binding.stateLayout.setState(viewState.layoutState)
        }
        is ShoppingListItemsViewState.CreateNewListContent -> {
          showCreateListView()
        }
        is ShoppingListItemsViewState.Result -> {
          onListLoaded(viewState.userLists)
        }
      }
    }

  }

  private fun showCreateListView() = with(binding){
    ytCreateList.isVisible = true
    fabAdd.isVisible = false
    ytCreateList.initView(R.string.create_your_first_list, R.string.create_list) {
      findNavController().navigate(R.id.action_shopping_list_to_create_list)
    }
  }

  private fun onListLoaded(userLists: List<UserListWithProducts>) = with(binding) {
    binding.fabAdd.isVisible = true
    binding.ytCreateList.isVisible = false
    rvUserList.adapter = UserListAdapter(::onClickUserList).apply {
      submitList(userLists)
    }
  }

  private fun onClickUserList(userListWithProduct: UserListWithProducts) {
    navigateUserListDetail(userListWithProduct)
  }

  private fun navigateUserListDetail(userListWithProduct: UserListWithProducts){
    findNavController().navigate(
      HomeFragmentDirections.actionShoppingListToListDetail(
        userList = userListWithProduct.userList,
        title = userListWithProduct.userList.name
      )
    )
  }

}