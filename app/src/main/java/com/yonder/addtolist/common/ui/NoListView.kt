package com.yonder.addtolist.common.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.yonder.addtolist.R
import com.yonder.addtolist.theme.padding_16
import com.yonder.addtolist.theme.padding_8

/**
 * @author yusuf.onder
 * Created on 11.01.2022
 */

@Composable
fun NoListView( onClickCreateNewList: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .padding(padding_16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.create_your_first_list),
            style = MaterialTheme.typography.h5
        )

        ExtendedFloatingActionButton(
            backgroundColor = colorResource(id = R.color.colorPrimary),
            contentColor = colorResource(id = R.color.white),
            modifier = Modifier.padding(padding_16),
            text = { Text(text = stringResource(id = R.string.create)) },
            onClick = onClickCreateNewList,
            icon = { Icon(Icons.Filled.Add, stringResource(id = R.string.cd_create_new_list)) }
        )

    }
}