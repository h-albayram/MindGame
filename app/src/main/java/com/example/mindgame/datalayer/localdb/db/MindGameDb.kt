package com.example.mindgame.datalayer.localdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mindgame.datalayer.dao.ScoreTableDao
import com.example.mindgame.datalayer.localdb.entity.ScoreTableEntity

@Database(entities = arrayOf(ScoreTableEntity::class), version = 1)
abstract class MindGameDb : RoomDatabase() {

    abstract fun getScoreTableDao(): ScoreTableDao

    companion object {
        private var INSTANCE: MindGameDb? = null

        fun getInstance(context: Context): MindGameDb? {
            synchronized(MindGameDb::class) {

                if (INSTANCE == null) {
                    //Database oluşturma
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MindGameDb::class.java, "MindGame"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}