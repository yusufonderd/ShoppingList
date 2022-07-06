package com.yonder.addtolist.scenes.home.row

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.yonder.addtolist.R
import com.yonder.addtolist.theme.padding_16

/**
 * @author yusuf.onder
 * Created on 17.02.2022
 */
@Composable

fun CreateListFab(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier.padding(padding_16),
        onClick = onClick,
        backgroundColor = colorResource(id = R.color.colorPrimary)
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            stringResource(
                id = R.string.create_new_list
            ),
            tint = colorResource(id = R.color.white)
        )
    }
}