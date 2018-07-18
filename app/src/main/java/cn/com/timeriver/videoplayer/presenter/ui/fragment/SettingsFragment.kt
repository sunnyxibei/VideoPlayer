package cn.com.timeriver.videoplayer.presenter.ui.fragment

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.presenter.ui.activity.AboutActivity
import org.jetbrains.anko.startActivity

/**
 * 直接继承系统的PreferenceFragment
 * 结合R.xml.settings
 * 可以迅速实现一个Settings界面
 */
class SettingsFragment : PreferenceFragment() {

    val ABOUT = "about"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        addPreferencesFromResource(R.xml.settings)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?): Boolean {
        when (preference?.key) {
            ABOUT -> startActivity<AboutActivity>()
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference)
    }
}