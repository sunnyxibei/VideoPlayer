package cn.com.timeriver.videoplayer.presenter.contract

import cn.com.timeriver.videoplayer.model.NewsItem

/**
 * {@link HomeFragment}对应的MVP功能接口
 */
interface HomeContract {

    interface Presenter {

        fun loadData(offset: Int, reset: Boolean = true)

    }

    interface View {
        fun showOnFailure()
        fun showOnSuccess(newsItems: List<NewsItem>, reset: Boolean)

    }
}