package com.example.mindgame.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mindgame.R
import com.example.mindgame.Router
import com.example.mindgame.datalayer.GameSizeEnum
import com.example.mindgame.datalayer.GenerateImageData
import com.example.mindgame.datalayer.localdb.SharedPreference
import com.example.mindgame.datalayer.localdb.db.MindGameDb
import com.example.mindgame.datalayer.localdb.entity.ScoreTableEntity
import com.example.mindgame.datalayer.model.PlayDto
import com.example.mindgame.datalayer.model.ScoreDto
import io.androidedu.hoop.adapter.PlayListAdapter
import kotlinx.android.synthetic.main.activity_play.*
import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread
import kotlin.random.Random

class PlayActivity : BaseActivity(), View.OnClickListener {

    lateinit var sharedPreference: SharedPreference
    var userName = ""
    var gameType = 0
    var gameSize = 0
    var stopTime: Long = 0
    var andTime = ""
    var timer = Timer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        sharedPreference = SharedPreference(this)
        /*   gridView.onItemClickListener = object : AdapterView.OnItemClickListener {
               override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                   val selectedItem = parent!!.getItemAtPosition(position).toString()
                   val deneme = parent.getItemAtPosition(position)
               }

           }*/
        btnBack.setOnClickListener(this)
        btnPause.setOnClickListener(this)
        btnPlay.setOnClickListener(this)
        if (sharedPreference.getValueInt("GameType") != 0
            && sharedPreference.getValueInt("GameSize") != 0
            && !sharedPreference.getValueString("UserName").isNullOrEmpty()
        ) {
            gameType = sharedPreference.getValueInt("GameType")
            userName=sharedPreference.getValueString("UserName").toString()
            when (sharedPreference.getValueInt("GameSize")) {
                1 -> {
                    gameSize = GameSizeEnum.SMALL.value
                    gridView.numColumns = 2
                    andTime = "00:30"
                }

                2 -> {
                    gameSize = GameSizeEnum.LARGE.value
                    gridView.numColumns = 4
                    andTime = "01:00"

                }
            }
            userName = sharedPreference.getValueString("UserName").toString()
        }
        val numberList: ArrayList<Int> = ArrayList()
        var i = 0
        var control = true
        while (i < gameSize) {
            control = true
            val randomnumber = Random.nextInt(0, gameSize)
            for (l in 0..numberList.size - 1) {
                if (numberList[l] == randomnumber) {
                    control = false
                    break
                }
            }
            if (control) {
                numberList.add(randomnumber)
                i += 1
            }
        }
        val imageList = GenerateImageData.getImage(gameType, gameSize, getActivityContext()!!)
        Collections.shuffle(imageList)
        with(gridView) {
            adapter = PlayListAdapter(imageList, context, numberList, getActivity()!!,gameType,userName)
        }
        txtTime.base = SystemClock.elapsedRealtime()
        txtTime.start()
        txtTime.setOnChronometerTickListener {
            val time = it.text
            if (andTime == time) {
                txtTime.stop()
                addDialog()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btnBack -> {
                stopTime = txtTime.base - SystemClock.elapsedRealtime()
                txtTime.stop()
                addAlertDialog()
            }
            btnPause -> {
                stopTime = txtTime.base - SystemClock.elapsedRealtime()
                txtTime.stop()
                btnPlay.visibility = View.VISIBLE
                btnPause.visibility = View.GONE
            }
            btnPlay -> {
                txtTime.base = SystemClock.elapsedRealtime() + stopTime
                txtTime.start()
                btnPlay.visibility = View.GONE
                btnPause.visibility = View.VISIBLE
            }
        }
    }

    fun addDialog() {
        val dialogControl = AlertDialog.Builder(this)
        val mView = this.layoutInflater.inflate(R.layout.custom_dialog, null)
        val okButton = mView.findViewById<Button>(R.id.dialog_submit)
        val txtinformation = mView.findViewById<TextView>(R.id.txtinformation)
        val txtScoreTitle = mView.findViewById<TextView>(R.id.txtScoreTitle)
        val txtScores = mView.findViewById<TextView>(R.id.txtScores)
        txtScoreTitle.text="Oyunu zamanında bitiremedin."
        txtinformation.setGravity(Gravity.CENTER_HORIZONTAL)
        txtScores.setGravity(Gravity.CENTER_HORIZONTAL)
        txtScores.text = ""
        dialogControl.setOnDismissListener {
            timer = Timer()
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    getActivity()!!.runOnUiThread(Runnable {
                        val router = Router()
                        router.switchPage(this@PlayActivity, HomeActivity())
                        this@PlayActivity.finishAffinity()
                        timer.cancel()
                    })
                }
            }, 0, 300000)
        }
        dialogControl.setView(mView)
        val alertDialog = dialogControl.show()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCancelable(false)
        okButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }
    fun addAlertDialog() {
        val dialogControl = AlertDialog.Builder(this)
        val mView = this.layoutInflater.inflate(R.layout.custom_alert_dialog, null)
        val yepButton = mView.findViewById<Button>(R.id.btnYep)
        val noButton = mView.findViewById<Button>(R.id.btnNo)
        dialogControl.setOnDismissListener {

        }
        dialogControl.setView(mView)
        val alertDialog = dialogControl.show()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCancelable(false)
        yepButton.setOnClickListener {
            alertDialog.dismiss()
            timer = Timer()
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    getActivity()!!.runOnUiThread(Runnable {
                        val router = Router()
                        router.switchPage(getActivityContext(), HomeActivity())
                        this@PlayActivity.finishAffinity()
                        timer.cancel()
                    })
                }
            }, 0, 300000)
        }
        noButton.setOnClickListener{
            txtTime.base = SystemClock.elapsedRealtime() + stopTime
            txtTime.start()
            alertDialog.dismiss()
        }
    }

}
