package com.yonder.addtolist.scenes.accountdetail

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.databinding.FragmentAccountDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author yusuf.onder
 * Created on 21.07.2021
 */

@AndroidEntryPoint
class AccountDetailFragment : BaseFragment<FragmentAccountDetailBinding>() {

  val viewModel: AccountDetailViewModel by viewModels()

  override fun initBinding(inflater: LayoutInflater) =
    FragmentAccountDetailBinding.inflate(inflater)

  override fun initViews() {
    binding.btnLogout.setSafeOnClickListener {
      viewModel.logout()
    }
  }

  override fun initObservers() {
    lifecycleScope.launchWhenResumed {
      viewModel.state.collect { viewState ->
        when (viewState) {
          AccountDetailViewState.Logout -> {
            findNavController().navigate(AccountDetailFragmentDirections.actionAccountDetailToLogin())
          }
          else -> Unit
        }

      }
    }
  }

}
