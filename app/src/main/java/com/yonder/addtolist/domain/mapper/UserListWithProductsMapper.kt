package com.yonder.addtolist.domain.mapper

import android.content.Context
import com.yonder.addtolist.R
import com.yonder.addtolist.core.extensions.orZero
import com.yonder.core.base.mapper.ListMapperImpl
import com.yonder.core.base.mapper.Mapper
import com.yonder.addtolist.local.entity.UserListWithProducts
import com.yonder.addtolist.domain.uimodel.UserListUiModel
import com.yonder.addtolist.domain.decider.UncompletedItemsWrapper
import com.yonder.addtolist.common.enums.AppColor
import com.yonder.addtolist.core.extensions.EMPTY_STRING
import com.yonder.addtolist.core.extensions.orFalse
import com.yonder.addtolist.domain.decider.CompletedItemsWrapper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author yusuf.onder
 * Created on 30.08.2021
 */


class UserListWithProductsMapper @Inject constructor(@ApplicationContext private val context: Context) :
    Mapper<UserListWithProducts?, UserListUiModel?> {

    val mapper = UserListProductEntityToUiModel(context)
    override fun map(input: UserListWithProducts?): UserListUiModel? {

        if (input == null)
            return null
        val productsList = ListMapperImpl(mapper).map(input.products).sortedBy { it?.isDone.orFalse() }
        val safeProductList = productsList.filterNotNull()
        val productListSize = safeProductList.size
        val uncompletedItems = UncompletedItemsWrapper.wrap(safeProductList)
        val completedItemsCount = CompletedItemsWrapper.getCount(safeProductList)
        val description = if (productListSize == 0) {
            EMPTY_STRING
        } else {
            "$completedItemsCount / $productListSize"
        }

        val list = input.userList
        return UserListUiModel(
            id = list.id.orZero(),
            uuid = list.uuid,
            name = list.name,
            color = list.color,
            lineProgress = completedItemsCount.toFloat() / safeProductList.size.toFloat() ,
            uncompletedItems = uncompletedItems,
            shouldShowUncompletedItems = uncompletedItems.isNotBlank(),
            products = safeProductList,
            appColor = AppColor.find(input.userList.color),
            description = description
        )
    }

}
