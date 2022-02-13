package com.yonder.addtolist.scenes.home.row

import androidx.compose.foundation.Image
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
import com.yonder.addtolist.R
import com.yonder.addtolist.theme.padding_8

/**
 * @author yusuf.onder
 * Created on 8.02.2022
 */
@Composable
fun AddNewListRow(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_baseline_add_24
                ),
                contentDescription = stringResource(
                    id = R.string.create_new_list
                ),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.colorPrimary))
            )
            Text(
                text = stringResource(id = R.string.create_new_list),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(padding_8),
                color = colorResource(id = R.color.colorPrimary)
            )
        }
    }
}