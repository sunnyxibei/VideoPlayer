package cn.com.timeriver.videoplayer.base

interface BaseListPresenter {

    fun loadData(offset: Int, reset: Boolean = true)
    fun unSubscribe()

}