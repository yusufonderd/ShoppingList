package com.yonder.addtolist.uicomponent

import androidx.compose.foundation.background
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.yonder.addtolist.R

/**
 * @author yusuf.onder
 * Created on 18.02.2022
 */

@Composable
fun ThinDivider() {
    Divider(modifier = Modifier.background(colorResource(id = R.color.gray_50)))
}