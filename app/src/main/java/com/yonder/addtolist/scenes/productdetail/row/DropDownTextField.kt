package com.yonder.addtolist.scenes.productdetail.row

import androidx.compose.foundation.background
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.yonder.addtolist.R
import com.yonder.addtolist.domain.uimodel.CategoryUiModel

/**
 * @author yusuf.onder
 * Created on 20.02.2022
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownTextField(
    value: String,
    categories: List<CategoryUiModel>,
    modifier: Modifier,
    onItemClick: (CategoryUiModel) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = value,
            modifier = modifier.background(color = colorResource(id = R.color.white)),
            onValueChange = { },
            label = { Text(stringResource(id = R.string.select_category)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(containerColor = colorResource(id = R.color.white))
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            categories.forEach { categoryUiModel ->
                DropdownMenuItem(text = {
                    Text(text = categoryUiModel.formattedName)
                }, onClick = {
                    onItemClick.invoke(categoryUiModel)
                    expanded = false
                }
                )
            }
        }
    }
}