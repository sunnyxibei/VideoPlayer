package cn.com.timeriver.videoplayer.presenter.impl

import cn.com.timeriver.videoplayer.model.NewsItem
import cn.com.timeriver.videoplayer.presenter.contract.HomeContract
import cn.com.timeriver.videoplayer.util.URLProviderUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.io.IOException

class HomePresenter(var mView: HomeContract.View) : HomeContract.Presenter, AnkoLogger {

    override fun loadData(offset: Int, reset: Boolean) {
        //请求网络数据，并加载RecyclerView
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(URLProviderUtils.getHomeUrl(offset, 20))
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                mView.showOnFailure()
            }

            override fun onResponse(call: Call?, response: Response?) {
                val result = response?.body()?.string()
                info { result }
                val newsItems: List<NewsItem> = Gson().fromJson<List<NewsItem>>(result, object : TypeToken<List<NewsItem>>() {}.type)
                newsItems.let {
                    mView.showOnSuccess(newsItems, reset)
                }
            }

        })
    }

}