package io.androidedu.hoop.adapter

import android.app.ActionBar
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.SystemClock
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.UiThread
import com.example.mindgame.R
import com.example.mindgame.Router
import com.example.mindgame.datalayer.localdb.SharedPreference
import com.example.mindgame.datalayer.localdb.db.MindGameDb
import com.example.mindgame.datalayer.localdb.entity.ScoreTableEntity
import com.example.mindgame.datalayer.model.PlayDto
import com.example.mindgame.ui.HomeActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.gameplay.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import kotlin.concurrent.thread
import kotlin.random.Random

class PlayListAdapter(
        var playlist: List<PlayDto>,
        val context: Context,
        val numberList: ArrayList<Int>,
        val activity: Activity,
        val gameType:Int,
        val userName:String
) : BaseAdapter() {
    val controlList: ArrayList<Int> = ArrayList()
    var getImageView: View? = null
    var temp = 0
    var control: Int = 0
    var Score = 0
    var Finish = 0
    var timer = Timer()
    lateinit var sharedPreference: SharedPreference
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val container = LayoutInflater.from(context).inflate(R.layout.gameplay, parent, false)
        val playCount: PlayDto
        playCount = playlist[numberList[position]]
        sharedPreference = SharedPreference(context)
        container.imgCard.tag = playCount.imgUrl
        if (playCount.type) {
            container.lytPlay.visibility = View.INVISIBLE
            container.imgCard.isClickable = false
        } else {
            container.lytPlay.visibility = View.VISIBLE
            container.imgCard.isClickable = true
            container.imgCard.setImageResource(R.drawable.questionsmark)
        }

        activity.txtPlayScore.text = "Puan:$Score"
        container.imgCard.setOnClickListener {
            container.imgCard.setImageResource(container.imgCard.tag.toString().toInt())
            if (getImageView == null) {
                getImageView = getView(position, convertView, parent)
                control = numberList[position]
            }
            temp += 1
            container.imgCard.isClickable = false
            if (temp == 2) {
                if (getImageView!!.imgCard.tag == container.imgCard.tag) {
                    timer = Timer()
                    timer.scheduleAtFixedRate(object : TimerTask() {
                        override fun run() {
                            activity.runOnUiThread(Runnable {
                                Score += 10
                                activity.txtPlayScore.text = "Puan:$Score"
                                notifyDataSetChanged()
                                timer.cancel()

                            })
                        }
                    }, 750, 300000)
                    playlist[control].type = true
                    playlist[numberList[position]].type = true
                    getImageView = null
                    temp = 0
                    Finish += 1
                } else {
                    timer = Timer()
                    timer.scheduleAtFixedRate(object : TimerTask() {
                        override fun run() {
                            activity.runOnUiThread(Runnable {
                                Score -= 5
                                activity.txtPlayScore.text = "Puan:$Score"
                                notifyDataSetChanged()
                                timer.cancel()
                            })
                        }
                    }, 1500, 300000)
                    playlist[control].type = false
                    playlist[numberList[position]].type = false
                    getImageView = null
                    temp = 0
                }
            }

            if (Finish == numberList.size / 2 || Score == -50) {
                sharedPreference.save("Score", (Score+10).toString())
                addNewScore(context,userName,gameType,numberList.size,(Score+10))
                addDialog()
            }
        }
        return container
    }

    override fun getItem(items: Int): Any {
        return playlist.get(items)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int {
        return playlist.size
    }

    fun addDialog() {
        val dialogControl = AlertDialog.Builder(context)
        val mView = activity.layoutInflater.inflate(R.layout.custom_dialog, null)
        val okButton = mView.findViewById<Button>(R.id.dialog_submit)
        val txtinformation = mView.findViewById<TextView>(R.id.txtinformation)
        val txtScores = mView.findViewById<TextView>(R.id.txtScores)
        txtinformation.setGravity(Gravity.CENTER_HORIZONTAL)
        txtScores.setGravity(Gravity.CENTER_HORIZONTAL)
        txtScores.text = (Score+10).toString()
        dialogControl.setOnDismissListener {
            timer = Timer()
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    activity.runOnUiThread(Runnable {
                        val router = Router()
                        router.switchPage(context, HomeActivity())
                        activity.finishAffinity()
                        timer.cancel()
                    })
                }
            }, 0, 300000)
        }
        activity.txtTime.stop()
        dialogControl.setView(mView)
        val alertDialog = dialogControl.show()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCancelable(false)
        okButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }
    fun addNewScore(context: Context,userName:String,gameType:Int,gameSize:Int,score:Int){
        val mindGameDb = MindGameDb.getInstance(context)
        val scoreTableDao = mindGameDb!!.getScoreTableDao()
        val scoreTableEntity = ScoreTableEntity(
            userName = userName,
            gameType = gameType,
            gameSize = gameSize,
            score = score
        )
        thread(start = true) {
            scoreTableDao.addNewItem(scoreTableEntity)
        }
    }
}