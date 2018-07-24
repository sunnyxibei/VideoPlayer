package cn.com.timeriver.videoplayer.model.net

import java.io.IOException

/**
 * 处理网络返回结果
 */
interface RequestHandler<RESPONSE> {

    fun onFailure(e: IOException?)
    fun onSuccess(response: RESPONSE?)

}