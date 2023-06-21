package com.cookandroid.bungabbangtycoonproject.rank.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rank")
data class Rank(
    var name: String,
    var revenue: Int
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
