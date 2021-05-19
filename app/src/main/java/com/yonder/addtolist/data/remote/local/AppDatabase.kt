package com.yonder.addtolist.data.remote.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yonder.addtolist.features.list.data.local.dao.CategoryEntityDao
import com.yonder.addtolist.features.list.data.local.entity.CategoryEntity

/**
 * Yusuf Onder on 12,May,2021
 */

@Database(
  entities = [CategoryEntity::class],
  version = 1,
  exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
  abstract fun categoryDao(): CategoryEntityDao
}