package cn.com.timeriver.videoplayer.adapter

import android.content.Context
import android.view.View
import cn.com.timeriver.videoplayer.base.BaseHolder
import cn.com.timeriver.videoplayer.base.BaseListAdapter
import cn.com.timeriver.videoplayer.model.bean.PlayLists
import cn.com.timeriver.videoplayer.widget.YuedanCard

class YuedanAdapter(playLists: List<PlayLists>) : BaseListAdapter<PlayLists>(playLists) {

    override fun getLocalItemView(context: Context?): View? {
        return YuedanCard(context)
    }

    override fun setLocalData(holder: BaseHolder, itemBean: PlayLists) {
        val yuedanCard: YuedanCard = holder.itemView as YuedanCard
        yuedanCard.setCardAuthor(itemBean.title)
        yuedanCard.setCardComposition(itemBean.creator.nickName)
        yuedanCard.setCardBackground(itemBean.playListBigPic)
        yuedanCard.setCardTag(itemBean.creator.largeAvatar)
    }

}