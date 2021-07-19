package com.yonder.addtolist.scenes.login.presentation

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.yonder.addtolist.R
import com.yonder.addtolist.core.extensions.toReadableMessage

/**
 * Yusuf Onder on 12,May,2021
 */

internal fun LoginFragment.handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
  try {
    completedTask.getResult(ApiException::class.java)?.let { account ->
      viewModel.continueWithGoogle(account)
    } ?: run {
      showToastMessage(R.string.error_occurred)
    }
  } catch (e: ApiException) {
    showToastMessage(e.toReadableMessage())
  }
}

internal fun LoginFragment.startGoogleLogin() {
  val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestEmail()
    .build()
  val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
  startForGoogleSignInResult.launch(mGoogleSignInClient.signInIntent)
}


