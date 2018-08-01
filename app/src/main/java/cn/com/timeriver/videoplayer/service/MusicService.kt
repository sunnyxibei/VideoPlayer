package cn.com.timeriver.videoplayer.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import android.widget.RemoteViews
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.Actions
import cn.com.timeriver.videoplayer.base.App
import cn.com.timeriver.videoplayer.base.Constants
import cn.com.timeriver.videoplayer.model.bean.MusicBean
import cn.com.timeriver.videoplayer.ui.activity.MainActivity
import cn.com.timeriver.videoplayer.ui.activity.MusicPlayerActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.info
import java.util.*

class MusicService : Service(), AnkoLogger {

    private var playMode = Constants.LOOPING
    private var position = -1
    private val MUSIC_ID = 1

    private val FROM_CONTENT = 0
    private val FROM_PRE = 1
    private val FROM_STATUS = 2
    private val FROM_NEXT = 3

    private lateinit var notification: Notification
    private val mediaPlayer by lazy { MediaPlayer() }
    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
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
        //处理从状态栏点击进入的逻辑
        val from = intent?.getIntExtra("from", -1)
        when (from) {
            FROM_CONTENT -> musicBeanList?.let {
                sendUpdateUiBroadcast(it[position])
                info { "musicBean = ${it[position]}" }
            }
            FROM_PRE -> {
                position--
                playMusic()
            }
            FROM_STATUS -> musicBeanList?.let {
                sendUpdateUiBroadcast(it[position])
                info { "musicBean = ${it[position]}" }
                switchPlayStatus()
                notifyPlayStatusChanged(it[position])
            }
            FROM_NEXT -> {
                autoPlayNext()
            }
            else -> {
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
            }
        }
        return START_NOT_STICKY
    }

    private fun notifyPlayStatusChanged(musicBean: MusicBean) {
        sendUpdateUiBroadcast(musicBean)
        if (mediaPlayer.isPlaying) {
            notification.contentView.setImageViewResource(R.id.state, R.mipmap.btn_audio_play_normal)
        } else {
            notification.contentView.setImageViewResource(R.id.state, R.mipmap.btn_audio_pause_normal)
        }
        notificationManager.notify(MUSIC_ID, notification)
    }

    private fun showNotification() {
        //显示通知
        val intents = arrayOf(
                Intent(this, MainActivity::class.java),
                Intent(this, MusicPlayerActivity::class.java).putExtra("from", FROM_CONTENT)
        )
        val pendingIntent = PendingIntent.getActivities(this, FROM_CONTENT, intents, PendingIntent.FLAG_UPDATE_CURRENT)
        notification = NotificationCompat.Builder(this, "music")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setCustomContentView(getRemoteView())
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .build()
        notificationManager.notify(MUSIC_ID, notification)
    }

    private fun getRemoteView(): RemoteViews? {
        val remoteViews = RemoteViews(packageName, R.layout.notification)
        //修改标题和内容
        remoteViews.setTextViewText(R.id.title, musicBeanList?.get(position)?.title)
        remoteViews.setTextViewText(R.id.artist, musicBeanList?.get(position)?.artist)
        //处理上一曲 下一曲  状态点击事件
        remoteViews.setOnClickPendingIntent(R.id.pre, getPrePendingIntent())
        remoteViews.setOnClickPendingIntent(R.id.state, getStatePendingIntent())
        remoteViews.setOnClickPendingIntent(R.id.next, getNextPendingIntent())
        return remoteViews
    }

    private fun getNextPendingIntent(): PendingIntent? {
        val intent = Intent(this, MusicService::class.java)
        intent.putExtra("from", FROM_NEXT)
        return PendingIntent.getService(this, FROM_NEXT, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getStatePendingIntent(): PendingIntent? {
        val intent = Intent(this, MusicService::class.java)
        intent.putExtra("from", FROM_STATUS)
        return PendingIntent.getService(this, FROM_STATUS, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getPrePendingIntent(): PendingIntent? {
        val intent = Intent(this, MusicService::class.java)
        intent.putExtra("from", FROM_PRE)
        return PendingIntent.getService(this, FROM_PRE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
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
                showNotification()
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

        override fun callSwitchPlayPosition(newPosition: Int) {
            position = newPosition
            playMusic()
        }

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