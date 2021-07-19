package com.yonder.addtolist.scenes.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.databinding.ShoppingListItemsFragmentBinding
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.scenes.list.presentation.adapter.UserListsAdapter
import com.yonder.statelayout.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint
class ShoppingListItemsFragment : BaseFragment<ShoppingListItemsFragmentBinding>() {

  val viewModel: ShoppingListItemsViewModel by viewModels()

  override fun initBinding(inflater: LayoutInflater) =
    ShoppingListItemsFragmentBinding.inflate(inflater)

  @Inject
  lateinit var shoppingListNavigator: ShoppingListNavigator


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observeLiveData()
  }

  override fun onResume() {
    super.onResume()
    viewModel.getShoppingItems()
  }

  private fun observeLiveData() {
    lifecycleScope.launchWhenResumed {
      viewModel.shoppingListViewState.collect { viewState ->
        when (viewState) {
          is ShoppingListItemsViewState.CreateNewListContent -> {
            binding.stateLayout.setState(State.CONTENT)
            binding.ytCreateList.isVisible = true
            binding.ytCreateList.initView(R.string.create_your_first_list, R.string.create_list) {
              findNavController().navigate(R.id.action_shopping_list_to_create_list)
            }
          }
          is ShoppingListItemsViewState.Result -> {
            onListLoaded(viewState.userLists)
          }
          is ShoppingListItemsViewState.Loading -> {
            binding.stateLayout.setState(State.LOADING)
          }

          is ShoppingListItemsViewState.Error ->
            binding.stateLayout.setState(State.ERROR)
        }
      }
    }
  }

  private fun onClickUserList(userList: UserListEntity) {
    shoppingListNavigator.navigateList(userList)
  }

  private fun onListLoaded(userLists: List<UserListEntity>) = with(binding) {
    stateLayout.setState(State.CONTENT)
    rvUserList.adapter = UserListsAdapter(::onClickUserList).apply {
      submitList(userLists)
    }
  }

}