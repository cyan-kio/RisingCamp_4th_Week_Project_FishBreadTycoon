package com.cookandroid.bungabbangtycoonproject.rank.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RankDao {
    @Insert
    fun insert(rank: Rank)

    @Query("SELECT * FROM Rank ORDER BY revenue DESC")
    fun getRevenueDesc(): MutableList<Rank>
}