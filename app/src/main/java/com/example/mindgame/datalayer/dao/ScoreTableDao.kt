package com.example.mindgame.datalayer.dao

import androidx.room.*
import com.example.mindgame.datalayer.localdb.entity.ScoreTableEntity
import com.example.mindgame.datalayer.model.ScoreDto

@Dao
interface ScoreTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewItem(scoreTable: ScoreTableEntity)

    @Delete()
    fun removeItem(scoreTable: ScoreTableEntity)

    @Update()
    fun updateItem(scoreTable: ScoreTableEntity)

    @Query("Select * from score_Table where _id LIKE :id")
    fun findSingleItem(id: Int): ScoreTableEntity

    @Query("Select * from score_Table")
    fun getAllScoreTable(): List<ScoreTableEntity>
}