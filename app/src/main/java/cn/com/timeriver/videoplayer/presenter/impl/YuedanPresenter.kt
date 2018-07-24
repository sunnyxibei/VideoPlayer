package cn.com.timeriver.videoplayer.presenter.impl

import cn.com.timeriver.videoplayer.model.bean.YuedanItem
import cn.com.timeriver.videoplayer.model.net.RequestHandler
import cn.com.timeriver.videoplayer.model.net.YuedanRequest
import cn.com.timeriver.videoplayer.presenter.contract.YuedanContract
import cn.com.timeriver.videoplayer.util.URLProviderUtils
import java.io.IOException

class YuedanPresenter(var mView: YuedanContract.View?) : YuedanContract.Presenter {

    override fun loadData(offset: Int, reset: Boolean) {
        val yueDanUrl = URLProviderUtils.getYueDanUrl(0, 20)
        val handler = object : RequestHandler<YuedanItem> {
            override fun onFailure(e: IOException?) {
                mView?.showOnFailure(e)
            }

            override fun onSuccess(response: YuedanItem?) {
                response?.let {
                    mView?.showOnSuccess(response, reset)
                }
            }
        }
        YuedanRequest(yueDanUrl, handler).execute()
    }

    override fun unSubscribe() {
        mView = null
    }

}