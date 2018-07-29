package cn.com.timeriver.videoplayer.ui.activity

import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseActivity
import cn.com.timeriver.videoplayer.model.bean.PlayerBean
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import org.jetbrains.anko.find

class JiaoziVideoPlayerActivity : BaseActivity() {

    private lateinit var videoView: JZVideoPlayerStandard

    override fun getLayoutId() = R.layout.activity_video_player_jiaozi

    override fun initView() {
        videoView = find(R.id.video_view)
    }

    override fun initData() {
        val playerBean = intent.getParcelableExtra<PlayerBean>("item")
        videoView.setUp(playerBean.url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, playerBean.title)
        videoView.thumbImageView.setImageResource(R.mipmap.about_banner)
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