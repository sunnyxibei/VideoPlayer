package cn.com.timeriver.videoplayer.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.com.timeriver.videoplayer.model.bean.PlayLists
import cn.com.timeriver.videoplayer.widget.LoadMoreView
import cn.com.timeriver.videoplayer.widget.YuedanView

class YuedanAdapter(playLists: List<PlayLists>) : RecyclerView.Adapter<YuedanHolder>() {

    companion object {
        private const val TYPE_NORMAL = 0
        private const val TYPE_FOOTER = 1
    }

    private var data: List<PlayLists> = playLists

    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == data.size) TYPE_FOOTER else TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YuedanHolder {
        val context = parent.context
        return if (viewType == TYPE_NORMAL) {
            YuedanHolder(YuedanView(context))
        } else {
            YuedanHolder(LoadMoreView(context))
        }
    }


    override fun onBindViewHolder(holder: YuedanHolder, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val yuedanView: YuedanView = holder.itemView as YuedanView
            val item = data[position]
            yuedanView.setCardAuthor(item.title)
            yuedanView.setCardComposition(item.creator.nickName)
            yuedanView.setCardBackground(item.playListBigPic)
            yuedanView.setCardTag(item.creator.largeAvatar)
        }
    }

}

class YuedanHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)