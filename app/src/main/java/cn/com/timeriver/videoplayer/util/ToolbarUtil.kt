package cn.com.timeriver.videoplayer.util

import android.support.v7.widget.Toolbar

/**
 * 所有界面Toolbar的管理类
 */
interface ToolbarUtil {

    var toolbar: Toolbar

    fun initMainToolbar() {
        toolbar.title = "手机影音"
    }

    fun initSettingsToolbar() {
        toolbar.title = "设置页面"
    }

}