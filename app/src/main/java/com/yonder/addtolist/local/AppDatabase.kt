package com.yonder.addtolist.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yonder.addtolist.local.dao.CategoryEntityDao
import com.yonder.addtolist.local.dao.UserListEntityDao
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.UserListEntity

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