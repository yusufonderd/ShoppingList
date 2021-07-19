package com.yonder.addtolist.scenes.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.openWithKeyboard
import com.yonder.addtolist.databinding.FragmentListDetailBinding
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
          ListDetailViewState.ShowContent -> {
            binding.stateLayout.setState(State.CONTENT)
          }
          is ListDetailViewState.Error -> {
            binding.stateLayout.setState(State.ERROR)
          }
        }
      }
    }
  }

  private fun initViews() = with(binding) {
    etSearch.openWithKeyboard(requireContext())
  }

}