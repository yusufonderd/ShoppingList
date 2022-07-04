package com.yonder.addtolist.scenes.accountdetail

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yonder.addtolist.R
import com.yonder.addtolist.domain.uimodel.UserUiModel
import com.yonder.addtolist.scenes.activity.Screen
import com.yonder.addtolist.theme.default_page_padding
import com.yonder.addtolist.uicomponent.ErrorView
import com.yonder.addtolist.uicomponent.LoadingView

@Composable
fun AccountScreen(navController: NavController) {

    val viewModel = hiltViewModel<AccountDetailViewModel>()

    val state by viewModel.state.collectAsState()
    when (state) {
        AccountDetailViewState.Loading -> {
            LoadingView()
        }
        is AccountDetailViewState.Error -> {
            ErrorView(
                centerText = (state as AccountDetailViewState.Error).error,
                onClickTryAgain = {
                    viewModel.fetchCurrentUser()
                }
            )
        }
        is AccountDetailViewState.AccountInfo -> {
            ProfileView(user = (state as AccountDetailViewState.AccountInfo).userUIModel,viewModel = viewModel)
        }
        is AccountDetailViewState.Logout -> {
            navController.navigate(Screen.Login.route)
        }
    }

}

@Composable
fun ProfileView(user: UserUiModel,viewModel: AccountDetailViewModel) {

    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(default_page_padding)
    ) {

        if (user.isAnonymousUser().not()) {
            item {
                ProfileImageView(imageUrl = user.profileImage)
            }
            item {
                AccountDetailText(text = user.fullName)
            }
            item {
                AccountDetailText(text = user.email)
            }
        } else {

            item {
                AnonymousUserHeader()
            }
            /*
            item {
                AuthButton(
                    textResId = R.string.continue_with_facebook,
                    backgroundColor = R.color.facebook
                ) {

                }
            }

            item {
                AuthButton(
                    textResId = R.string.continue_with_google,
                    backgroundColor = R.color.google
                ) {

                }
            }
            */
        }

        if (user.isAnonymousUser().not()) {
            item {
                AccountDetailText(text = user.createdAt)
            }
        }

        item {
            AuthButton(textResId = R.string.account_logout, backgroundColor = R.color.colorRed) {
                val alertDialogBuilder = AlertDialog.Builder(context).apply {
                    setTitle(R.string.logout_title)
                    setMessage(R.string.logout_message)
                    setPositiveButton(R.string.yes) { _: DialogInterface, _: Int ->
                        viewModel.logout()
                    }
                    setNegativeButton(R.string.cancel, null)
                }
                alertDialogBuilder.create().show()
            }
        }
    }

}