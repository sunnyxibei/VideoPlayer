package cn.com.timeriver.videoplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import cn.com.timeriver.videoplayer.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.info

class MvInnerCard : RelativeLayout, AnkoLogger {

    private val mInnerBackgroundIv: ImageView
    private val mInnerTagIv: ImageView
    private val mInnerAuthorTv: TextView
    private val mInnerCompositionTv: TextView

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.layout_yuedan, this, true)
        mInnerBackgroundIv = find(R.id.iv_yuedan_bg)
        mInnerTagIv = find(R.id.iv_yuedan_tag)
        mInnerAuthorTv = find(R.id.tv_yuedan_author)
        mInnerCompositionTv = find(R.id.tv_yuedan_composition)
    }

    fun setCardBackground(picUrl: String) {
        Glide.with(this)
                .setDefaultRequestOptions(RequestOptions().centerCrop())
                .load(picUrl)
                .into(mInnerBackgroundIv)
    }

    fun setCardTag(picUrl: String) {
        info { picUrl }
        Glide.with(this)
                .setDefaultRequestOptions(RequestOptions().centerCrop().transform(CircleCrop()))
                .load(picUrl)
                .into(mInnerTagIv)
    }

    fun setCardAuthor(author: String) {
        mInnerAuthorTv.text = author
    }

    fun setCardComposition(composition: String) {
        mInnerCompositionTv.text = composition
    }
}