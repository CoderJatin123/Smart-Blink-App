package com.application.smartblink.DataStore

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class IpStore(val context : Context) {
    var  sharedPref : SharedPreferences =
        context.getSharedPreferences("mySharedPreferences", AppCompatActivity.MODE_PRIVATE)

    fun getIp(): String? {
        return sharedPref!!.getString("wifi_ip", null)
    }
    fun setIp(ip : String){
        val editor = sharedPref!!.edit()
        editor!!.putString("wifi_ip", ip)
        editor.apply()
    }
}