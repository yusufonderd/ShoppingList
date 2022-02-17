package com.yonder.addtolist.scenes.listdetail.row

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.yonder.addtolist.R
import com.yonder.addtolist.domain.uimodel.UserListProductUiModel
import com.yonder.addtolist.theme.padding_8
import timber.log.Timber

/**
 * @author yusuf.onder
 * Created on 17.02.2022
 */

@Composable
fun UserListProductRow(
    product: UserListProductUiModel,
    onEditClicked: () -> Unit,
    onDoneClicked: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onDoneClicked.invoke()
            },
        verticalAlignment = Alignment.CenterVertically,

        ) {

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

        Column() {
            Row() {

                if (product.isDone) {
                    Text(
                        text = product.name,
                        modifier = Modifier.padding(end = padding_8),
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.gray_100),
                        style = TextStyle(
                            textDecoration = TextDecoration.LineThrough,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            letterSpacing = 0.5.sp
                        )
                    )
                } else {
                    Text(
                        text = product.categoryUnicode,
                        modifier = Modifier.padding(end = padding_8),
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = product.name,
                        modifier = Modifier.padding(end = padding_8),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.primaryTextColor)
                    )
                }


                Text(
                    text = product.quantity,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.gray_100)
                )
            }

            if (product.note.isNotBlank()) {
                Text(
                    text = product.note,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.primaryTextColor)
                )
            }
        }



        if (product.isFavorite) {
            Image(
                painter = painterResource(R.drawable.ic_baseline_star_24),
                contentDescription = "Star",
                colorFilter = ColorFilter.tint(colorResource(R.color.colorOrange))
            )
        }

        Spacer(modifier = Modifier.weight(1.0f))


        if (product.isDone) {
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
}
