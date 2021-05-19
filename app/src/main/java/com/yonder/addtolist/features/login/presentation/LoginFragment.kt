package com.yonder.addtolist.features.login.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.yonder.addtolist.R
import com.yonder.addtolist.core.base.BaseFragment
import com.yonder.addtolist.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>() {

  val viewModel: LoginViewModel by viewModels()

  private fun setObserver() {
    lifecycleScope.launchWhenResumed {
      viewModel.state.collect { viewState ->
        when (viewState) {
          LoginViewState.NavigateLogin -> {
            findNavController().navigate(R.id.action_login_to_shopping_list_items)
          }
          else -> Unit
        }
      }
    }
  }

  private fun setupViews() {
    setupFacebookLogin()
    binding.btnContinueAsGuest.setOnClickListener {
      viewModel.continueAsGuest()
    }
    binding.btnContinueWithFacebook.setOnClickListener {
      binding.loginButton.performClick()
    }
    binding.btnContinueWithGoogle.setOnClickListener {
      startGoogleLogin()
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViews()
    setObserver()
  }

  private fun setupFacebookLogin() {
    with(binding.loginButton){
      fragment = this@LoginFragment
      registerCallback(viewModel.callbackManager, viewModel.facebookCallback)
    }
  }

  override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
    LoginFragmentBinding.inflate(inflater)

  internal val startForGoogleSignInResult =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
      if (result.resultCode == Activity.RESULT_OK) {
        handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))
      }
    }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    viewModel.callbackManager.onActivityResult(requestCode, resultCode, data)
  }
}