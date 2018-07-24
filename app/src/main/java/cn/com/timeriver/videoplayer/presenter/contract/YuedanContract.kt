package cn.com.timeriver.videoplayer.presenter.contract

import cn.com.timeriver.videoplayer.model.bean.YuedanItem
import java.io.IOException

interface YuedanContract {

    interface Presenter {
        fun loadData(offset: Int, reset: Boolean = true)
    }

    interface View {
        fun showOnFailure(e: IOException?)
        fun showOnSuccess(yuedanItem: YuedanItem, reset: Boolean)
    }
}