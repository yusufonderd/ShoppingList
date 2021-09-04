package com.yonder.addtolist.scenes.languageselection

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.addVerticalDivider
import com.yonder.addtolist.common.ui.extensions.setLinearLayoutManager
import com.yonder.addtolist.databinding.FragmentLanguageSelectionBinding
import com.yonder.addtolist.scenes.languageselection.adapter.LanguageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */

@AndroidEntryPoint
class LanguageSelectionFragment : BaseFragment<FragmentLanguageSelectionBinding>() {

  private val viewModel: LanguageSelectionViewModel by viewModels()

  val adapterLanguage: LanguageAdapter by lazy {
    LanguageAdapter()
  }

  override fun initBinding(inflater: LayoutInflater) =
    FragmentLanguageSelectionBinding.inflate(inflater)

  override fun initObservers() {
    lifecycleScope.launchWhenResumed {
      viewModel.state.collect { viewEvent ->
        when (viewEvent) {
          is LanguageSelectionViewEvent.Load -> {
            adapterLanguage.submitList(viewEvent.languages)
          }
        }
      }
    }
  }

  override fun initViews() {
    initRecyclerView()
  }

  private fun initRecyclerView() = with(binding.rvLanguage) {
    setLinearLayoutManager()
    addVerticalDivider()
    adapter = adapterLanguage
  }

}
