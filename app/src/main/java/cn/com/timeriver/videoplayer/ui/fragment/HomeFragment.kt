package cn.com.timeriver.videoplayer.ui.fragment

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.adapter.NewsAdapter
import cn.com.timeriver.videoplayer.base.BaseFragment
import cn.com.timeriver.videoplayer.model.NewsItem
import cn.com.timeriver.videoplayer.util.ThreadUtil
import cn.com.timeriver.videoplayer.util.URLProviderUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.onUiThread
import java.io.IOException

class HomeFragment : BaseFragment() {

    private lateinit var mNewsList: RecyclerView
    private lateinit var mRefreshLayout: SwipeRefreshLayout
    private val mNewsItems = arrayListOf<NewsItem>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        mNewsList = find(R.id.list_news)
        mRefreshLayout = find(R.id.refresh)

        mNewsList.layoutManager = LinearLayoutManager(context)
        mNewsList.adapter = NewsAdapter(mNewsItems)

        mRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN)
        mRefreshLayout.setOnRefreshListener {
            initData()
        }
    }

    override fun initData() {
        //请求网络数据，并加载RecyclerView
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(URLProviderUtils.getHomeUrl(0, 20))
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                myToast("fetch data fail")
                ThreadUtil.runOnMainThread(Runnable { mRefreshLayout.isRefreshing = false })
            }

            override fun onResponse(call: Call?, response: Response?) {
                ThreadUtil.runOnMainThread(Runnable { mRefreshLayout.isRefreshing = false })
                myToast("fetch data success")
                val result = response?.body()?.string()
                info(result)
                val newsItems: List<NewsItem> = Gson().fromJson<List<NewsItem>>(result, object : TypeToken<List<NewsItem>>() {}.type)
                newsItems.let {
                    onUiThread {
                        refreshNewsList(newsItems)
                    }
                }
            }

        })
    }

    private fun refreshNewsList(newsItems: List<NewsItem>) {
        mNewsItems.clear()
        mNewsItems.addAll(newsItems)
        mNewsList.adapter.notifyDataSetChanged()
    }
}