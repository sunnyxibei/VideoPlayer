package cn.com.timeriver.videoplayer.presenter.impl

import cn.com.timeriver.videoplayer.model.bean.MvPagerBean
import cn.com.timeriver.videoplayer.model.net.MvInnerRequest
import cn.com.timeriver.videoplayer.model.net.RequestHandler
import cn.com.timeriver.videoplayer.presenter.contract.MvInnerContract
import cn.com.timeriver.videoplayer.util.URLProviderUtils
import java.io.IOException

class MvInnerPresenter(var mView: MvInnerContract.View?, var area: String) :
        MvInnerContract.Presenter {

    override fun loadData(offset: Int, reset: Boolean) {
        val mvListUrl = URLProviderUtils.getMVListUrl(area, offset, 20)
        MvInnerRequest(mvListUrl, object : RequestHandler<MvPagerBean> {
            override fun onFailure(e: IOException?) {
                mView?.showOnFailure(e)
            }

            override fun onSuccess(response: MvPagerBean?) {
                response?.let {
                    mView?.showOnSuccess(response.videos, reset)
                }
            }

        }).execute()
    }

    override fun unSubscribe() {
        mView?.let { mView = null }
    }

}