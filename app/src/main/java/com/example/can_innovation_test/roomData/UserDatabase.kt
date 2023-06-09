package com.example.can_innovation_test.roomData

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [UserDataEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
//    companion object {
//        @Volatile
//        private var INSTANCE: UserDatabase? = null
//
//        fun getDatabase(context: Context): UserDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    UserDatabase::class.java,
//                    "word_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
}