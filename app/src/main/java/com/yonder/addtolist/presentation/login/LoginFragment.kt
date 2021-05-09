package com.yonder.addtolist.presentation.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yonder.addtolist.core.base.BaseFragment
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>() {

  val viewModel: LoginViewModel by viewModels()

  override fun setObserver() {

  }

  override fun setupViews() {
    binding.btnContinueAsGuest.setOnClickListener {
      context?.let { safeContext ->
        viewModel.continueWith(
          context = safeContext,
          providerType = ProviderType.GUEST
        )
      }
    }
  }

  override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
    LoginFragmentBinding.inflate(layoutInflater)

}