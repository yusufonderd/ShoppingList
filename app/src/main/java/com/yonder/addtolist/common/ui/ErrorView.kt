package com.yonder.addtolist.common.ui

/**
 * @author yusuf.onder
 * Created on 24.11.2021
 */
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yonder.addtolist.R
import com.yonder.addtolist.theme.padding_16
import com.yonder.addtolist.theme.padding_8

@Composable
fun ErrorView(centerText: String, onClickTryAgain: () -> Unit) {
  Box(
    modifier = Modifier
      .fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Icon(
        Icons.Filled.Info,
        stringResource(id = R.string.cd_error_image),
        modifier = Modifier
          .height(64.dp)
          .width(64.dp)
      )
      Text(
        text = centerText, textAlign = TextAlign.Center, color = Color.DarkGray,
        modifier = Modifier.padding(vertical = padding_8, horizontal = padding_16)
      )

      Button(onClick = onClickTryAgain, modifier = Modifier.padding(padding_16)) {
        Text(text = stringResource(id = R.string.try_again))
      }
    }
  }
}
