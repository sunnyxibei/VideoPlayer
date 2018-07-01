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

class SettingsFragment : PreferenceFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        addPreferencesFromResource(R.xml.settings)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?): Boolean {
        when (preference?.key) {
            "about" -> startActivity<AboutActivity>()
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference)
    }
}