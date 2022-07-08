package com.yonder.addtolist.scenes.lists.row

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.yonder.addtolist.R
import com.yonder.addtolist.domain.uimodel.UserListUiModel
import com.yonder.addtolist.theme.padding_4

/**
 * @author yusuf.onder
 * Created on 8.02.2022
 */
@Composable
fun ListRow(list: UserListUiModel, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier
                    .padding(padding_4)
                    .weight(1.0f)
            ) {
                Text(
                    text = list.name,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = list.appColor.colorResId)
                )
                if (list.shouldShowUncompletedItems) {
                    Text(
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
                    color = colorResource(id = R.color.colorGray)
                )
                Image(
                    painter = painterResource(R.drawable.ic_baseline_chevron_right_24),
                    contentDescription = stringResource(R.string.cd_premium_image),
                    colorFilter = ColorFilter.tint(colorResource(R.color.gray_100))
                )
            }
        }
    }
}