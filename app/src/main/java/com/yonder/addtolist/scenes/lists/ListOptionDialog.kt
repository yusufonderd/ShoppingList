package com.yonder.addtolist.scenes.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.yonder.addtolist.R
import com.yonder.addtolist.uicomponent.TextIcon

@Composable
fun ListOptionDialog(listName: String, onDismissRequest: () -> Unit,onDeleteList: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest, content = {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = listName,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedButton(onClick = {
                        onDeleteList.invoke()
                    }) {
                        TextIcon(
                            Icons.Filled.Delete,
                            stringResource(id = R.string.delete_list),
                            colorResource(id = R.color.colorRed)
                        )
                    }
                }
            }
        })
}