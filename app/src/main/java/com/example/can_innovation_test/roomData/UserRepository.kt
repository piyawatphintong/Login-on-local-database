package com.example.can_innovation_test.roomData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    suspend fun insert(user: UserDataEntity) {
        withContext(Dispatchers.IO) {
            userDao.insertAll(user)
        }
    }

    suspend fun getUserByUsername(username: String): UserDataEntity? {
        return withContext(Dispatchers.IO) {
            userDao.getUserByUsername(username)
        }
    }

    suspend fun checkUser(username: String, password: String): UserDataEntity? {
        return withContext(Dispatchers.IO) {
            userDao.getUser(username, password)
        }
    }

    suspend fun getRowCount(): Int {
        return withContext(Dispatchers.IO) {
            userDao.getRowCount()
        }
    }
}