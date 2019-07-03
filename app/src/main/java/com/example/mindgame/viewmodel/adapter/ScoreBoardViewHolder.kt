package com.example.mindgame.viewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mindgame.R
import com.example.mindgame.datalayer.GameTypeEnum
import com.example.mindgame.datalayer.localdb.entity.ScoreTableEntity


class ScoreBoardViewHolder(parent: ViewGroup):
    RecyclerView.ViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.row_scoreboard, parent, false)) {
    var txtUserName: TextView
    var txtGameType: TextView
    var txtGameSize: TextView
    var txtScore: TextView

    init {
        txtUserName = itemView.findViewById(R.id.txtUsername)
        txtGameType = itemView.findViewById(R.id.txtGameType)
        txtGameSize = itemView.findViewById(R.id.txtGameSize)
        txtScore = itemView.findViewById(R.id.txtScore)
    }
    fun Bind(ScoreList:ScoreTableEntity){
        txtUserName.text=ScoreList.userName
        txtGameSize.text=ScoreList.gameSize.toString()+" Kare"
        when(ScoreList.gameType){
            GameTypeEnum.ANIMAL.value->{
                txtGameType.text="Hayvan"
            }
            GameTypeEnum.OBJECT.value->{
                txtGameType.text="Eşya"
            }
            GameTypeEnum.PLANT.value->{
                txtGameType.text="Bitki"
            }
            GameTypeEnum.GEOMETRICOBJECT.value->{
                txtGameType.text="Geometrik Şekil"
            }
        }

        txtScore.text=ScoreList.score.toString()
    }
}