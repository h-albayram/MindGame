package com.example.mindgame.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindgame.R
import com.example.mindgame.datalayer.localdb.db.MindGameDb
import com.example.mindgame.datalayer.localdb.entity.ScoreTableEntity
import com.example.mindgame.viewmodel.adapter.ScoreBoardAdapter
import kotlinx.android.synthetic.main.activity_score_board.*
import kotlin.concurrent.thread

class ScoreBoardActivity : AppCompatActivity() {
    var scoreList: List<ScoreTableEntity>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_board)
        val mindGameDb = MindGameDb.getInstance(this)
        val scoreTableDao = mindGameDb!!.getScoreTableDao()
        thread(start = true) {
            scoreList = scoreTableDao.getAllScoreTable()
            with(recyclerScoreBoard) {
                adapter = ScoreBoardAdapter(scoreList!!)
                layoutManager = LinearLayoutManager(this@ScoreBoardActivity)
            }
        }

    }
}
