package com.yonder.addtolist.uicomponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yonder.addtolist.R

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */

@Composable
fun NoListView(onClickCreateNewList: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.create_new_list),
            style = MaterialTheme.typography.h5,
            color = Color.DarkGray
        )

        ExtendedFloatingActionButton(
            backgroundColor = colorResource(id = R.color.colorPrimary),
            contentColor = colorResource(id = R.color.white),
            modifier = androidx.compose.ui.Modifier.padding(16.dp),
            text = { Text(text = stringResource(id = R.string.create)) },
            onClick = onClickCreateNewList,
            icon = { Icon(Icons.Filled.Add, stringResource(id = R.string.cd_create_new_list)) }
        )

    }
}