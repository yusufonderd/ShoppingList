package com.yonder.addtolist.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yonder.addtolist.local.entity.UserListEntity

/**
 * @author: yusufonder
 * @date: 05/06/2021
 */

@Dao
interface UserListEntityDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(userList: UserListEntity)

  @Query("SELECT * FROM userList")
  suspend fun getUserLists(): List<UserListEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(userList: List<UserListEntity>)

}
