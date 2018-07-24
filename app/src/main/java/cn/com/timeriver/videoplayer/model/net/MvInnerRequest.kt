package cn.com.timeriver.videoplayer.model.net

import cn.com.timeriver.videoplayer.model.bean.MvPagerBean

class MvInnerRequest(url: String, handler: RequestHandler<MvPagerBean>) :
        BaseRequest<MvPagerBean>(url, handler)