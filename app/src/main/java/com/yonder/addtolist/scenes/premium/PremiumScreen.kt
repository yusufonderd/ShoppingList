package com.yonder.addtolist.scenes.premium

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yonder.addtolist.R
import com.yonder.addtolist.theme.padding_16
import com.yonder.addtolist.theme.padding_8

@Composable
fun PremiumScreen(navController: NavController) {

    val viewModel = hiltViewModel<PremiumViewModel>()

    fun getPremiumFeatures(): ArrayList<PremiumFeature> {
        val items = ArrayList<PremiumFeature>()
        items.add(PremiumFeature(R.string.unlimited_lists))
        items.add(PremiumFeature(R.string.create_own_color))
        items.add(PremiumFeature(R.string.remove_all_ads))
        items.add(PremiumFeature(R.string.unlimited_access_to_all_features))
        return items
    }

    LazyColumn {
        item {

            Box {
                Image(
                    painter = painterResource(R.drawable.header_premium),
                    contentDescription = stringResource(R.string.cd_premium_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )


                TextWithShadow(
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(R.string.premium_brand_title),
                    modifier = Modifier
                        .padding(padding_16)
                        .align(Alignment.BottomStart)
                )

            }
        }

        item {
            Text(
                text = stringResource(R.string.be_premium_text),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(padding_8)
            )
        }

        items(getPremiumFeatures()) { premiumFeature ->
            Text(
                text = stringResource(premiumFeature.titleResId),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = padding_16, vertical = padding_8),
                textAlign = TextAlign.Left
            )
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        viewModel.buyPremium()
                    }, modifier = Modifier
                        .padding(padding_8)

                ) {
                    Text(stringResource(R.string._continue))
                }
            }

        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    onClick = {
                        viewModel.restorePurchase()
                    }, modifier = Modifier
                        .padding(bottom = padding_8)
                        .padding(horizontal = padding_8)

                ) {
                    Text(
                        text = stringResource(R.string.restore_purchase)
                    )
                }
            }
        }
    }
}


@Composable
fun TextWithShadow(
    text: String,
    modifier: Modifier,
    style: TextStyle,
    fontWeight: FontWeight
) {
    Text(
        text = text,
        fontWeight = fontWeight,
        style = style,
        color = Color.DarkGray,
        modifier = modifier
            .offset(
                x = 2.dp,
                y = 2.dp
            )
            .alpha(0.75f)
    )
    Text(
        text = text,
        fontWeight = fontWeight,
        style = style,
        color = Color.White,
        modifier = modifier
    )
}

data class PremiumFeature(@StringRes var titleResId: Int)
