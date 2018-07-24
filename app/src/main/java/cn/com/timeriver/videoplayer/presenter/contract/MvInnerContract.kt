package cn.com.timeriver.videoplayer.presenter.contract

import cn.com.timeriver.videoplayer.base.BaseListPresenter
import cn.com.timeriver.videoplayer.base.BaseListView
import cn.com.timeriver.videoplayer.model.bean.VideosBean

interface MvInnerContract {

    interface Presenter : BaseListPresenter

    interface View : BaseListView<List<VideosBean>>

}