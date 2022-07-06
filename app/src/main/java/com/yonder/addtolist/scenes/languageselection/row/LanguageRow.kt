package com.yonder.addtolist.scenes.languageselection.row

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.yonder.addtolist.R
import com.yonder.addtolist.domain.uimodel.LanguageUiModel
import com.yonder.addtolist.theme.padding_8

/**
 * @author yusuf.onder
 * Created on 9.02.2022
 */
@Composable
fun LanguageRow(language: LanguageUiModel, onClick: () -> Unit) {
    TextButton(
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding_8)
        ) {
            Text(
                text = language.name,
                color = colorResource(id = R.color.primaryTextColor),
            )
        }

    }
}

