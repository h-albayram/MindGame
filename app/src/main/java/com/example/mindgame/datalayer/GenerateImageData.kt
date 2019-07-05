package com.example.mindgame.datalayer

import android.content.Context
import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat
import com.example.mindgame.R
import com.example.mindgame.datalayer.model.PlayDto
import kotlin.random.Random

object GenerateImageData {
    fun getImage(GameType: Int, GameSize: Int,context: Context): List<PlayDto> {
        val numberList: ArrayList<Int> = ArrayList(GameSize)
        var imageList: ArrayList<PlayDto> = ArrayList()
        var control = true
        var i=0
        while ( i<GameSize/2) {
            control=true
            val randomnumber = Random.nextInt(1, 30)
            for (l in 0..numberList.size - 1) {
                if (numberList[l] ==randomnumber){
                    control=false
                    break
                }
            }
            if(control){
                numberList.add(randomnumber)
                i+=1
            }
        }
        when(GameType){
            GameTypeEnum.ANIMAL.value->{
                imageList= getImageList("animal",numberList,context)
            }
            GameTypeEnum.PLANT.value->{
                imageList= getImageList("plant",numberList,context)
            }
            GameTypeEnum.OBJECT.value->{
                imageList=   getImageList("object",numberList,context)
            }
            GameTypeEnum.GEOMETRICOBJECT.value->{
                imageList= getImageList("gobject",numberList,context)
            }
        }
        return imageList
    }
    fun getImageList(GameType: String,numberList:ArrayList<Int>,context: Context):ArrayList<PlayDto>{
        val imageList :ArrayList<PlayDto> = ArrayList()
        for (i in 0..numberList.size-1){
            val imageName=GameType+numberList[i]
            val PlayImage=PlayDto(false,context.resources.getIdentifier(imageName,"drawable","com.example.mindgame"))
            val PlayImage2=PlayDto(false,context.resources.getIdentifier(imageName,"drawable","com.example.mindgame"))
            imageList.add(PlayImage)
            imageList.add(PlayImage2)
        }
        return imageList
    }
}