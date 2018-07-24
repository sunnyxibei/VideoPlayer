package cn.com.timeriver.videoplayer.ui.fragment

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.adapter.YuedanAdapter
import cn.com.timeriver.videoplayer.base.BaseFragment
import cn.com.timeriver.videoplayer.model.bean.PlayLists
import cn.com.timeriver.videoplayer.model.bean.YuedanItem
import cn.com.timeriver.videoplayer.presenter.contract.YuedanContract
import cn.com.timeriver.videoplayer.presenter.impl.YuedanPresenter
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.onUiThread
import java.io.IOException

class YuedanFragment : BaseFragment(), YuedanContract.View {

    private lateinit var mYuedanList: RecyclerView
    private lateinit var mRefreshLayout: SwipeRefreshLayout
    private var mPlaylists = arrayListOf<PlayLists>()
    private lateinit var mPresenter: YuedanContract.Presenter

    override fun getLayoutId(): Int {
        return R.layout.fragment_yuedan
    }

    override fun initView() {
        mYuedanList = find(R.id.list_yuedan)
        mRefreshLayout = find(R.id.refresh)

        mYuedanList.layoutManager = LinearLayoutManager(context)
        mYuedanList.adapter = YuedanAdapter(mPlaylists)


        //刷新监听
        mPresenter = YuedanPresenter(this)

        mRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN)
        mRefreshLayout.setOnRefreshListener {
            mPresenter.loadData(0)
        }
        //加载更多监听
        mYuedanList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                //当前滑动已经停止
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = mYuedanList.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val manager: LinearLayoutManager = layoutManager
                        //且最后一行（Footer）已经显示出来
                        if (manager.findLastVisibleItemPosition() == mPlaylists.size) {
                            mPresenter.loadData(mPlaylists.size, false)
                        }
                    }
                }
            }
        })

    }

    override fun initData() {
        mPresenter.loadData(0)
    }

    override fun showOnFailure(e: IOException?) {
        onUiThread {
            myToast("fetch data fail")
            mRefreshLayout.isRefreshing = false
        }
    }

    override fun showOnSuccess(yuedanItem: YuedanItem, reset: Boolean) {
        onUiThread {
            mRefreshLayout.isRefreshing = false
            myToast("fetch data success")
            if (reset) {
                mPlaylists.clear()
            }
            mPlaylists.addAll(yuedanItem.playLists)
            mYuedanList.adapter.notifyDataSetChanged()
        }
    }

}