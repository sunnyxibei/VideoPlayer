package cn.com.timeriver.videoplayer.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.support.v4.toast

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initListener()
    }

    /**
     * Framgnet初始化
     */
    private fun init() {

    }

    abstract fun initView(): View?

    protected open fun initData() {
    }

    protected open fun initListener() {
    }

    protected fun myToast(msg: String) {
        activity?.runOnUiThread { toast(msg) }
    }

}