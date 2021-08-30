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
  @field:ColumnInfo(name = "uuid") @PrimaryKey val uuid: String,
  @field:ColumnInfo(name = "id") var id: Int?,
  @field:ColumnInfo(name = "name") var name: String,
  @field:ColumnInfo(name = "sync") var sync: Boolean,
  @field:ColumnInfo(name = "color") var color: String
) : Parcelable
