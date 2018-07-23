package cn.com.timeriver.videoplayer.model.net

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.lang.reflect.ParameterizedType

/**
 * 单例模式的网络请求管理类
 */
class NetManager private constructor() {

    private val client by lazy { OkHttpClient() }

    companion object {
        val instance by lazy { NetManager() }
    }

    fun <RESPONSE> sendRequest(req: BaseRequest<RESPONSE>) {

        val request = Request.Builder()
                .url(req.url)
                .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call?, e: IOException?) {
                req.handler.onFailure(e)
            }

            override fun onResponse(call: Call?, response: Response?) {
                val result = response?.body()?.string()
                val parseResult = parseResult(req, result)
                req.handler.onSuccess(parseResult)
            }

        })
    }

    private fun <RESPONSE> parseResult(req: BaseRequest<RESPONSE>, result: String?): RESPONSE {
        /**
         * 注意这里是通过反射获取参数化范型类型
         * 注意，虽然运行阶段范型被擦除，但是是可以获取到ParameterizedType的
         */
        val type = (req::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return Gson().fromJson<RESPONSE>(result, type)
    }

}