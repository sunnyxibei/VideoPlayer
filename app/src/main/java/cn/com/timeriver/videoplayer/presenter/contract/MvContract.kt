package cn.com.timeriver.videoplayer.presenter.contract

import cn.com.timeriver.videoplayer.model.bean.MvAreaBean
import java.io.IOException


interface MvContract {

    interface View {
        fun showOnFailure(e: IOException?)
        fun showOnSuccess(mvAreaBeans: List<MvAreaBean>?)
    }

    interface Presenter {
        fun loadData()
    }
}