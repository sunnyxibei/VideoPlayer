package cn.com.timeriver.videoplayer.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.com.timeriver.videoplayer.widget.LoadMoreView

abstract class BaseListAdapter<ITEMBEAN>(itemBean: List<ITEMBEAN>) : RecyclerView.Adapter<BaseHolder>() {

    companion object {
        private const val TYPE_NORMAL = 0
        private const val TYPE_FOOTER = 1
    }

    private var data: List<ITEMBEAN> = itemBean

    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == data.size) TYPE_FOOTER else TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val context = parent.context
        return if (viewType == TYPE_NORMAL) {
            BaseHolder(getLocalItemView(context))
        } else {
            BaseHolder(LoadMoreView(context))
        }
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val item = data[position]
            setLocalData(holder, item)
        }
    }

    abstract fun getLocalItemView(context: Context?): View?

    abstract fun setLocalData(holder: BaseHolder, itemBean: ITEMBEAN)

}

class BaseHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)