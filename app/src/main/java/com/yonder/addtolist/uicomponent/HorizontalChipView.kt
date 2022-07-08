package com.yonder.addtolist.uicomponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.yonder.addtolist.R

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */

@ExperimentalMaterialApi
@Composable
fun HorizontalChipView(
    listNames: Array<String>,
    modifier: Modifier,
    onClickListItem: (String) -> Unit
) {
    LazyRow(modifier = modifier) {
        items(listNames) { listName ->
            HorizontalChipRow(
                listName = listName,
                onClick = onClickListItem
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HorizontalChipRow(
    listName: String,
    onClick: (String) -> Unit
) {
    Surface(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(R.color.gray_100)
        ),
        onClick = {
            onClick.invoke(listName)
        }
    ) {
        Text(
            text = listName,
            color = colorResource(id = R.color.primaryTextColor),
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.white),
                    shape = RectangleShape
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}