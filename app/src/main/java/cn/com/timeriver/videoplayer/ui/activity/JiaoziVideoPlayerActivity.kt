package cn.com.timeriver.videoplayer.ui.activity

import android.content.Intent
import android.support.v4.view.ViewPager
import android.widget.RadioGroup
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.adapter.MvPlayerAdapter
import cn.com.timeriver.videoplayer.base.BaseActivity
import cn.com.timeriver.videoplayer.model.bean.PlayerBean
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import org.jetbrains.anko.find
import org.jetbrains.anko.info

class JiaoziVideoPlayerActivity : BaseActivity() {

    private lateinit var videoView: JZVideoPlayerStandard
    private lateinit var radioGroup: RadioGroup
    private lateinit var viewPager: ViewPager

    override fun getLayoutId() = R.layout.activity_video_player_jiaozi

    override fun initView() {
        videoView = find(R.id.video_view)
        radioGroup = find(R.id.rg_mv)
        viewPager = find(R.id.mv_lower_pager)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        initData()
    }

    override fun initData() {
        JZVideoPlayer.releaseAllVideos()
        videoView.thumbImageView.setImageResource(R.mipmap.default_thumbnail)
        val data = intent.data
        info { data }
        if (data == null) {
            val playerBean = intent.getParcelableExtra<PlayerBean>("item")
            videoView.setUp(playerBean.url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, playerBean.title)
        } else {
            val dataString = data.toString()
            if (dataString.startsWith("http")) {
                videoView.setUp(dataString, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, dataString)
            } else {
                videoView.setUp(data.path, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, data.path)
            }
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_desc -> viewPager.currentItem = 0
                R.id.rb_comment -> viewPager.currentItem = 1
                R.id.rb_relative -> viewPager.currentItem = 2
            }
        }
        viewPager.adapter = MvPlayerAdapter(supportFragmentManager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                radioGroup.check(
                        when (position) {
                            0 -> R.id.rb_desc
                            1 -> R.id.rb_comment
                            else -> R.id.rb_relative
                        }
                )
            }
        })
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