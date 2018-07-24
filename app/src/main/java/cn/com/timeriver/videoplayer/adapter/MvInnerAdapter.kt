package cn.com.timeriver.videoplayer.adapter

import android.content.Context
import android.view.View
import cn.com.timeriver.videoplayer.base.BaseHolder
import cn.com.timeriver.videoplayer.base.BaseListAdapter
import cn.com.timeriver.videoplayer.model.bean.VideosBean
import cn.com.timeriver.videoplayer.widget.MvInnerCard

class MvInnerAdapter(videosBeans: List<VideosBean>) : BaseListAdapter<VideosBean>(videosBeans) {

    override fun getLocalItemView(context: Context?): View? {
        return MvInnerCard(context)
    }

    override fun setLocalData(holder: BaseHolder, itemBean: VideosBean) {
        val card = holder.itemView as MvInnerCard
        card.setCardAuthor(itemBean.artistName)
        card.setCardComposition(itemBean.title)
        card.setCardTag(itemBean.albumImg)
        card.setCardBackground(itemBean.posterPic)
    }

}