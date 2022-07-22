package com.yonder.addtolist.local.dao

import androidx.room.*
import com.yonder.addtolist.local.entity.UserListEntity
import com.yonder.addtolist.local.entity.UserListProductEntity

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

  @Delete
  suspend fun delete(list: UserListEntity)

  @Query("SELECT * FROM userList WHERE  uuid = :listUUID ")
  suspend fun findByListUUID(listUUID: String): List<UserListEntity>

}
