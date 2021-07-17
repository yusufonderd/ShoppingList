package com.yonder.addtolist.features.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.databinding.ShoppingListItemsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
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
    setObserver()
    Timber.d("adl-111 onViewCreated")
  }

  override fun onResume() {
    super.onResume()
    Timber.d("adl-111 onresume")
  }

  private fun setObserver() {
    lifecycleScope.launchWhenStarted {
      Timber.d("adl-111 launchWhenStarted")
      viewModel.shoppingListViewState.collect { viewState ->
        when (viewState) {
          is ShoppingListItemsViewState.Result -> {
            viewState.categories.list.forEach {  categoryWithProductsUiModel ->

            Timber.e("adl-111 => " +
                "${categoryWithProductsUiModel.name} - " +
                "${categoryWithProductsUiModel.products.size} - " +
                "${categoryWithProductsUiModel.translationResponses.size}")


            }
            viewState.categories.result.message.let(::showSnackBar)
          }
          is ShoppingListItemsViewState.Error ->
            viewState.errorMessage.let(::showSnackBar)
          else -> Unit
        }
      }
    }
  }



}