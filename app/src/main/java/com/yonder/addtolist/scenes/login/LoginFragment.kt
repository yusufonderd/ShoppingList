package com.yonder.addtolist.scenes.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.compose.rememberImagePainter
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.yonder.addtolist.BuildConfig
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.extensions.showToastMessage
import com.yonder.addtolist.core.extensions.toReadableMessage
import com.yonder.addtolist.domain.usecase.FacebookGraphUseCase
import com.yonder.addtolist.scenes.login.row.AuthButton
import com.yonder.addtolist.scenes.login.row.CustomFacebookButton
import com.yonder.addtolist.scenes.login.row.FacebookUtil
import com.yonder.addtolist.theme.padding_16
import com.yonder.addtolist.theme.padding_8
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment() {

    val viewModel: LoginViewModel by viewModels()



    val callbackManager = CallbackManager.Factory.create()
    val loginManager = LoginManager.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MainContent()
            }
        }
    }

    private val login = {
        LoginManager.getInstance().logIn(this, listOf(FacebookGraphUseCase.FIELDS_VALUES))
    }

    private val logout = {
        LoginManager.getInstance().logOut()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MainContent() {

        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        viewModel.continueWithGoogle(account = account)
                    } else {
                        Timber.e("account is null")
                    }
                } catch (e: ApiException) {
                   Timber.e("Api Exception => ${e.localizedMessage}")
                }

            }

        var isLoading by remember { mutableStateOf(false) }
        val event by viewModel.effect.collectAsState(initial = LoginViewState.Initial)

         fun googleLogin(
            context: Context,
            launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
        ) {
            lateinit var googleSignInClient: GoogleSignInClient

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.REQUEST_ID_TOKEN)
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(context, gso)

            val signInIntent = googleSignInClient.signInIntent

            launcher.launch(signInIntent)

        }

        when (event) {
            LoginViewState.NavigateToShoppingItems -> {
               // findNavController().navigate(R.id.action_login_to_shopping_list_items)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(color = colorResource(id = R.color.colorPrimary))
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.h4
            )

            Text(
                text = stringResource(id = R.string.app_description),
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(padding_8)
            )
            Image(
                rememberImagePainter(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_login_welcome
                    )
                ),
                contentDescription = stringResource(id = R.string.cd_welcome_image),
                modifier = Modifier.padding(padding_16)
            )


            /*AuthButton(textResId = R.string.continue_with_google) {
               googleLogin(requireContext(),launcher)
            }

            AuthButton(textResId = R.string.continue_with_facebook) {
             // login.invoke()
                facebookLogin(requireContext(),viewModel )
            }*/


            AuthButton(textResId = R.string.continue_as_guest) {
                viewModel.continueAsGuest()
            }

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        FacebookUtil.callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
       // viewModel.callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun facebookLogin(context: Context,viewModel: LoginViewModel) {

        val callbackManager = CallbackManager.Factory.create()
        val loginManager = LoginManager.getInstance()

        (context as? ActivityResultRegistryOwner)?.let {
            loginManager.logIn(
               it,
                callbackManager,
                listOf("email")
            )

            loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                    // viewModel.loginWithCredential(credential)
                }

                override fun onCancel() {
                }

                override fun onError(error: FacebookException) {
                }
            })
        }


    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                viewModel.continueWithGoogle(account)
            } else {
                context?.showToastMessage(R.string.error_occurred)
            }
        } catch (e: ApiException) {
            context?.showToastMessage(e.toReadableMessage())
        }
    }

}
