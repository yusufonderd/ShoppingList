package com.yonder.addtolist.scenes.premium

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yonder.addtolist.R

/**
 * @author yusuf.onder
 * Created on 11.11.2021
 */

data class PremiumFeature(var titleResId : Int)

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
        }

        IconButton(modifier = Modifier
          .then(Modifier.size(24.dp)
        ,
        ),
          onClick = { }) {
          Icon(
            Icons.Filled.Search,
            "contentDescription",
            tint = Color.White
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