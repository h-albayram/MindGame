package com.example.mindgame.datalayer.model

data class ScoreDto(
    val id:Int,
    val userName:String,
    val gameType:Int,
    val gameSize:Int,
    val score:Int) {
}