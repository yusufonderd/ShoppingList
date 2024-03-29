package com.yonder.addtolist.scenes.productdetail.row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.yonder.addtolist.R
import com.yonder.addtolist.theme.padding_16
import com.yonder.addtolist.theme.padding_8

/**
 * @author yusuf.onder
 * Created on 20.02.2022
 */
@Composable
fun DeleteProductButton(onClick : ()-> Unit){

    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.colorRed)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding_16)
            .padding(top = padding_8)
    ) {
        Text(
            text = stringResource(id = R.string.delete_item),
            color = colorResource(id = R.color.always_white)
        )
    }
}