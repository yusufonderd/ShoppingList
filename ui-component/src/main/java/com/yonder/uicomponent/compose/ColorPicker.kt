package com.yonder.uicomponent.compose

import android.annotation.SuppressLint
import androidx.annotation.ColorRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.yonder.uicomponent.R

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */

@ExperimentalFoundationApi
@Composable
fun ColorPicker(
    listColors: List<Int>,
    modifier: Modifier,
    onClickColor: (Int) -> Unit,
    selectedColor: Int? = null,
    count: Int = 6
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(count),
        modifier = modifier
    ) {
        items(listColors.size) { item ->
            CircleShape(
                color = listColors[item],
                onClickColor = onClickColor,
                isSelected = selectedColor == listColors[item]
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun CircleShape(
    @ColorRes color: Int,
    onClickColor: (Int) -> Unit,
    isSelected: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .padding(4.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = when {
                        isSelected -> colorResource(R.color.gray_100)
                        else -> Color.Transparent
                    }
                )
            )
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(colorResource(id = color))
                .clickable(
                    onClick = {
                        onClickColor.invoke(color)
                    }
                )
        )
    }
}
