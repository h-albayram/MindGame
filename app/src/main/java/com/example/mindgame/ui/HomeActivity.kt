package com.example.mindgame.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.mindgame.R
import com.example.mindgame.datalayer.localdb.SharedPreference
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(), View.OnClickListener {

    var isChecked = true
    lateinit var sharedPreference: SharedPreference
    var Score = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btnPlay.setOnClickListener(this)
        btnScoreBoard.setOnClickListener(this)
        sharedPreference = SharedPreference(this)
        if (!sharedPreference.getValueString("UserName").isNullOrEmpty()) {
            txtUser.text = sharedPreference.getValueString("UserName")
            txtScore.text = sharedPreference.getValueString("Score").toString()
        }

    }

    override fun onClick(v: View?) {
        when (v) {
            btnPlay -> {
                isChecked = true
                if (spnrGameType.selectedItemId.toInt() == 0) {
                    showAlert("Oyun türü boş geçilemez", this, R.color.colorRed)
                    isChecked = false
                }
                if (spnrGameSize.selectedItemId.toInt() == 0) {
                    showAlert("Oyun büyüklüğü boş geçilemez", this, R.color.colorRed)
                    isChecked = false
                }
                if (txtUsername.text.isNullOrEmpty()) {
                    showAlert("Oyuncu adı boş bırakılamaz", this, R.color.colorRed)
                    isChecked = false
                }
                if (isChecked) {
                    val intent = Intent(this, PlayActivity::class.java)
                    sharedPreference.save("GameType", spnrGameType.selectedItemId.toInt())
                    sharedPreference.save("GameSize", spnrGameSize.selectedItemId.toInt())
                    sharedPreference.save("UserName", txtUsername.text.toString())
                    startActivity(intent)
                }
            }
            btnScoreBoard -> {
                val intent = Intent(this, ScoreBoardActivity::class.java)
                startActivity(intent)
            }
        }
    }


}
