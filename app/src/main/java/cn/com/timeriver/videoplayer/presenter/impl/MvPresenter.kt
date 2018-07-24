package cn.com.timeriver.videoplayer.presenter.impl

import cn.com.timeriver.videoplayer.model.bean.MvAreaBean
import cn.com.timeriver.videoplayer.model.net.MvRequest
import cn.com.timeriver.videoplayer.model.net.RequestHandler
import cn.com.timeriver.videoplayer.presenter.contract.MvContract
import cn.com.timeriver.videoplayer.util.URLProviderUtils
import java.io.IOException

class MvPresenter(var mView: MvContract.View) : MvContract.Presenter {

    override fun loadData() {
        val mVareaUrl = URLProviderUtils.getMVareaUrl()
        MvRequest(mVareaUrl, object : RequestHandler<List<MvAreaBean>> {
            override fun onFailure(e: IOException?) {
                mView.showOnFailure(e)
            }

            override fun onSuccess(response: List<MvAreaBean>?) {
                response?.let {
                    mView.showOnSuccess(response)
                }
            }

        }).execute()
    }

}