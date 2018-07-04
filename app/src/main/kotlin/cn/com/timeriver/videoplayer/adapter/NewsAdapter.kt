package cn.com.timeriver.videoplayer.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.com.timeriver.videoplayer.widget.NewsCard

class NewsAdapter : RecyclerView.Adapter<NewsHolder>() {

    override fun getItemCount(): Int {
        return 20
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(NewsCard(parent.context))
    }


    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
    }

}

class NewsHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

}