package cn.com.timeriver.videoplayer.base

import java.io.IOException

interface BaseListView<RESPONSE> {

    fun showOnFailure(e: IOException?)
    fun showOnSuccess(response: RESPONSE, reset: Boolean)

}