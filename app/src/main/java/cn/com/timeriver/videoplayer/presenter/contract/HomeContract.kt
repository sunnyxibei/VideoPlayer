package cn.com.timeriver.videoplayer.presenter.contract

import cn.com.timeriver.videoplayer.base.BaseListPresenter
import cn.com.timeriver.videoplayer.base.BaseListView
import cn.com.timeriver.videoplayer.model.bean.HomeItem

/**
 * {@link HomeFragment}对应的MVP功能接口
 */
interface HomeContract {

    interface Presenter: BaseListPresenter

    interface View : BaseListView<List<HomeItem>>
}