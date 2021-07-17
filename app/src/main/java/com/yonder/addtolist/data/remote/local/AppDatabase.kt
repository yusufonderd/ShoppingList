package com.yonder.addtolist.data.remote.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yonder.addtolist.features.list.data.local.dao.CategoryEntityDao
import com.yonder.addtolist.features.list.data.local.dao.UserListEntityDao
import com.yonder.addtolist.features.list.data.local.entity.CategoryEntity
import com.yonder.addtolist.features.list.data.local.entity.UserListEntity

/**
 * Yusuf Onder on 12,May,2021
 */

@Database(
  entities = [CategoryEntity::class, UserListEntity::class],
  version = 2,
  exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
  abstract fun categoryDao(): CategoryEntityDao
  abstract fun userListDao(): UserListEntityDao
}