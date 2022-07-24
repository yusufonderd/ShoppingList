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
)