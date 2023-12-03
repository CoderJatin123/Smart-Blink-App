package com.application.smartblink.WifiModule

import android.os.AsyncTask
import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SendDataTask(private val data: String) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg voids: Void?): String? {
        try {
            val client = OkHttpClient()
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = data.toRequestBody(mediaType)

            val request = Request.Builder()
                .url("http://192.168.36.242/")
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                Log.d("TAG", "doInBackground: "+response.toString())
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }
                else{
                    Log.d("TAG", "doInBackground: message successfully send"+response.body)
                    return response.message
                }
            }
        } catch (e: Exception) {
            return e.printStackTrace().toString()
        }
    }
}