package com.example.can_innovation_test.roomData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg UserDatabase: UserDataEntity)
    @Query("SELECT * FROM users WHERE Username = :Username")
    fun getUserByUsername(Username: String): UserDataEntity?
    @Query("SELECT COUNT(*) FROM users")
    fun getRowCount(): Int
    @Query("SELECT * FROM users WHERE Username = :username AND Password = :password")
    fun getUser(username: String, password: String): UserDataEntity?
}