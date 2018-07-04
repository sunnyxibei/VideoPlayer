package cn.com.timeriver.videoplayer.presenter.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.adapter.NewsAdapter
import cn.com.timeriver.videoplayer.base.BaseFragment
import cn.com.timeriver.videoplayer.model.NewsItem
import cn.com.timeriver.videoplayer.util.URLProviderUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.find
import java.io.IOException

class HomeFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        val recyclerView = find<RecyclerView>(R.id.list_news)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = NewsAdapter()

        //请求网络数据，并加载RecyclerView
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(URLProviderUtils.getHomeUrl(0, 20))
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                myToast("Fail to get data")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val result = response?.body()?.string()
                info(result)
                val fromJson: List<NewsItem> = Gson().fromJson<List<NewsItem>>(result, object : TypeToken<List<NewsItem>>() {}.type)
                fromJson.let {
                    println(it)
                }
            }

        })
    }
}