package com.yonder.addtolist.scenes.accountdetail

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.compose.rememberImagePainter
import com.yonder.addtolist.R
import com.yonder.addtolist.common.ui.LoadingView
import com.yonder.addtolist.scenes.login.domain.model.UserUiModel
import com.yonder.addtolist.theme.default_page_padding
import com.yonder.addtolist.theme.profile_image_size
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
    val accountUIState by viewModel.state.collectAsState()
    when (accountUIState) {
      AccountDetailViewState.Loading -> {
        LoadingView()
      }
      is AccountDetailViewState.AccountInfo -> {
        ProfileView(
          user =
          (accountUIState as AccountDetailViewState.AccountInfo)
            .userUIModel
        )
      }
      is AccountDetailViewState.Logout -> {
        findNavController().navigate(AccountDetailFragmentDirections.actionAccountDetailToLogin())
      }
      else -> Unit
    }

  }

  @Composable
  fun ProfileView(user: UserUiModel) {

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(default_page_padding)
    ) {

      item {
        ProfileImageView(imageUrl = user.profileImage)
      }

      item {
        Text(
          text = user.fullName,
          style = MaterialTheme.typography.body1,
          color = Color.DarkGray,
          modifier = Modifier
            .fillMaxSize()
            .padding(8.dp), textAlign = TextAlign.Center
        )
      }

      item {
        Text(
          text = user.email,
          style = MaterialTheme.typography.body1,
          color = Color.DarkGray,
          modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
          textAlign = TextAlign.Center
        )
      }

      item {
        Text(
          text = user.createdAt,
          style = MaterialTheme.typography.body1,
          color = Color.DarkGray,
          modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
          textAlign = TextAlign.Center
        )
      }

      item {
        Button(
          onClick = {
            val alertDialogBuilder = AlertDialog.Builder(requireContext()).apply {
              setTitle(R.string.logout_title)
              setMessage(R.string.logout_message)
              setPositiveButton(R.string.yes) { _: DialogInterface, _: Int ->
                viewModel.logout()
              }
              setNegativeButton(R.string.cancel, null)
            }
            alertDialogBuilder.create().show()
          },
          modifier = Modifier.padding(vertical = 16.dp),

          colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
          Text(
            text = getString(R.string.account_logout),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
              .fillMaxSize()
          )
        }
      }
    }

  }

  @Composable
  fun ProfileImageView(imageUrl: String) {
    Box(modifier = Modifier.padding(32.dp)) {
      Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = getString(R.string.cd_close_premium_screen),
        modifier = Modifier
          .fillMaxSize()
          .size(profile_image_size)

      )
    }

  }


}
