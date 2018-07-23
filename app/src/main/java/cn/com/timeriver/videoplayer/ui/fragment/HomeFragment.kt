package cn.com.timeriver.videoplayer.ui.fragment

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.adapter.NewsAdapter
import cn.com.timeriver.videoplayer.base.BaseFragment
import cn.com.timeriver.videoplayer.model.bean.NewsItem
import cn.com.timeriver.videoplayer.presenter.contract.HomeContract
import cn.com.timeriver.videoplayer.presenter.impl.HomePresenter
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.onUiThread

class HomeFragment : BaseFragment(), HomeContract.View {

    private lateinit var mNewsList: RecyclerView
    private lateinit var mRefreshLayout: SwipeRefreshLayout
    private val mNewsItems = arrayListOf<NewsItem>()
    private lateinit var mPresenter: HomePresenter

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        mNewsList = find(R.id.list_news)
        mRefreshLayout = find(R.id.refresh)

        mNewsList.layoutManager = LinearLayoutManager(context)
        mNewsList.adapter = NewsAdapter(mNewsItems)
        //刷新监听
        mRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN)
        mRefreshLayout.setOnRefreshListener {
            mPresenter.loadData(0)
        }
        //加载更多监听
        mNewsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                //当前滑动已经停止
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = mNewsList.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val manager: LinearLayoutManager = layoutManager
                        //且最后一行（Footer）已经显示出来
                        if (manager.findLastVisibleItemPosition() == mNewsItems.size) {
                            mPresenter.loadData(mNewsItems.size, false)
                        }
                    }
                }
            }
        })
    }

    override fun initData() {
        mPresenter = HomePresenter(this)
        mPresenter.loadData(0)
    }

    override fun showOnSuccess(newsItems: List<NewsItem>, reset: Boolean) {
        onUiThread {
            mRefreshLayout.isRefreshing = false
            myToast("fetch data success")
            if (reset) {
                mNewsItems.clear()
            }
            mNewsItems.addAll(newsItems)
            mNewsList.adapter.notifyDataSetChanged()
        }
    }

    override fun showOnFailure() {
        myToast("fetch data fail")
        onUiThread { mRefreshLayout.isRefreshing = false }
    }
}