package cn.com.timeriver.videoplayer.util

import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseFragment
import cn.com.timeriver.videoplayer.presenter.ui.fragment.HomeFragment
import cn.com.timeriver.videoplayer.presenter.ui.fragment.MvFragment
import cn.com.timeriver.videoplayer.presenter.ui.fragment.VbangFragment
import cn.com.timeriver.videoplayer.presenter.ui.fragment.YuedanFragment

/**
 * 延迟加载+线程安全
 * Kotlin单例实现
 * 私有化构造方法
 * 提供公共访问实例的方法
 * 线程安全的延迟初始化
 */
class FragmentFactory private constructor() {

    private val homeFragment by lazy { HomeFragment() }
    private val mvFragment by lazy { MvFragment() }
    private val vbangFragment by lazy { VbangFragment() }
    private val yuedanFragment by lazy { YuedanFragment() }

    companion object {
        val instance by lazy { FragmentFactory() }
    }

    fun getFragment(id: Int): BaseFragment? {
        return when (id) {
            R.id.tab_home -> homeFragment
            R.id.tab_mv -> mvFragment
            R.id.tab_vbang -> vbangFragment
            R.id.tab_yuedan -> yuedanFragment
            else -> null
        }
    }

}