package com.example.mindgame

import android.content.Context
import android.content.Intent
import com.example.mindgame.ui.BaseActivity

class Router {

    fun switchPage(activity: BaseActivity, activity2: BaseActivity) {
        activity.startActivity(Intent(activity, activity2::class.java))

    }

    fun switchPage(context: Context?, activity2: BaseActivity) {
        val i = Intent(context, activity2::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(i)
    }

    fun switchPage(context: Context?, activity2: BaseActivity, itemKey: String, item: String) {
        val i = Intent(context, activity2::class.java)
        i.putExtra(itemKey, item)

        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(i)
    }

    fun switchPage(context: Context?, activity2: BaseActivity, itemKey: String, item: Long) {
        val i = Intent(context, activity2::class.java)
        i.putExtra(itemKey, item)

        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(i)
    }
    fun switchPage(context: Context?, activity2: BaseActivity, itemKey: String, item: Boolean) {
        val i = Intent(context, activity2::class.java)
        i.putExtra(itemKey, item)

        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(i)
    }
    fun switchPage(context: Context?, activity2: BaseActivity, itemKey: String, item: Int) {
        val i = Intent(context, activity2::class.java)
        i.putExtra(itemKey, item)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(i)
    }
    fun switchPage(context: Context?, activity2: BaseActivity, itemKey: String, item: Int, itemKey2: String, item2 :Int) {
        val i = Intent(context, activity2::class.java)
        i.putExtra(itemKey, item)
        i.putExtra(itemKey2,item2)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(i)
    }
    fun switchPage(context: Context?, activity2: BaseActivity, itemKey: String, item: String, itemKey2: String, item2 :String) {
        val i = Intent(context, activity2::class.java)
        i.putExtra(itemKey, item)
        i.putExtra(itemKey2,item2)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(i)
    }
    fun switchPage(context: Context?, activity2: BaseActivity, itemKey: String, item: Int, itemKey2: String, item2 :Int, showKeyboard: Boolean) {
        val i = Intent(context, activity2::class.java)
        i.putExtra(itemKey, item)
        i.putExtra(itemKey2,item2)
        i.putExtra("showKeyboard",showKeyboard)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(i)
    }

}