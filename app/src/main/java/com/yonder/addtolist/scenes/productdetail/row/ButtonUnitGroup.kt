package com.yonder.addtolist.scenes.productdetail.row

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.yonder.addtolist.common.enums.ProductUnitType

/**
 * @author yusuf.onder
 * Created on 20.02.2022
 */
@Composable
fun UnitSegmentedControlView(
    options: ArrayList<String>,
    unitType: ProductUnitType,
    onClickUnit: (unit: ProductUnitType) -> Unit
) {
    val cornerRadius = 8.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        options.forEachIndexed { index, item ->
            OutlinedButton(
                modifier = when (index) {
                    0 -> {
                        if (unitType.index == index) {
                            Modifier
                                .offset(0.dp, 0.dp)
                                .zIndex(1f)
                        } else {
                            Modifier
                                .offset(0.dp, 0.dp)
                                .zIndex(0f)
                        }
                    }
                    else -> {
                        val offset = -1 * index
                        if (unitType.index == index) {
                            Modifier
                                .offset(offset.dp, 0.dp)
                                .zIndex(1f)
                        } else {
                            Modifier
                                .offset(offset.dp, 0.dp)
                                .zIndex(0f)
                        }
                    }
                }.then(Modifier.weight(0.25f)),
                onClick = {
                    onClickUnit.invoke(ProductUnitType.find(index))
                },
                shape = when (index) {
                    // left outer button
                    0 -> RoundedCornerShape(
                        topStart = cornerRadius,
                        topEnd = 0.dp,
                        bottomStart = cornerRadius,
                        bottomEnd = 0.dp
                    )
                    // right outer button
                    options.size - 1 -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = cornerRadius,
                        bottomStart = 0.dp,
                        bottomEnd = cornerRadius
                    )
                    // middle button
                    else -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                },
                border = BorderStroke(
                    1.dp, if (unitType.index == index) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.DarkGray.copy(alpha = 0.75f)
                    }
                ),
                colors = if (unitType.index == index) {
                    // selected colors
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.1f
                        ), contentColor = MaterialTheme.colorScheme.primary
                    )
                } else {
                    // not selected colors
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                },
            ) {
                Text(
                    text = options[index],
                    color = if (unitType.index == index) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.DarkGray.copy(alpha = 0.9f)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}
