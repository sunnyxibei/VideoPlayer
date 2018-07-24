package cn.com.timeriver.videoplayer.model.net

import cn.com.timeriver.videoplayer.model.bean.HomeItem

class HomeRequest(url: String, handler: RequestHandler<List<HomeItem>>) :
        BaseRequest<List<HomeItem>>(url, handler)