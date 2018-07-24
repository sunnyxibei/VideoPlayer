package cn.com.timeriver.videoplayer.base

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.com.timeriver.videoplayer.R
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.onUiThread
import java.io.IOException

abstract class BaseListFragment<RESPONSE, ITEMBEAN> : BaseFragment(), BaseListView<RESPONSE> {

    private lateinit var mList: RecyclerView
    private lateinit var mRefreshLayout: SwipeRefreshLayout
    private var mItemBeans = arrayListOf<ITEMBEAN>()
    private val mPresenter by lazy { getLocalPresenter() }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list_standard
    }

    override fun initView() {
        mList = find(R.id.list_news)
        mRefreshLayout = find(R.id.refresh)

        mList.layoutManager = LinearLayoutManager(context)
        mList.adapter = getLocalAdapter(mItemBeans)

        mRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN)
        mRefreshLayout.setOnRefreshListener {
            mPresenter.loadData(0)
        }
        //加载更多监听
        mList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                //当前滑动已经停止
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = mList.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val manager: LinearLayoutManager = layoutManager
                        //且最后一行（Footer）已经显示出来
                        if (manager.findLastVisibleItemPosition() == mItemBeans.size) {
                            mPresenter.loadData(mItemBeans.size, false)
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

    override fun showOnSuccess(response: RESPONSE, reset: Boolean) {
        onUiThread {
            mRefreshLayout.isRefreshing = false
            myToast("fetch data success")
            if (reset) {
                mItemBeans.clear()
            }
            mItemBeans.addAll(getLocalItemBeanList(response))
            mList.adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unSubscribe()
    }

    abstract fun getLocalPresenter(): BaseListPresenter

    abstract fun getLocalItemBeanList(response: RESPONSE): List<ITEMBEAN>

    abstract fun getLocalAdapter(mItemBeans: List<ITEMBEAN>): BaseListAdapter<ITEMBEAN>

}