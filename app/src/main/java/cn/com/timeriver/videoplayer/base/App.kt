package cn.com.timeriver.videoplayer.base

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication

class App : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}