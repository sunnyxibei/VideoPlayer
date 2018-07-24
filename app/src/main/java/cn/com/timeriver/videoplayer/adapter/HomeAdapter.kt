package cn.com.timeriver.videoplayer.adapter

import android.content.Context
import android.view.View
import cn.com.timeriver.videoplayer.base.BaseHolder
import cn.com.timeriver.videoplayer.base.BaseListAdapter
import cn.com.timeriver.videoplayer.model.bean.HomeItem
import cn.com.timeriver.videoplayer.widget.HomeCard

class HomeAdapter(homeItems: List<HomeItem>) : BaseListAdapter<HomeItem>(homeItems) {

    override fun getLocalItemView(context: Context?): View? {
        return HomeCard(context)
    }

    override fun setLocalData(holder: BaseHolder, itemBean: HomeItem) {
        val card: HomeCard = holder.itemView as HomeCard
        card.setCardAuthor(itemBean.title)
//        card.setCardComposition(itemBean.description)
//        card.setCardTag(itemBean.type)
        card.setCardBackground(itemBean.posterPic)
    }

}