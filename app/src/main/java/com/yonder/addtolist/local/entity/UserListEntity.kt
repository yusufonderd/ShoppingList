package com.yonder.addtolist.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * @author: yusufonder
 * @date: 05/06/2021
 */


@Parcelize
@Entity(tableName = "userList")
class UserListEntity(
  @field:ColumnInfo(name = "id") @PrimaryKey val id: String,
  @field:ColumnInfo(name = "name") val name: String
) :Parcelable