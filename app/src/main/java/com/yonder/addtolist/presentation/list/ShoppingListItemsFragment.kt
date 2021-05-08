package com.yonder.addtolist.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yonder.addtolist.R
import com.yonder.addtolist.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingListItemsFragment : BaseFragment() {


  val viewModel: ShoppingListItemsViewModel by viewModels()

  @Inject
  lateinit var shoppingListNavigator: ShoppingListNavigator

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.shopping_list_items_fragment, container, false)
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