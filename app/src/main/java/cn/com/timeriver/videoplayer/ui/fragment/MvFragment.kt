package cn.com.timeriver.videoplayer.ui.fragment

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.adapter.MvPagerAdapter
import cn.com.timeriver.videoplayer.base.BaseFragment
import cn.com.timeriver.videoplayer.model.bean.MvAreaBean
import cn.com.timeriver.videoplayer.presenter.contract.MvContract
import cn.com.timeriver.videoplayer.presenter.impl.MvPresenter
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.onUiThread
import java.io.IOException

class MvFragment : BaseFragment(), MvContract.View {

    private lateinit var mTabMv: TabLayout
    private lateinit var mPagerMv: ViewPager
    private val mPresenter by lazy { MvPresenter(this) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mv
    }

    override fun initView() {
        mTabMv = find(R.id.tab_layout_mv)
        mPagerMv = find(R.id.pager_mv)
    }

    override fun initData() {
        mPresenter.loadData()
    }

    override fun showOnFailure(e: IOException?) {
        onUiThread {
            myToast(e?.message.toString())
        }
    }

    override fun showOnSuccess(mvAreaBeans: List<MvAreaBean>?) {
        onUiThread {
            mvAreaBeans?.let {
                mPagerMv.adapter = MvPagerAdapter(mvAreaBeans, childFragmentManager)
                mTabMv.setupWithViewPager(mPagerMv)
            }
        }
    }

}