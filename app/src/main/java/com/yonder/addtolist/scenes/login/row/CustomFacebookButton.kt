package com.yonder.addtolist.scenes.login.row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.yonder.addtolist.R
import timber.log.Timber

@Composable
fun CustomFacebookButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onSuccess: (LoginResult) -> Unit,
    onCancel: () -> Unit,
    onError: (FacebookException?) -> Unit,
) {

    val callbackManager = FacebookUtil.callbackManager
    val loginText = stringResource(R.string.continue_with_facebook)
    AndroidView(
        modifier = modifier.fillMaxWidth().height(50.dp),
        factory = ::LoginButton,
        update = { button ->
            button.setLoginText(loginText)
            button.setPermissions("email")
            button.isEnabled = enabled

            button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    onSuccess(result)
                    Timber.e("Login : ${result.accessToken}")
                }

                override fun onCancel() {
                    onCancel()
                    Timber.e("Login : On Cancel")
                }

                override fun onError(error: FacebookException) {
                    onError(error)
                    Timber.e("Login : ${error?.localizedMessage}")
                }
            })
        }
    )
}
object FacebookUtil {
    val callbackManager by lazy {
        CallbackManager.Factory.create()
    }
}