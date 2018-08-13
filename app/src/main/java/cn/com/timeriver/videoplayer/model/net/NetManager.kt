package cn.com.timeriver.videoplayer.model.net

import android.os.Environment
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.io.File
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.util.concurrent.TimeUnit

/**
 * 单例模式的网络请求管理类
 */
class NetManager private constructor() : AnkoLogger {

    private val client by lazy {
        val cacheFile = File(Environment.getExternalStorageDirectory(), "/okhttp")
        OkHttpClient.Builder()
                .addInterceptor { it.proceed(it.request()) }
                .cache(Cache(cacheFile, 1024 * 1024 * 100))
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
    }

    companion object {
        val instance by lazy { NetManager() }
    }

    fun <RESPONSE> sendRequest(req: BaseRequest<RESPONSE>) {

        val cacheControl = CacheControl.Builder()
                .maxAge(60, TimeUnit.SECONDS)
                .build()
        val request = Request.Builder()
                .cacheControl(cacheControl)
                .url(req.url)
                .build()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call?, e: IOException?) {
                req.handler.onFailure(e)
            }

            override fun onResponse(call: Call?, response: Response?) {
                val currentThread = Thread.currentThread()
                info {
                    "ThreadName : ${currentThread.name} & ThreadId : ${currentThread.id}"
                }
                val result = response?.body()?.string()
                info { result }
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