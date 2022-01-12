package com.yonder.addtolist.scenes.login.presentation

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.yonder.addtolist.BuildConfig
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.base.BaseFragment
import com.yonder.addtolist.common.ui.extensions.setSafeOnClickListener
import com.yonder.addtolist.common.ui.extensions.showSnackBar
import com.yonder.addtolist.common.ui.extensions.showToastMessage
import com.yonder.addtolist.core.extensions.toReadableMessage
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
          binding.showSnackBar(viewState.message)
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


  private val startForGoogleSignInResult =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
      if (result.resultCode == Activity.RESULT_OK) {
        handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))
      }
    }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    viewModel.callbackManager.onActivityResult(requestCode, resultCode, data)
  }

  private fun startGoogleLogin() {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(BuildConfig.REQUEST_ID_TOKEN)
      .requestEmail()
      .build()
    val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    startForGoogleSignInResult.launch(mGoogleSignInClient.signInIntent)
  }

  private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
    try {
      val account = completedTask.getResult(ApiException::class.java)
      if (account != null){
        viewModel.continueWithGoogle(account)
      }else{
        context?.showToastMessage(R.string.error_occurred)
      }
    } catch (e: ApiException) {
      context?.showToastMessage(e.toReadableMessage())
    }
  }

}
