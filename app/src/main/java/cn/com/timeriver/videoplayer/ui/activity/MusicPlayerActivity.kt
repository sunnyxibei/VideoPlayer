package cn.com.timeriver.videoplayer.ui.activity

import android.annotation.SuppressLint
import android.content.*
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.*
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.Actions
import cn.com.timeriver.videoplayer.base.App
import cn.com.timeriver.videoplayer.base.BaseActivity
import cn.com.timeriver.videoplayer.base.Constants
import cn.com.timeriver.videoplayer.model.bean.MusicBean
import cn.com.timeriver.videoplayer.service.IMusicService
import cn.com.timeriver.videoplayer.service.MusicService
import cn.com.timeriver.videoplayer.util.StringUtil
import org.jetbrains.anko.*
import java.util.*

class MusicPlayerActivity : BaseActivity(), AnkoLogger {

    private val UPDATE_PROGRESS = 1

    private lateinit var stateButton: ImageView
    private lateinit var audioTitle: TextView
    private lateinit var audioArtist: TextView
    private lateinit var backView: View
    private lateinit var audioAnim: ImageView
    private lateinit var progressTv: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var modeIv: ImageView
    private lateinit var preIv: ImageView
    private lateinit var nextIv: ImageView
    private lateinit var playListIv: ImageView

    private lateinit var data: Intent
    private lateinit var parseDuration: String

    private lateinit var animationDrawable: AnimationDrawable
    private var iMusicService: IMusicService? = null
    private var musicBeanList: ArrayList<MusicBean>? = null
    private val conn by lazy { MusicConnection() }

    override fun getLayoutId() = R.layout.activity_music_player

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Actions.MUSIC_START_PLAY -> initPlayView(intent)
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            if (msg?.what == UPDATE_PROGRESS) {
                scheduleTimerTask()
            }
        }
    }

    override fun initView() {
        stateButton = find(R.id.status)
        audioTitle = find(R.id.audio_title)
        audioArtist = find(R.id.artist)
        backView = find(R.id.back)
        audioAnim = find(R.id.audio_anim)
        progressTv = find(R.id.tv_progress)
        seekBar = find(R.id.progress_sk)
        modeIv = find(R.id.mode)
        preIv = find(R.id.pre)
        nextIv = find(R.id.next)
        playListIv = find(R.id.playlist)

        data = Intent(intent)
        data.setClass(this, MusicService::class.java)
        startService(data)
        bindService(data, conn, Context.BIND_AUTO_CREATE)

        musicBeanList = intent?.getParcelableArrayListExtra<MusicBean>("list")

        stateButton.setOnClickListener {
            iMusicService?.callSwitchPlayStatus()
            updatePlayStatusButton(iMusicService?.isMediaPlaying())
        }

        val filter = IntentFilter()
        filter.addAction(Actions.MUSIC_START_PLAY)
        LocalBroadcastManager.getInstance(App.getInstance()).registerReceiver(mReceiver, filter)

        backView.setOnClickListener { onBackPressed() }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    seekBar?.let {
                        iMusicService?.callSeekTo(it.progress)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        updatePlayModeButton(defaultSharedPreferences.getInt(Constants.PLAY_MODE, Constants.LOOPING))
        modeIv.setOnClickListener { updatePlayModeButton(iMusicService?.callChangePlayMode()) }
        playListIv.setOnClickListener { showBottomSheetDialog() }
    }

    private fun showBottomSheetDialog() {
        musicBeanList?.let {
            val sheetDialog = BottomSheetDialog(this)
            val listView = ListView(this)
            listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, it)
            listView.setOnItemClickListener { parent, view, position, id ->
                iMusicService?.callSwitchPlayPosition(position)
                sheetDialog.dismiss()
            }
            sheetDialog.setContentView(listView)
            sheetDialog.show()
        }
    }

    private fun updatePlayModeButton(playMode: Int?) {
        modeIv.imageResource = when (playMode) {
            Constants.SINGLE -> R.drawable.selector_btn_playmode_single
            Constants.RANDOM -> R.drawable.selector_btn_playmode_random
            else -> R.drawable.selector_btn_playmode_order
        }
    }

    /**
     * 开始播放时要初始化一些界面View
     */
    private fun initPlayView(intent: Intent) {
        val musicBean = intent.getParcelableExtra<MusicBean>("music_bean")
        //获取当前播放条目的时长，并设定给ProgressBar
        val duration = iMusicService?.getMusicDuration()
        duration?.let {
            parseDuration = StringUtil.parseDuration(duration)
            seekBar.max = duration
            scheduleTimerTask()
        }

        audioTitle.text = musicBean.title
        audioArtist.text = musicBean.artist
        animationDrawable = audioAnim.image as AnimationDrawable
        animationDrawable.start()
        updatePlayStatusButton(iMusicService?.isMediaPlaying())
        preIv.setOnClickListener { iMusicService?.callPlayPre() }
        nextIv.setOnClickListener { iMusicService?.callPlayNext() }
    }

    private fun scheduleTimerTask() {
        iMusicService?.let {
            val currentProgress = it.getCurrentProgress()
            seekBar.progress = currentProgress
            val parseProgress = StringUtil.parseDuration(currentProgress)
            progressTv.text = "$parseProgress / $parseDuration"
            handler.sendEmptyMessage(UPDATE_PROGRESS)
        }
    }

    private fun updatePlayStatusButton(isMediaPlaying: Boolean?) {
        isMediaPlaying?.let {
            if (it) {
                stateButton.imageResource = R.drawable.selector_btn_audio_play
                animationDrawable.start()
                scheduleTimerTask()
            } else {
                stateButton.imageResource = R.drawable.selector_btn_audio_pause
                animationDrawable.stop()
                handler.removeCallbacksAndMessages(null)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(App.getInstance()).unregisterReceiver(mReceiver)
        handler.removeCallbacksAndMessages(null)
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