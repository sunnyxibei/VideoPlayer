package cn.com.timeriver.videoplayer.base

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication

class App : MultiDexApplication() {

    companion object {
        private lateinit var instance: App
        fun getInstance(): App {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}