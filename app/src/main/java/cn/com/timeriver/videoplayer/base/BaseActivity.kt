package cn.com.timeriver.videoplayer.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

abstract class BaseActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initData()
        initListener()
    }

    abstract fun getLayoutId(): Int

    protected open fun initData() {
    }

    protected open fun initListener() {
    }

    protected fun myToast(msg: String) {
        runOnUiThread { toast(msg) }
    }

    /**
     * 注意学习reified和inline的使用方法
     */
    protected inline fun <reified T : BaseActivity> startActivityAndFinish() {
        startActivity<T>()
        finish()
    }

}