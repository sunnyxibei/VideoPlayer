package cn.com.timeriver.videoplayer.ui.activity

import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseActivity
import cn.com.timeriver.videoplayer.model.bean.MusicBean
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MusicPlayerActivity : BaseActivity(), AnkoLogger {

    override fun getLayoutId() = R.layout.activity_music_player

    override fun initView() {
        val musicBeanList = intent.getParcelableArrayListExtra<MusicBean>("list")
        val position = intent.getIntExtra("position", -1)

        info { "musicBeanList = $musicBeanList & position = $position" }
    }

}