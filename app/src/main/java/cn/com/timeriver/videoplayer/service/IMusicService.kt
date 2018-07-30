package cn.com.timeriver.videoplayer.service

interface IMusicService {
    fun callSwitchPlayStatus()
    fun isMediaPlaying(): Boolean?
}