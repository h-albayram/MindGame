package com.example.mindgame.viewmodel.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mindgame.datalayer.localdb.entity.ScoreTableEntity
import com.example.mindgame.datalayer.model.ScoreDto

class ScoreBoardAdapter(val ScoreList:List<ScoreTableEntity>) :RecyclerView.Adapter<ScoreBoardViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreBoardViewHolder =
        ScoreBoardViewHolder(parent)

    override fun getItemCount(): Int {
        ScoreList.let {
            return it.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ScoreBoardViewHolder, position: Int) {
        ScoreList.let {
            holder.Bind(it[position])
        }
    }
}