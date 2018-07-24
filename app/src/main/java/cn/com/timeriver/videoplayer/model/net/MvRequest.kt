package cn.com.timeriver.videoplayer.model.net

import cn.com.timeriver.videoplayer.model.bean.MvAreaBean

class MvRequest(url: String, handler: RequestHandler<List<MvAreaBean>>) :
        BaseRequest<List<MvAreaBean>>(url, handler)