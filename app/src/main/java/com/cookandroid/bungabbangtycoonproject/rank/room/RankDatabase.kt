package com.cookandroid.bungabbangtycoonproject.rank.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Rank::class], version = 1)
abstract class RankDatabase: RoomDatabase() {
    abstract fun rankDao(): RankDao
}