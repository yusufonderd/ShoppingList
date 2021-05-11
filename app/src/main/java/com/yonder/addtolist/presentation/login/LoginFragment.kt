package com.yonder.addtolist.presentation.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.yonder.addtolist.core.base.BaseFragment
import com.yonder.addtolist.common.ProviderType
import com.yonder.addtolist.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>() {

  val viewModel: LoginViewModel by viewModels()

  override fun setObserver() {

  }

  override fun setupViews() {
    setupFacebookLogin()
    binding.btnContinueAsGuest.setOnClickListener {
      viewModel.continueAsGuest()
    }
    binding.btnContinueWithFacebook.setOnClickListener {
      binding.loginButton.performClick()
    }
  }

  private fun setupFacebookLogin() {
    binding.loginButton.registerCallback(viewModel.callbackManager, viewModel.facebookCallback)
  }

  override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
    LoginFragmentBinding.inflate(layoutInflater)

}