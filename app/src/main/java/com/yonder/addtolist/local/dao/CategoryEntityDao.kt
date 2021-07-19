package com.yonder.addtolist.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yonder.addtolist.local.entity.CategoryEntity

/**
 * Yusuf Onder on 12,May,2021
 */
@Dao
interface CategoryEntityDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(category: CategoryEntity)

  @Query("SELECT * FROM category")
  suspend fun fetchCategory(): List<CategoryEntity>

}