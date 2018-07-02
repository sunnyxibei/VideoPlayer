package cn.com.timeriver.videoplayer.presenter.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.adapter.NewsAdapter
import cn.com.timeriver.videoplayer.base.BaseFragment
import org.jetbrains.anko.support.v4.find

class HomeFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        val recyclerView = find<RecyclerView>(R.id.list_news)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = NewsAdapter()
    }

}