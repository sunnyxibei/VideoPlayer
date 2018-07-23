package cn.com.timeriver.videoplayer.presenter.impl

import cn.com.timeriver.videoplayer.model.bean.NewsItem
import cn.com.timeriver.videoplayer.model.net.HomeRequest
import cn.com.timeriver.videoplayer.model.net.RequestHandler
import cn.com.timeriver.videoplayer.presenter.contract.HomeContract
import cn.com.timeriver.videoplayer.util.URLProviderUtils
import org.jetbrains.anko.AnkoLogger
import java.io.IOException

class HomePresenter(var mView: HomeContract.View) : HomeContract.Presenter, AnkoLogger {

    override fun loadData(offset: Int, reset: Boolean) {
        //请求网络数据，并加载RecyclerView
        HomeRequest(URLProviderUtils.getHomeUrl(offset, 20),
                object : RequestHandler<List<NewsItem>> {
                    override fun onFailure(e: IOException?) {
                        mView.showOnFailure()

                    }

                    override fun onSuccess(response: List<NewsItem>) {
                        response.let {
                            mView.showOnSuccess(response, reset)
                        }
                    }
                }).execute()
    }

}