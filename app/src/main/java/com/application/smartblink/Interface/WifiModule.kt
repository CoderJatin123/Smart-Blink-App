package com.application.smartblink.Interface

interface WifiModule {
    fun onResponse(success:Boolean)
    fun onFail()
}