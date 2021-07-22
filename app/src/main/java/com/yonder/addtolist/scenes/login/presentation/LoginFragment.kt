package com.yonder.addtolist.scenes.login.presentation

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>() {

  override fun initBinding(inflater: LayoutInflater) = LoginFragmentBinding.inflate(inflater)

  val viewModel: LoginViewModel by viewModels()

  override fun initObservers() {
    viewModel.state.observe(viewLifecycleOwner) { viewState ->
      when (viewState) {
        LoginViewState.NavigateLogin -> {
          findNavController().navigate(R.id.action_login_to_shopping_list_items)
        }
        is LoginViewState.Error -> {
          showSnackBar(viewState.message)
        }
        else -> Unit
      }
    }
  }

  override fun initViews() = with(binding) {
    setupFacebookLogin()
    btnContinueAsGuest.setSafeOnClickListener {
      viewModel.continueAsGuest()
    }
    btnContinueWithFacebook.setSafeOnClickListener {
      loginButton.performClick()
    }
    btnContinueWithGoogle.setSafeOnClickListener {
      startGoogleLogin()
    }
  }

  private fun setupFacebookLogin() = with(binding.loginButton) {
    fragment = this@LoginFragment
    registerCallback(viewModel.callbackManager, viewModel.facebookCallback)
  }


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