package cn.com.timeriver.videoplayer.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cn.com.timeriver.videoplayer.ui.fragment.MvPlayerFragment

class MvPlayerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        val args = Bundle()
        args.putInt("position", position)
        return MvPlayerFragment.newInstance(args)
    }

    override fun getCount() = 3

}
