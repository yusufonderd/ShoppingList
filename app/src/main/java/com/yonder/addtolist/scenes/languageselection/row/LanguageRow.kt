package com.yonder.addtolist.scenes.languageselection.row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yonder.addtolist.scenes.languageselection.data.model.LanguageUiModel
import com.yonder.addtolist.theme.padding_8

/**
 * @author yusuf.onder
 * Created on 9.02.2022
 */
@Composable
fun LanguageRow(language: LanguageUiModel,onClick: () -> Unit){
    TextButton(
        onClick = onClick
    ) {
        Text(
            text = language.name,
            color = Color.DarkGray,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding_8)
                .padding(horizontal = padding_8)
                .align(Alignment.Bottom)
        )
    }
}

