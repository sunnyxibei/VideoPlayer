package cn.com.timeriver.videoplayer.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.com.timeriver.videoplayer.model.NewsItem
import cn.com.timeriver.videoplayer.widget.NewsCard

class NewsAdapter() : RecyclerView.Adapter<NewsHolder>() {

    private lateinit var data: List<NewsItem>

    constructor(newsItems: List<NewsItem>) : this() {
        this.data = newsItems
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(NewsCard(parent.context))
    }


    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val newsItem = data[position]
//        holder.card.setCardAuthor(newsItem.title)
//        holder.card.setCardComposition(newsItem.description)
        holder.card.setCardTag(newsItem.type)
        holder.card.setCardBackground(newsItem.posterPic)
    }

}

class NewsHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    val card: NewsCard = itemView as NewsCard
}