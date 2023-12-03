package com.application.smartblink

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.application.smartblink.DataStore.IpStore
import com.application.smartblink.Interface.WifiModule
import com.application.smartblink.Service.MyNotificationListener.Companion.isNotificationServiceEnabled
import com.application.smartblink.WifiModule.SendDataTask
import com.application.smartblink.WifiModule.VerifyWifiCommunication
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity(),WifiModule {
    lateinit var add_device_btn : MaterialButton
    lateinit var connextion_status: TextView
    lateinit var device_info: TextView
    lateinit var device_ip: EditText
    var isAddDevice: Boolean=true
    var isConnected: Boolean=false
    lateinit var round_shape :ImageView
    lateinit var ipStore : IpStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        init()

        if (!isNotificationServiceEnabled(this)) {
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivity(intent)
        }

        add_device_btn.setOnClickListener {
            if(isAddDevice) {
                device_ip.visibility = View.VISIBLE
                add_device_btn.text = "Connect"
                isAddDevice=false
            }
            else{
                VerifyWifiCommunication(this,device_ip.text.toString()).execute()
                ipStore.setIp(device_ip.text.toString())
            }

        }
    }

    fun init() {
        add_device_btn=findViewById(R.id.add_device_btn)
        device_info=findViewById(R.id.device_info)
        connextion_status=findViewById(R.id.connection_status)
        device_ip=findViewById(R.id.ip)
        round_shape=findViewById(R.id.round_shape)
        ipStore= IpStore(this)

        val ip = ipStore.getIp()
        Log.d("TAG", "init: $ip")
        if (!ip.equals(null)){
            VerifyWifiCommunication(this,ip.toString()).execute()
        }
    }

    override fun onResponse(success: Boolean) {

        if(success){
            runOnUiThread {
                device_ip.visibility = View.GONE
                add_device_btn.visibility = View.GONE
                isAddDevice = false
                connextion_status.text = "Connected"
                round_shape.backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.green))
                device_info.visibility=View.VISIBLE
                val info = "Device - esp8266 \n\n ip - ${ipStore.getIp()}"
                device_info.text=info
                isConnected=true
            }

        }
    }

    override fun onFail() {
        runOnUiThread {
                if(isAddDevice)
                    Toast.makeText(
                        baseContext,
                        "Some error occurred. Please check connection.",
                        Toast.LENGTH_SHORT
                    ).show()
        }
        isAddDevice=false

    }
}