package cn.com.timeriver.videoplayer.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.com.timeriver.videoplayer.model.NewsItem
import cn.com.timeriver.videoplayer.widget.LoadMoreView
import cn.com.timeriver.videoplayer.widget.NewsCard

class NewsAdapter() : RecyclerView.Adapter<NewsHolder>() {

    companion object {
        private const val TYPE_NORMAL = 0
        private const val TYPE_FOOTER = 1
    }

    private lateinit var data: List<NewsItem>

    constructor(newsItems: List<NewsItem>) : this() {
        this.data = newsItems
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == data.size) TYPE_FOOTER else TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val context = parent.context
        return if (viewType == TYPE_NORMAL) {
            NewsHolder(NewsCard(context))
        } else {
            NewsHolder(LoadMoreView(context))
        }
    }


    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val card: NewsCard = holder.itemView as NewsCard
            val newsItem = data[position]
//        card.setCardAuthor(newsItem.title)
//        card.setCardComposition(newsItem.description)
            card.setCardTag(newsItem.type)
            card.setCardBackground(newsItem.posterPic)
        }
    }

}

class NewsHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

}