package com.yonder.addtolist.uicomponent

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yonder.addtolist.R

/**
 * @author yusuf.onder
 * Created on 17.02.2022
 */
@Composable

fun CreateListFab(onClick: () -> Unit) {

    ExtendedFloatingActionButton(
        text = {
            Text(
                text = stringResource(
                    id = R.string.create_new_list
                )
            )
        },
        shape = RoundedCornerShape(32.dp),
        onClick = onClick,
        icon = {
            Icon(
                Icons.Filled.Add,
                stringResource(
                    id = R.string.create_new_list
                ),
                tint = Color.White
            )
        },
        containerColor = colorResource(id = R.color.colorPrimary),
        contentColor = Color.White,
        modifier = Modifier.padding(16.dp)
    )

}