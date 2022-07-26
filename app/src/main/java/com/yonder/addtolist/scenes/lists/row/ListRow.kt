package com.yonder.addtolist.scenes.lists.row

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.yonder.addtolist.R
import com.yonder.addtolist.domain.uimodel.UserListUiModel
import com.yonder.addtolist.theme.padding_4
import com.yonder.addtolist.theme.padding_8

/**
 * @author yusuf.onder
 * Created on 8.02.2022
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListRow(list: UserListUiModel, onClick: () -> Unit, onLongClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding_4)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier
                    .weight(1.0f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(padding_4),
                    text = list.name,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.black)
                )
                if (list.shouldShowUncompletedItems) {
                    Text(
                        modifier = Modifier
                            .padding(padding_4),
                        text = list.uncompletedItems,
                        maxLines = 1,
                        style = MaterialTheme.typography.body1,
                        color = colorResource(id = R.color.secondaryTextColor)
                    )
                }


            }

            Row() {
                Text(
                    text = list.description,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = list.appColor.colorResId)
                )
                Image(
                    painter = painterResource(R.drawable.ic_baseline_chevron_right_24),
                    contentDescription = stringResource(R.string.cd_premium_image),
                    colorFilter = ColorFilter.tint(colorResource(R.color.gray_100))
                )
            }
        }

        if (list.products.isNotEmpty()) {
            LinearProgressIndicator(
                progress = list.lineProgress,
                trackColor = colorResource(id = list.appColor.colorResId).copy(alpha = 0.3f),
                color = colorResource(id = list.appColor.colorResId),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding_4)
            )
        }


    }


}