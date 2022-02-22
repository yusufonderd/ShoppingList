package com.yonder.addtolist.scenes.productdetail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.yonder.addtolist.R
import com.yonder.addtolist.theme.padding_16

/**
 * @author yusuf.onder
 * Created on 20.02.2022
 */
@Composable
fun AddToFavoriteButton(isFavorite: Boolean, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isFavorite) {
                colorResource(id = R.color.colorGray)
            } else {
                colorResource(id = R.color.colorOrange)
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding_16)
    ) {
        Text(
            text = if (isFavorite) {
                stringResource(id = R.string.remove_favorites)
            } else {
                stringResource(id = R.string.add_favorites)
            },
            color = colorResource(id = R.color.white)
        )
    }
}