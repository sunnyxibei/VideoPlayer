package cn.com.timeriver.videoplayer.model.net

/**
 * 网络请求POJO基类
 */
open class BaseRequest<RESPONSE>(var url: String, var handler: RequestHandler<RESPONSE>) {

    fun execute() {
        NetManager.instance.sendRequest(this)
    }
}