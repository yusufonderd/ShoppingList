package com.yonder.addtolist.scenes.listdetail.row

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Badge
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.yonder.addtolist.R
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.scenes.listdetail.items.model.ItemUiModel
import com.yonder.addtolist.theme.padding_4
import com.yonder.addtolist.theme.padding_8

/**
 * @author yusuf.onder
 * Created on 17.02.2022
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductRow(
    item: ItemUiModel,
    onAddProductClicked: (String) -> Unit,
    onDecreaseProductClicked: (UserListProductUiModel) -> Unit,
    onIncreaseQuantityClicked: (UserListProductUiModel) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (item.product != null) {
                    onIncreaseQuantityClicked.invoke(item.product!!)
                } else {
                    onAddProductClicked.invoke(item.name)
                }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {

        if (item.product == null) {
            Image(
                modifier = Modifier.padding(padding_8),
                painter = painterResource(R.drawable.ic_baseline_add_circle_outline_32),
                contentDescription = "Favorites",
                colorFilter = ColorFilter.tint(colorResource(R.color.gray_100))
            )
        } else {
            Badge(
                modifier = Modifier.padding(padding_8),
                contentColor = colorResource(id = R.color.white),
                backgroundColor = colorResource(id = R.color.colorPrimary)
            ) {
                Text(
                    text = "${item.product?.quantityValue}",
                    modifier = Modifier.padding(padding_4),
                    style = MaterialTheme.typography.body2
                )
            }
        }

        Text(
            text = item.name,
            modifier = Modifier.padding(end = padding_8),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.primaryTextColor)
        )

        Spacer(modifier = Modifier.weight(1.0f))
        if (item.product != null) {
            TextButton(onClick = {
                onDecreaseProductClicked.invoke(item.product!!)
            }) {
                Image(
                    modifier = Modifier.padding(padding_8),
                    painter = painterResource(R.drawable.ic_baseline_remove_circle_24),
                    contentDescription = "remove product",
                    colorFilter = ColorFilter.tint(colorResource(R.color.colorRed))
                )
            }
        }

    }

}
