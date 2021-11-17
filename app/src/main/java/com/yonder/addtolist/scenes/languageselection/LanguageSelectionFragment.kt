package com.yonder.addtolist.scenes.languageselection

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.addVerticalDivider
import com.yonder.addtolist.common.ui.extensions.setLinearLayoutManager
import com.yonder.addtolist.common.ui.extensions.showSnackBar
import com.yonder.addtolist.databinding.FragmentLanguageSelectionBinding
import com.yonder.addtolist.scenes.languageselection.adapter.LanguageAdapter
import com.yonder.addtolist.scenes.languageselection.data.model.LanguageUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author yusuf.onder
 * Created on 4.09.2021
 */

@AndroidEntryPoint
class LanguageSelectionFragment : BaseFragment<FragmentLanguageSelectionBinding>() {

  private val viewModel: LanguageSelectionViewModel by viewModels()

  private val adapter: LanguageAdapter by lazy {
    LanguageAdapter()
  }

  override fun initBinding(inflater: LayoutInflater) =
    FragmentLanguageSelectionBinding.inflate(inflater)

  override fun initObservers() {
    lifecycleScope.launchWhenResumed {
      viewModel.state.collect { viewEvent ->
        when (viewEvent) {
          is LanguageSelectionViewEvent.Load -> {
            setLanguages(viewEvent.languages)
          }
          else -> Unit
        }
      }
    }
  }

  override fun initViews() {
    initRecyclerView()
  }

  private fun setLanguages(languages: List<LanguageUiModel>) {
    adapter.submitList(languages)
  }

  private fun initRecyclerView() = with(binding.rvLanguage) {
    setLinearLayoutManager()
    addVerticalDivider()
    adapter = this@LanguageSelectionFragment.adapter.apply {
      onClickLanguage = {
       // TODO("Change app language")
      }
    }
  }

}
