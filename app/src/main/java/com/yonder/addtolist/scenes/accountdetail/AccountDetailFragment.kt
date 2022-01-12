package com.yonder.addtolist.scenes.accountdetail

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yonder.addtolist.R
import com.yonder.addtolist.scenes.login.domain.model.UserUiModel
import com.yonder.addtolist.theme.default_page_padding
import com.yonder.uicomponent.compose.ErrorView
import com.yonder.uicomponent.compose.LoadingView
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yusuf.onder
 * Created on 21.07.2021
 */

@AndroidEntryPoint
class AccountDetailFragment : Fragment() {

  val viewModel: AccountDetailViewModel by viewModels()

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

  @Composable
  fun MainContent() {
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
        ProfileView(user = (state as AccountDetailViewState.AccountInfo).userUIModel)
      }
      is AccountDetailViewState.Logout -> {
        findNavController().navigate(AccountDetailFragmentDirections.actionAccountDetailToLogin())
      }
    }

  }

  @Composable
  fun ProfileView(user: UserUiModel) {

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
      }

      if (user.isAnonymousUser().not()) {
        item {
          AccountDetailText(text = user.createdAt)
        }
      }

      item {
        AuthButton(textResId = R.string.account_logout, backgroundColor = R.color.colorRed) {
          val alertDialogBuilder = AlertDialog.Builder(requireContext()).apply {
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

}
