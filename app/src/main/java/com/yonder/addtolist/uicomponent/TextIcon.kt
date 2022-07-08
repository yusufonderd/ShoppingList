package com.yonder.addtolist.uicomponent

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TextIcon(imageVector: ImageVector, text: String, color: Color) {
    Row {
        Icon(imageVector = imageVector, contentDescription = text, tint = color)
        Text(text = text,color = color)
    }
}