package com.yonder.addtolist.features.list.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author: yusufonder
 * @date: 05/06/2021
 */


@Entity(tableName = "userList")
class UserListEntity(
  @field:ColumnInfo(name = "id") @PrimaryKey val id: String,
  @field:ColumnInfo(name = "name") val name: String
)