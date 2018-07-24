package cn.com.timeriver.videoplayer.ui.activity

import android.widget.VideoView
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseActivity
import cn.com.timeriver.videoplayer.model.bean.PlayerBean
import org.jetbrains.anko.find

class VideoPlayerActivity : BaseActivity() {

    private lateinit var videoView: VideoView

    override fun getLayoutId() = R.layout.activity_video_player

    override fun initView() {
        videoView = find(R.id.video_view)
    }

    override fun initData() {
        val playerBean = intent.getParcelableExtra<PlayerBean>("item")
        videoView.setVideoPath(playerBean.url)
        videoView.setOnPreparedListener {
            videoView.start()
        }
    }


}