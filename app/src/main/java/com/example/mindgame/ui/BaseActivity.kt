package com.example.mindgame.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.tapadoo.alerter.Alerter

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
    fun showAlert(message: String, activity: Activity, color: Int) {
        Alerter.create(activity)
            .setText(message)
            .setBackgroundColorRes(color)
            .setContentGravity(Gravity.CENTER_HORIZONTAL)
            .setDuration(3000)
            .hideIcon()
            .show()

    }
    fun getActivityContext(): Context? {
        return this
    }
    fun getActivity(): Activity? {
        return this
    }
}