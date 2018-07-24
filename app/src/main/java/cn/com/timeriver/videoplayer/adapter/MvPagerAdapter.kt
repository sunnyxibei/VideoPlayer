package cn.com.timeriver.videoplayer.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cn.com.timeriver.videoplayer.model.bean.MvAreaBean
import cn.com.timeriver.videoplayer.ui.fragment.MvInnerFragment

class MvPagerAdapter(var mvAreaBeans: List<MvAreaBean>, manager: FragmentManager) : FragmentPagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        val mvAreaBean = mvAreaBeans[position]
        val args = Bundle()
        args.putSerializable("args", mvAreaBean)
        return MvInnerFragment.getInstance(args)
    }

    override fun getCount(): Int {
        return mvAreaBeans.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mvAreaBeans[position].name
    }

}