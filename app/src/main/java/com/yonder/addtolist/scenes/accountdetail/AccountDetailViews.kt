package com.yonder.addtolist.scenes.accountdetail

import android.content.DialogInterface
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.yonder.addtolist.R
import com.yonder.addtolist.theme.padding_16
import com.yonder.addtolist.theme.padding_32
import com.yonder.addtolist.theme.padding_8
import com.yonder.addtolist.theme.profile_image_size

/**
 * @author yusuf.onder
 * Created on 24.11.2021
 */

@Composable
fun AccountDetailText(text: String) {
  Text(
    text = text,
    style = MaterialTheme.typography.body1,
    color = Color.DarkGray,
    modifier = Modifier
      .fillMaxSize()
      .padding(padding_8),
    textAlign = TextAlign.Center
  )
}

@Composable
fun AnonymousUserHeader() {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .clip(RoundedCornerShape(8.dp))
      .background(
        colorResource(id = R.color.colorGray_alpha_10)
      )
  ) {
    Icon(
      imageVector = Icons.Filled.AccountCircle,
      contentDescription = stringResource(id = R.string.cd_profile_image),
      modifier = Modifier
        .height(128.dp)
        .width(128.dp)
        .padding(padding_16),
      tint = Color.DarkGray
    )
    Divider(color = Color.LightGray)
    Row(
      modifier = Modifier.padding(padding_8)
    ) {
      Icon(
        imageVector = Icons.Filled.Info,
        contentDescription = stringResource(id = R.string.cd_profile_image),
        tint = Color.DarkGray
      )
      Text(
        text = stringResource(id = R.string.anonymous_user_encourage_text),
        textAlign = TextAlign.Center,
        color = Color.DarkGray
      )
    }
  }
}

@Composable
fun ProfileImageView(imageUrl: String) {
  Box(modifier = Modifier.padding(padding_32)) {
    Image(
      painter = rememberImagePainter(imageUrl),
      contentDescription = stringResource(R.string.cd_close_premium_screen),
      modifier = Modifier
        .fillMaxSize()
        .size(profile_image_size)
    )
  }
}

@Composable
fun AuthButton(
  @StringRes textResId: Int,
  @ColorRes backgroundColor: Int,
  @ColorRes textColorRes: Int = R.color.white,
  onClick: () -> Unit
) {
  Button(
    onClick = onClick,
    modifier = Modifier.padding(vertical = padding_8),
    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = backgroundColor))
  ) {
    Text(
      text = stringResource(textResId),
      color = colorResource(id = textColorRes),
      textAlign = TextAlign.Center,
      modifier = Modifier
        .fillMaxSize()
    )
  }
}