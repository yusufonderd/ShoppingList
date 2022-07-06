package com.yonder.addtolist.uicomponent

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yonder.addtolist.R

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */


@Composable
fun SubmitButton(@StringRes textResId: Int, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.colorPrimary),
            contentColor = colorResource(id = R.color.white)
        ),
        onClick = onClick
    ) {
        Text(text = stringResource(id = textResId))
    }
}

