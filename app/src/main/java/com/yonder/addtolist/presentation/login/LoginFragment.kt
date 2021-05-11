package com.yonder.addtolist.presentation.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.yonder.addtolist.core.base.BaseFragment
import com.yonder.addtolist.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>() {

  val viewModel: LoginViewModel by viewModels()

  override fun setObserver() = Unit

  override fun setupViews() {
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

  private fun startGoogleLogin() {
    activity?.let { safeActivity ->
      val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
      val mGoogleSignInClient = GoogleSignIn.getClient(safeActivity, gso);
      val signInIntent: Intent = mGoogleSignInClient.signInIntent
      startActivityForResult(signInIntent, RC_SIGN_IN)
    }
  }

  private fun setupFacebookLogin() {
    binding.loginButton.registerCallback(viewModel.callbackManager, viewModel.facebookCallback)
  }

  override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
    LoginFragmentBinding.inflate(layoutInflater)

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == RC_SIGN_IN) {
      val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
      handleSignInResult(task)
    }
  }

  private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
    try {
      val account = completedTask.getResult(ApiException::class.java)
      viewModel.continueWithGoogle(account)
    } catch (e: ApiException) {
      Timber.e(e.localizedMessage)
    }
  }

  companion object {
    private const val RC_SIGN_IN = 1
  }

}