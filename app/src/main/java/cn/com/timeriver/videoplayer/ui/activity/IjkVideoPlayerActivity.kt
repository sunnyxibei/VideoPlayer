package cn.com.timeriver.videoplayer.ui.activity

import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseActivity
import cn.com.timeriver.videoplayer.model.bean.PlayerBean
import com.dou361.ijkplayer.widget.IjkVideoView
import org.jetbrains.anko.find

class IjkVideoPlayerActivity : BaseActivity() {

    private lateinit var videoView: IjkVideoView

    override fun getLayoutId() = R.layout.activity_video_player_ijk

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

    override fun onDestroy() {
        super.onDestroy()
        videoView.stopPlayback()
    }

}