package com.yonder.addtolist.scenes.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.openWithKeyboard
import com.yonder.addtolist.databinding.FragmentListDetailBinding
import com.yonder.addtolist.local.entity.ProductEntitySummary
import com.yonder.addtolist.scenes.detail.adapter.productlist.ProductListsAdapter
import com.yonder.statelayout.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@AndroidEntryPoint
class ListDetailFragment : BaseFragment<FragmentListDetailBinding>() {

  private val args: ListDetailFragmentArgs by navArgs()

  private val viewModel: ListDetailViewModel by viewModels()

  val adapter: ProductListsAdapter by lazy {
    ProductListsAdapter(::onClickProduct)
  }

  private fun onClickProduct(product: ProductEntitySummary) {

  }

  override fun initBinding(inflater: LayoutInflater) =
    FragmentListDetailBinding.inflate(inflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViews()
    observeLiveData()
  }

  private fun observeLiveData() = with(viewModel) {
    lifecycleScope.launchWhenResumed {
      state.collect { viewState ->
        when (viewState) {
          ListDetailViewState.Loading -> {
            binding.stateLayout.setState(State.LOADING)
          }
          is ListDetailViewState.ShowContent -> {
            binding.stateLayout.setState(State.CONTENT)
            binding.etSearch.openWithKeyboard(requireContext())
          }
          is ListDetailViewState.QueryResult -> {
            setQueryResult(viewState.list)
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

  private fun initViews() = with(binding) {
    etSearch.addTextChangedListener { editable ->
      val query = editable.toString()
      viewModel.search(query)
    }
    recyclerView.itemAnimator = null
  }

  private fun setQueryResult(list: List<ProductEntitySummary>) {
    binding.recyclerView.adapter = adapter.apply {
      submitList(list)
    }
  }

}