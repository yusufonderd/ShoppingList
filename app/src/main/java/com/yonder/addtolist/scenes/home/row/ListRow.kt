package com.yonder.addtolist.scenes.home.row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.yonder.addtolist.R
import com.yonder.addtolist.domain.uimodel.UserListUiModel
import com.yonder.addtolist.theme.padding_8

/**
 * @author yusuf.onder
 * Created on 8.02.2022
 */
@Composable
fun ListRow(list: UserListUiModel, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = list.name,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(padding_8),
            color = colorResource(id = R.color.primaryTextColor)
        )
    }
}