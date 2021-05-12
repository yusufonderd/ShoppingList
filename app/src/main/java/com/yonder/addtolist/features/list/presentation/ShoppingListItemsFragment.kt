package com.yonder.addtolist.features.list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yonder.addtolist.core.base.BaseFragment
import com.yonder.addtolist.databinding.ShoppingListItemsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingListItemsFragment : BaseFragment<ShoppingListItemsFragmentBinding>() {

  val viewModel: ShoppingListItemsViewModel by viewModels()

  override fun initBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ) = ShoppingListItemsFragmentBinding.inflate(inflater)

  @Inject
  lateinit var shoppingListNavigator: ShoppingListNavigator

  override fun setupViews() {

  }

  override fun setObserver() {
    lifecycleScope.launchWhenResumed {
      viewModel.shoppingListViewState.collect { viewState ->
        when (viewState) {
          ShoppingListItemsViewState.GoLogin -> {
            shoppingListNavigator.navigateLogin()
          }
          else -> {

          }
        }

      }
    }
  }


}