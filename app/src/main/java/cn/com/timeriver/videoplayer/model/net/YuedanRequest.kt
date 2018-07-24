package cn.com.timeriver.videoplayer.model.net

import cn.com.timeriver.videoplayer.model.bean.YuedanItem

class YuedanRequest(url: String, handler: RequestHandler<YuedanItem>) :
        BaseRequest<YuedanItem>(url, handler)