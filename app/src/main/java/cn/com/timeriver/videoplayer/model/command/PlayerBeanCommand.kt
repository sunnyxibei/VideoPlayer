package cn.com.timeriver.videoplayer.model.command

import cn.com.timeriver.videoplayer.model.bean.PlayerBean
import cn.com.timeriver.videoplayer.model.bean.VideosBean

class PlayerBeanCommand(var videosBean: VideosBean) : Command<PlayerBean> {

    override fun execute() = PlayerBean(videosBean.id, videosBean.title, videosBean.url)
}