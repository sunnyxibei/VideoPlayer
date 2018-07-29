package cn.com.timeriver.videoplayer.ui.fragment

import android.os.Bundle
import cn.com.timeriver.videoplayer.adapter.MvInnerAdapter
import cn.com.timeriver.videoplayer.base.BaseListAdapter
import cn.com.timeriver.videoplayer.base.BaseListFragment
import cn.com.timeriver.videoplayer.base.BaseListPresenter
import cn.com.timeriver.videoplayer.model.bean.MvAreaBean
import cn.com.timeriver.videoplayer.model.bean.VideosBean
import cn.com.timeriver.videoplayer.model.command.PlayerBeanCommand
import cn.com.timeriver.videoplayer.presenter.contract.MvInnerContract
import cn.com.timeriver.videoplayer.presenter.impl.MvInnerPresenter
import cn.com.timeriver.videoplayer.ui.activity.IjkVideoPlayerActivity
import cn.com.timeriver.videoplayer.ui.activity.TextureVideoPlayerActivity
import cn.com.timeriver.videoplayer.ui.activity.VideoPlayerActivity
import org.jetbrains.anko.support.v4.startActivity

class MvInnerFragment : BaseListFragment<List<VideosBean>, VideosBean>(), MvInnerContract.View {

    companion object {
        fun getInstance(args: Bundle): MvInnerFragment {
            val fragment = MvInnerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLocalPresenter(): BaseListPresenter {
        val mvAreaBean: MvAreaBean = arguments?.getParcelable("args") as MvAreaBean
        return MvInnerPresenter(this, mvAreaBean.code)
    }

    override fun getLocalItemBeanList(response: List<VideosBean>): List<VideosBean> {
        return response
    }

    override fun getLocalAdapter(mItemBeans: List<VideosBean>): BaseListAdapter<VideosBean> {
        val mvInnerAdapter = MvInnerAdapter(mItemBeans)
        mvInnerAdapter.setOnItemClickListener {
            startActivity<IjkVideoPlayerActivity>("item" to PlayerBeanCommand(it).execute())
        }
        return mvInnerAdapter
    }

}