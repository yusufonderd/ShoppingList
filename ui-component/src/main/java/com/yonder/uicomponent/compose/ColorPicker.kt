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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    selectedIndex: Int? = null,
    count: Int = 6
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(count),
        modifier = modifier
    ) {
        items(listColors.size) { itemIndex ->
            CircleShape(
                color = listColors[itemIndex],
                onClickColor = onClickColor,
                index = itemIndex,
                isSelected = itemIndex == selectedIndex
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun CircleShape(
    @ColorRes color: Int,
    index: Int,
    onClickColor: (Int) -> Unit,
    isSelected: Boolean = false
) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(4.dp)

        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(colorResource(id = color))
                    .clickable(
                        onClick = {
                            onClickColor.invoke(index)
                        }
                    )
            )

            if (isSelected){
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(id = R.drawable.ic_baseline_check_24),
                    contentDescription = stringResource(id = R.string.cd_select_color),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(colorResource(R.color.white))
                )
            }


        }






    /**/


}
