package com.yonder.addtolist.scenes.home.row

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding_4)
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
                    style = MaterialTheme.typography.body1,
                    color = colorResource(id = R.color.secondaryTextColor)
                )
            }

        }

    }
}