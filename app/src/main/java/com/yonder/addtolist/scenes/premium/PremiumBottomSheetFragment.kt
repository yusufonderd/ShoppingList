package com.yonder.addtolist.scenes.premium

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yonder.addtolist.R

/**
 * @author yusuf.onder
 * Created on 11.11.2021
 */

data class PremiumFeature(var titleResId: Int)

class PremiumBottomSheetFragment : BottomSheetDialogFragment() {

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

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return super.onCreateDialog(savedInstanceState).apply {
      setOnShowListener {
        (dialog as BottomSheetDialog).behavior.setState(
          BottomSheetBehavior.STATE_EXPANDED
        )
      }
    }
  }


  private fun getPremiumFeatures(): ArrayList<PremiumFeature> {
    val items = ArrayList<PremiumFeature>()
    items.add(PremiumFeature(R.string.unlimited_lists))
    items.add(PremiumFeature(R.string.create_own_color))
    items.add(PremiumFeature(R.string.remove_all_ads))
    items.add(PremiumFeature(R.string.unlimited_access_to_all_features))
    return items
  }

  @Composable
  fun MainContent() {
    LazyColumn {
      item {

        Box {
          Image(
            painter = painterResource(R.drawable.header_premium),
            contentDescription = getString(R.string.cd_premium_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )

          FloatingActionButton(
            modifier = Modifier
              .padding(8.dp)
              .align(Alignment.TopEnd),
            onClick = {
              dismiss()
            },
            backgroundColor = Color.White.copy(alpha = 0.25f),
            contentColor = Color.White
          ) {
            Icon(
              Icons.Filled.Close,
              getString(R.string.cd_close_premium_screen)
            )
          }

          Text(
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            text = getString(R.string.premium_brand_title),
            modifier = Modifier
              .padding(16.dp)
              .align(Alignment.BottomStart),
          )


        }
      }

      item {
        Text(
          text = getString(R.string.be_premium_text),
          style = MaterialTheme.typography.body1,
          modifier = Modifier.padding(8.dp)
        )
      }

      items(getPremiumFeatures()) { premiumFeature ->
        Text(
          text = getString(premiumFeature.titleResId),
          style = MaterialTheme.typography.body1,
          modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
          textAlign = TextAlign.Left
        )
      }

      item {
        Button(
          onClick = {

          }, modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
        ) {
          Text(getString(R.string.upgrade))
        }
      }

      item {
        TextButton(
          onClick = { }, modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp)
            .padding(horizontal = 8.dp)
        ) {
          Text(text = getString(R.string.restore_purchase))
        }
      }

    }
  }


}