package cn.com.timeriver.videoplayer.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.widget.ImageView
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseActivity
import cn.com.timeriver.videoplayer.service.IMusicService
import cn.com.timeriver.videoplayer.service.MusicService
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.imageResource

class MusicPlayerActivity : BaseActivity(), AnkoLogger {

    private lateinit var stateButton: ImageView

    private lateinit var data: Intent
    private var iMusicService: IMusicService? = null
    private val conn by lazy { MusicConnection() }

    override fun getLayoutId() = R.layout.activity_music_player

    override fun initView() {
        stateButton = find(R.id.status)

        data = intent
        data.setClass(this, MusicService::class.java)
        startService(data)
        bindService(data, conn, Context.BIND_AUTO_CREATE)

        stateButton.setOnClickListener {
            iMusicService?.let { iMusicService ->
                iMusicService.callSwitchPlayStatus()
                updatePlayStatusButton(iMusicService.isMediaPlaying())
            }
        }
    }

    private fun updatePlayStatusButton(isMediaPlaying: Boolean?) {
        isMediaPlaying?.let {
            stateButton.imageResource =
                    if (it) R.drawable.selector_btn_audio_pause
                    else R.drawable.selector_btn_audio_play
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(conn)
        stopService(data)
    }

    inner class MusicConnection : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iMusicService = service as IMusicService
        }

    }
}