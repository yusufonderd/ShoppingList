package com.yonder.addtolist.scenes.listdetail.row

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.yonder.addtolist.R
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.theme.padding_8

/**
 * @author yusuf.onder
 * Created on 17.02.2022
 */

@Composable
fun EditButton(onEditClicked: () -> Unit) {
    IconButton(
        modifier = Modifier.padding(padding_8),
        onClick = onEditClicked
    ) {
        Icon(
            Icons.Filled.Edit,
            "Edit Product",
            tint = colorResource(id = R.color.gray_100)
        )
    }
}

@Composable
fun DoneImageView(isDone: Boolean) {
    if (isDone) {
        Image(
            modifier = Modifier.padding(padding_8),
            painter = painterResource(R.drawable.ic_baseline_check_circle_24),
            contentDescription = "Item has done",
            colorFilter = ColorFilter.tint(colorResource(R.color.gray_100))
        )
    } else {
        Image(
            modifier = Modifier.padding(padding_8),
            painter = painterResource(R.drawable.ic_baseline_radio_button_unchecked_24),
            contentDescription = "Mark item done",
            colorFilter = ColorFilter.tint(colorResource(R.color.colorPrimary))
        )
    }
}

@Composable
fun NoteField(note: String) {
    Text(
        text = note,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Start,
        color = colorResource(id = R.color.gray_200)
    )
}

@Composable
fun FavoriteImageView(onRemoveFavoriteClicked: () -> Unit) {
    IconButton(
        modifier = Modifier.padding(padding_8),
        onClick = onRemoveFavoriteClicked
    ) {
        Icon(
            Icons.Filled.Star,
            "Favorite button",
            tint = colorResource(id = R.color.colorOrange)
        )
    }

}

@Composable
fun TotalPriceField(price: String) {
    Text(
        text = price,
        modifier = Modifier.padding(start = padding_8),
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Center,
        color = colorResource(id = R.color.colorPrimary)
    )
}

@Composable
fun QuantityField(isDone: Boolean, quantity: String) {
    if (isDone) {
        Text(
            text = quantity,
            style = TextStyle(
                textDecoration = TextDecoration.LineThrough,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 0.5.sp
            ),
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.gray_100)
        )
    } else {
        Text(
            text = quantity,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.gray_100)
        )
    }
}

@Composable
fun ProductNameField(isDone: Boolean, name: String, formattedName: String, modifier: Modifier) {
    if (isDone) {
        Text(
            text = name,
            modifier = modifier,
            textAlign = TextAlign.Start,
            color = colorResource(id = R.color.gray_200),
            style = TextStyle(
                textDecoration = TextDecoration.LineThrough,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 0.5.sp
            )
        )
    } else {
        Text(
            text = formattedName,
            modifier = modifier,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Start,
            color = colorResource(id = R.color.primaryTextColor),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun UserListProductRow(
    product: UserListProductUiModel,
    onEditClicked: () -> Unit,
    onDoneClicked: () -> Unit,
    onRemoveFavoriteClicked: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding_8)
            .clickable {
                onDoneClicked.invoke()
            },
        verticalAlignment = Alignment.CenterVertically

    ) {

        EditButton(onEditClicked = onEditClicked)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            ProductNameField(
                isDone = product.isDone,
                name = product.name,
                formattedName = product.formattedName,
                modifier = Modifier
                    .padding(end = padding_8)
            )

            Row {
                if (product.shouldShowQuantityField) {
                    QuantityField(isDone = product.isDone, quantity = product.quantity)
                }

                if (product.shouldShowPriceField) {
                    TotalPriceField(price = product.totalPrice)
                }
            }
            if (product.shouldShowNoteField) {
                NoteField(note = product.note)
            }

        }

        if (product.isFavorite) {
            FavoriteImageView(onRemoveFavoriteClicked = onRemoveFavoriteClicked)
        }

        DoneImageView(isDone = product.isDone)

    }
}
