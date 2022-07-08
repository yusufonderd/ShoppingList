package com.yonder.addtolist.domain.uimodel

import android.os.Parcelable
import com.yonder.addtolist.common.enums.AppColor
import kotlinx.parcelize.Parcelize

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */

@Parcelize
data class UserListUiModel(
    val id: Int,
    val uuid: String,
    val name: String,
    val color: String,
    val uncompletedItems: String,
    val shouldShowUncompletedItems: Boolean,
    val products: List<UserListProductUiModel>,
    val appColor: AppColor,
    val description: String
) : Parcelable