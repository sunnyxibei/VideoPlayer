package cn.com.timeriver.videoplayer.model.bean


data class YuedanItem(val playLists: List<PlayLists>)

data class PlayLists(
        val id: Int,
        val title: String,
        val thumbnailPic: String,
        val playListPic: String,
        val playListBigPic: String,
        val videoCount: Int,
        val description: String,
        val category: String,
        val creator: Creator,
        val status: Int,
        val totalViews: Int,
        val totalFavorites: Int,
        val updateTime: String,
        val createdTime: String,
        val integral: Int,
        val weekIntegral: Int,
        val totalUser: Int,
        val rank: Int
)

data class Creator(
        val uid: Int,
        val nickName: String,
        val smallAvatar: String,
        val largeAvatar: String,
        val vipLevel: Int
)