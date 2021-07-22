package com.yonder.addtolist.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yonder.addtolist.local.dao.CategoryEntityDao
import com.yonder.addtolist.local.dao.ProductDao
import com.yonder.addtolist.local.dao.UserListEntityDao
import com.yonder.addtolist.local.dao.UserListProductDao
import com.yonder.addtolist.local.entity.CategoryEntity
import com.yonder.addtolist.local.entity.ProductEntity
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.local.entity.UserListProductEntity

/**
 * Yusuf Onder on 12,May,2021
 */

@Database(
  entities = [
    CategoryEntity::class,
    UserListEntity::class,
    ProductEntity::class,
    UserListProductEntity::class],
  version = 7,
  exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
  abstract fun categoryDao(): CategoryEntityDao
  abstract fun userListDao(): UserListEntityDao
  abstract fun userListProductDao(): UserListProductDao
  abstract fun productDao(): ProductDao
}