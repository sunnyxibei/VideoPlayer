package cn.com.timeriver.videoplayer.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import cn.com.timeriver.videoplayer.base.Actions
import cn.com.timeriver.videoplayer.base.App
import cn.com.timeriver.videoplayer.base.Constants
import cn.com.timeriver.videoplayer.model.bean.MusicBean
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.info
import java.util.*

class MusicService : Service(), AnkoLogger {

    private var playMode = Constants.LOOPING
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
        //处理播放同一首歌曲的逻辑
        musicBeanList = intent?.getParcelableArrayListExtra<MusicBean>("list")
        //Elvis运算符
        val paraPosition = intent?.getIntExtra("position", 0) ?: 0
        if (position != paraPosition) {
            position = paraPosition
            info { "musicBeanList = $musicBeanList & position = $position" }
            playMusic()
        } else {
            musicBeanList?.let {
                sendUpdateUiBroadcast(it[position])
                info { "musicBean = ${it[position]}" }
            }
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        info { "onBind" }
        return MusicBinder()
    }

    private fun playMusic() {
        musicBeanList?.let {
            if (position < 0) position += it.size
            position %= (musicBeanList?.size ?: 1)
            val musicBean = it[position]
            mediaPlayer.reset()
            mediaPlayer.setDataSource(musicBean.data)
            mediaPlayer.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.start()
                sendUpdateUiBroadcast(musicBean)
            }
            mediaPlayer.setOnCompletionListener { _ -> autoPlayNext() }
            mediaPlayer.prepareAsync()
        }
    }

    private fun sendUpdateUiBroadcast(musicBean: MusicBean) {
        val intent = Intent(Actions.MUSIC_START_PLAY)
        intent.putExtra("music_bean", musicBean)
        LocalBroadcastManager.getInstance(App.getInstance()).sendBroadcast(intent)
    }

    private fun autoPlayNext() {
        playMode = defaultSharedPreferences.getInt(Constants.PLAY_MODE, Constants.LOOPING)
        when (playMode) {
            Constants.LOOPING -> position++
            Constants.RANDOM -> position = Random().nextInt(musicBeanList?.size ?: 1)
        }
        playMusic()
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
        mediaPlayer.release()
    }

    inner class MusicBinder : Binder(), IMusicService {

        override fun callPlayPre() {
            position--
            playMusic()
        }

        override fun callPlayNext() {
            autoPlayNext()
        }

        override fun callChangePlayMode(): Int {
            playMode = (playMode + 1) % 3
            defaultSharedPreferences.edit().putInt(Constants.PLAY_MODE, playMode).apply()
            return playMode
        }

        override fun callSeekTo(progress: Int) {
            mediaPlayer.seekTo(progress)
        }

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