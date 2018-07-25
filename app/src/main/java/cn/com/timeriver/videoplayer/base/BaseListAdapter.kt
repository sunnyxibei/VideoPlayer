package cn.com.timeriver.videoplayer.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.com.timeriver.videoplayer.widget.LoadMoreView

/**
 * 查看继承关系的快捷键ctrl+H键
 */
abstract class BaseListAdapter<ITEMBEAN>(itemBean: List<ITEMBEAN>) : RecyclerView.Adapter<BaseHolder>() {

    companion object {
        private const val TYPE_NORMAL = 0
        private const val TYPE_FOOTER = 1
    }

    private var data: List<ITEMBEAN> = itemBean
    //定义函数类型变量
    private var listener: ((itemBean: ITEMBEAN) -> Unit)? = null

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
            holder.itemView.setOnClickListener {
                listener?.invoke(item)
            }
        }
    }

    fun setOnItemClickListener(listener: ((itemBean: ITEMBEAN) -> Unit)) {
        this.listener = listener
    }

    abstract fun getLocalItemView(context: Context?): View?

    abstract fun setLocalData(holder: BaseHolder, itemBean: ITEMBEAN)

}

class BaseHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)