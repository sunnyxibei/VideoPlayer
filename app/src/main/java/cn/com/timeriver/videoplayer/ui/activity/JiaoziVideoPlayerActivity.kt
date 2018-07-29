package cn.com.timeriver.videoplayer.ui.activity

import android.content.Intent
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseActivity
import cn.com.timeriver.videoplayer.model.bean.PlayerBean
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import org.jetbrains.anko.find
import org.jetbrains.anko.info

class JiaoziVideoPlayerActivity : BaseActivity() {

    private lateinit var videoView: JZVideoPlayerStandard

    override fun getLayoutId() = R.layout.activity_video_player_jiaozi

    override fun initView() {
        videoView = find(R.id.video_view)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        initData()
    }

    override fun initData() {
        JZVideoPlayer.releaseAllVideos()
        val data = intent.data
        info { data }
        if (data == null) {
            val playerBean = intent.getParcelableExtra<PlayerBean>("item")
            videoView.setUp(playerBean.url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, playerBean.title)
            videoView.thumbImageView.setImageResource(R.mipmap.about_banner)
        } else {
            val dataString = data.toString()
            if (dataString.startsWith("http")) {
                videoView.setUp(dataString, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, dataString)
            } else {
                videoView.setUp(data.path, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, data.path)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        JZVideoPlayer.releaseAllVideos()
    }

    override fun onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return
        }
        super.onBackPressed()
    }

}