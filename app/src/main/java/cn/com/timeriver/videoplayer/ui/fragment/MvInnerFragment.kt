package cn.com.timeriver.videoplayer.ui.fragment

import android.os.Bundle
import cn.com.timeriver.videoplayer.adapter.MvInnerAdapter
import cn.com.timeriver.videoplayer.base.BaseListAdapter
import cn.com.timeriver.videoplayer.base.BaseListFragment
import cn.com.timeriver.videoplayer.base.BaseListPresenter
import cn.com.timeriver.videoplayer.model.bean.MvAreaBean
import cn.com.timeriver.videoplayer.model.bean.VideosBean
import cn.com.timeriver.videoplayer.presenter.contract.MvInnerContract
import cn.com.timeriver.videoplayer.presenter.impl.MvInnerPresenter

class MvInnerFragment : BaseListFragment<List<VideosBean>, VideosBean>(), MvInnerContract.View {

    companion object {
        fun getInstance(args: Bundle): MvInnerFragment {
            val fragment = MvInnerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLocalPresenter(): BaseListPresenter {
        val mvAreaBean: MvAreaBean = arguments?.getSerializable("args") as MvAreaBean
        return MvInnerPresenter(this, mvAreaBean.code)
    }

    override fun getLocalItemBeanList(response: List<VideosBean>): List<VideosBean> {
        return response
    }

    override fun getLocalAdapter(mItemBeans: List<VideosBean>): BaseListAdapter<VideosBean> {
        return MvInnerAdapter(mItemBeans)
    }

}