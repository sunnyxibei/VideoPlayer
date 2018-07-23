package cn.com.timeriver.videoplayer.model.net

import cn.com.timeriver.videoplayer.model.bean.NewsItem

class HomeRequest(url: String, handler: RequestHandler<List<NewsItem>>) :
        BaseRequest<List<NewsItem>>(url, handler) {
}