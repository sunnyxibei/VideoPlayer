package cn.com.timeriver.videoplayer.ui.fragment

import cn.com.timeriver.videoplayer.adapter.YuedanAdapter
import cn.com.timeriver.videoplayer.base.BaseListAdapter
import cn.com.timeriver.videoplayer.base.BaseListFragment
import cn.com.timeriver.videoplayer.base.BaseListPresenter
import cn.com.timeriver.videoplayer.model.bean.PlayLists
import cn.com.timeriver.videoplayer.model.bean.YuedanItem
import cn.com.timeriver.videoplayer.presenter.contract.YuedanContract
import cn.com.timeriver.videoplayer.presenter.impl.YuedanPresenter

class YuedanFragment : BaseListFragment<YuedanItem, PlayLists>(), YuedanContract.View {

    override fun getLocalItemBeanList(response: YuedanItem): List<PlayLists> {
        return response.playLists
    }

    override fun getLocalAdapter(mItemBeans: List<PlayLists>): BaseListAdapter<PlayLists> {
        return YuedanAdapter(mItemBeans)
    }

    override fun getLocalPresenter(): BaseListPresenter {
        return YuedanPresenter(this)
    }

}