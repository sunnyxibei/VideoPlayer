package cn.com.timeriver.videoplayer.presenter.ui.activity

import android.preference.PreferenceManager
import android.support.v7.widget.Toolbar
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseActivity
import cn.com.timeriver.videoplayer.util.ToolbarUtil
import org.jetbrains.anko.find

class SettingsActivity : BaseActivity(), ToolbarUtil {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun getLayoutId(): Int {
        return R.layout.activity_settings
    }

    override fun initData() {
        initSettingsToolbar()

        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val push = sp.getBoolean("push", false)
        val noWifi = sp.getBoolean("no_wifi", false)
    }

}