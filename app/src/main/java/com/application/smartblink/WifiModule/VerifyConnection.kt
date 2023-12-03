package com.application.smartblink.WifiModule

import android.os.AsyncTask
import android.util.Log
import com.application.smartblink.Interface.WifiModule
import okhttp3.OkHttpClient
import okhttp3.Request

class VerifyWifiCommunication(private val callback: WifiModule, val ip:String) :
    AsyncTask<String, Void, Void>() {

    override fun doInBackground(vararg params: String?): Void? {
        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("http://${ip}/")
                .get()
                .build()

            client.newCall(request).execute().use { response ->
                Log.d("TAG", "doInBackground: "+response.toString())
                if (response.isSuccessful)
                    callback.onResponse(true)
                else
                    callback.onResponse(false)
            }
        } catch (e: Exception) {
            Log.d("TAG", "doInBackground: "+e)
            callback.onFail()
        }
        return null
    }
}