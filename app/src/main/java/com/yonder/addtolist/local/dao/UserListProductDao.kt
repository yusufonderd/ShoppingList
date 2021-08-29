package com.yonder.addtolist.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.yonder.addtolist.local.entity.UserListProductEntity
import com.yonder.addtolist.local.entity.UserListWithProducts
import kotlinx.coroutines.flow.Flow

/**
 * @author yusuf.onder
 * Created on 20.07.2021
 */
@Suppress("TooManyFunctions","LongParameterList")
@Dao
interface UserListProductDao {

  @Query("SELECT * FROM user_list_products WHERE id = :id")
  fun findById(id: Int): Flow<UserListProductEntity>


  @Query("SELECT * FROM user_list_products")
  fun loadAll(): LiveData<List<UserListProductEntity>>


  @Query("SELECT * FROM user_list_products  LIMIT :limit ")
  fun getLastNProduct(limit: Int): LiveData<List<UserListProductEntity>>

  @Transaction
  @Query("SELECT * FROM userList WHERE uuid = :listUUID")
  fun getUserListWithProducts(listUUID: String): LiveData<List<UserListWithProducts>>

  @Transaction
  @Query("SELECT * FROM userList")
  suspend fun getAllUserListWithProducts(): List<UserListWithProducts>

  @Transaction
  @Query("SELECT * FROM userList LIMIT :limit OFFSET :offset ")
  fun getFirstUserListWithProducts(limit: Int, offset: Int): LiveData<List<UserListWithProducts>>

  @Transaction
  @Query("SELECT * FROM userList WHERE uuid = :uuid")
  fun getUserListWithProductsBy(uuid: String): Flow<UserListWithProducts>

  @Query("SELECT * FROM user_list_products WHERE name = :productName and userListUUID = :listUUID LIMIT 1 ")
  fun findByListUUID(listUUID: String, productName: String): Flow<UserListProductEntity?>

  @Update
  suspend fun update(item: UserListProductEntity)

  @Delete
  suspend fun delete(product: UserListProductEntity)

  @Insert
  suspend fun insertAll(products: List<UserListProductEntity>)

  @Insert
  suspend fun insert(item: UserListProductEntity)

}
