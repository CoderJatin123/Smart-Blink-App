package com.application.smartblink.Boarding

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.application.smartblink.MainActivity
import com.application.smartblink.R
import com.google.android.material.button.MaterialButton

class BoardingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boarding_screen)

        supportActionBar?.hide()

        var  sharedPref : SharedPreferences =
            getSharedPreferences("mySharedPreferences", AppCompatActivity.MODE_PRIVATE)

        if(!sharedPref!!.getString("is_first_time", null).equals(null)){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        else{
            val started_btn: MaterialButton=findViewById(R.id.start_btn)
            val editor = sharedPref!!.edit()
            editor!!.putString("is_first_time", "NOT")
            editor.apply()
            started_btn.setOnClickListener {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }


    }
}