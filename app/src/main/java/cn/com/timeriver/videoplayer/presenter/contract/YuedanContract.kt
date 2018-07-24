package cn.com.timeriver.videoplayer.presenter.contract

import cn.com.timeriver.videoplayer.base.BaseListPresenter
import cn.com.timeriver.videoplayer.base.BaseListView
import cn.com.timeriver.videoplayer.model.bean.YuedanItem

interface YuedanContract {

    interface Presenter : BaseListPresenter

    interface View : BaseListView<YuedanItem>
}