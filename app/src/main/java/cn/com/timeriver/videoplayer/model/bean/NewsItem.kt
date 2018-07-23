package cn.com.timeriver.videoplayer.model.bean

data class NewsItem(
        val type: String,
        val subType: String,
        val id: Int,
        val title: String,
        val description: String,
        val posterPic: String,
        val thumbnailPic: String,
        val url: String,
        val hdUrl: String,
        val uhdUrl: String,
        val videoSize: Int,
        val hdVideoSize: Int,
        val uhdVideoSize: Int,
        val status: Int,
        val traceUrl: String,
        val clickUrl: String
)
