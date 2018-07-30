package cn.com.timeriver.videoplayer.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import cn.com.timeriver.videoplayer.base.Actions
import cn.com.timeriver.videoplayer.base.App
import cn.com.timeriver.videoplayer.model.bean.MusicBean
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*

class MusicService : Service(), AnkoLogger {

    private var position = -1

    private val mediaPlayer by lazy { MediaPlayer() }
    private var musicBeanList: ArrayList<MusicBean>? = null

    override fun onCreate() {
        super.onCreate()
        info { "onCreate" }
    }

    /**
     * 该方法会多次调用
     * 在这里获取intent中的数据
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        info { "onStartCommand" }
        musicBeanList = intent?.getParcelableArrayListExtra<MusicBean>("list")
        //Elvis运算符
        position = intent?.getIntExtra("position", -1) ?: -1
        info { "musicBeanList = $musicBeanList & position = $position" }
        playMusic()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        info { "onBind" }
        return MusicBinder()
    }

    private fun playMusic() {
        musicBeanList?.let {
            val musicBean = musicBeanList?.get(position)
            mediaPlayer.setDataSource(musicBean?.data)
            mediaPlayer.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.start()
                val intent = Intent(Actions.MUSIC_START_PLAY)
                intent.putExtra("music_bean", musicBean)
                LocalBroadcastManager.getInstance(App.getInstance()).sendBroadcast(intent)
            }
            mediaPlayer.prepareAsync()
        }
    }

    private fun switchPlayStatus() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        info { "onUnbind" }
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        info { "onDestroy" }
    }

    inner class MusicBinder : Binder(), IMusicService {

        override fun getCurrentProgress(): Int {
            return mediaPlayer.currentPosition
        }

        override fun getMusicDuration(): Int {
            return mediaPlayer.duration
        }

        override fun isMediaPlaying(): Boolean? {
            return mediaPlayer.isPlaying
        }

        override fun callSwitchPlayStatus() {
            switchPlayStatus()
        }

    }

}