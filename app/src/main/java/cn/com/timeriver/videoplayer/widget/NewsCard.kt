package cn.com.timeriver.videoplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import cn.com.timeriver.videoplayer.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.find

/**
 * 注意继承父类的构造方法
 *
 * 了解并学习Kotlin的构造方法
 */
class NewsCard : RelativeLayout {

    private val mCardBackgroundIv: ImageView
    private val mCardTagIv: ImageView
    private val mCardAuthorTv: TextView
    private val mCardCompositionTv: TextView

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val glideOption: RequestOptions = RequestOptions().centerCrop()

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.layout_news_card, this, true)
        mCardBackgroundIv = find(R.id.iv_news_card_bg)
        mCardTagIv = find(R.id.iv_news_card_tag)
        mCardAuthorTv = find(R.id.tv_news_card_author)
        mCardCompositionTv = find(R.id.tv_news_card_composition)
    }

    fun setCardBackground(picUrl: String) {
        Glide.with(this)
                .applyDefaultRequestOptions(glideOption)
                .load(picUrl)
                .into(mCardBackgroundIv)
    }

    fun setCardTag(type: String) {
        val tag = when (type) {
            "VIDEO" -> R.mipmap.home_page_video
            "AD" -> R.mipmap.home_page_ad
            "ACTIVITY" -> R.mipmap.home_page_activity
            "BULLETIN" -> R.mipmap.home_page_bulletin
            "FANART" -> R.mipmap.home_page_fanart
            "LIVE" -> R.mipmap.home_page_live
            "LIVE_NEW" -> R.mipmap.home_page_live_new
            "PLAYLIST" -> R.mipmap.home_page_playlist
            "PROGRAM" -> R.mipmap.home_page_program
            "PROJECT" -> R.mipmap.home_page_project
            else -> R.mipmap.home_page_star
        }
        mCardTagIv.setBackgroundResource(tag)
    }

    fun setCardAuthor(author: String) {
        mCardAuthorTv.text = author
    }

    fun setCardComposition(composition: String) {
        mCardCompositionTv.text = composition
    }

}