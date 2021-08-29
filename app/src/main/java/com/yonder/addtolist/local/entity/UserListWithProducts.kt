package com.yonder.addtolist.local.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */
data class UserListWithProducts(
  @Embedded val userList: UserListEntity,
  @Relation(
    parentColumn = "uuid",
    entityColumn = "userListUUID"
  )
  val products: List<UserListProductEntity>
) {

  fun wrappedUncompletedItemsCount(): Int {
    return products.filter { !it.wrappedDone() }.count()
  }

  @Suppress("MagicNumber")
  fun wrappedUncompletedItems(): String {
    return products.filter { !it.wrappedDone() }
      .take(5)
      .map { it.name }
      .joinToString(",")
  }

}
