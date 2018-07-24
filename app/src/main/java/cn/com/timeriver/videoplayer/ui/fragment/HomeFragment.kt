package cn.com.timeriver.videoplayer.ui.fragment

import cn.com.timeriver.videoplayer.adapter.HomeAdapter
import cn.com.timeriver.videoplayer.base.BaseListAdapter
import cn.com.timeriver.videoplayer.base.BaseListFragment
import cn.com.timeriver.videoplayer.base.BaseListPresenter
import cn.com.timeriver.videoplayer.model.bean.HomeItem
import cn.com.timeriver.videoplayer.presenter.contract.HomeContract
import cn.com.timeriver.videoplayer.presenter.impl.HomePresenter

class HomeFragment : BaseListFragment<List<HomeItem>, HomeItem>(), HomeContract.View {

    override fun getLocalPresenter(): BaseListPresenter {
        return HomePresenter(this)
    }

    override fun getLocalItemBeanList(response: List<HomeItem>): List<HomeItem> {
        return response
    }

    override fun getLocalAdapter(mItemBeans: List<HomeItem>): BaseListAdapter<HomeItem> {
        return HomeAdapter(mItemBeans)
    }
}