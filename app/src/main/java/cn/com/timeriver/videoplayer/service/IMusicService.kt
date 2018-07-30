package cn.com.timeriver.videoplayer.service

interface IMusicService {

    fun callSwitchPlayStatus()

    fun isMediaPlaying(): Boolean?

    fun getMusicDuration(): Int

    fun getCurrentProgress(): Int

    fun callSeekTo(progress: Int)

    fun callChangePlayMode(): Int

}