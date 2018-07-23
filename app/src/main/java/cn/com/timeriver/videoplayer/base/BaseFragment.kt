package cn.com.timeriver.videoplayer.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.toast

abstract class BaseFragment : Fragment(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
        initListener()
    }

    /**
     * Framgnet初始化
     */
    private fun init() {

    }

    abstract fun getLayoutId(): Int

    protected open fun initView() {
    }

    protected open fun initData() {
    }

    protected open fun initListener() {
    }

    protected fun myToast(msg: String) {
        activity?.runOnUiThread { toast(msg) }
    }

}