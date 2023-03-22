package com.example.can_innovation_test.roomData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDataEntity(
    @PrimaryKey
    val _id: Int,
    @ColumnInfo val Username: String,
    @ColumnInfo val Password: String,
    @ColumnInfo val FullName: String,
    @ColumnInfo val BirthDate: String,
    @ColumnInfo val Email: String,
    @ColumnInfo val TelephoneNumber: String
)

