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

class YuedanView : RelativeLayout, AnkoLogger {

    private val mYuedanBackgroundIv: ImageView
    private val mYuedanTagIv: ImageView
    private val mYuedanAuthorTv: TextView
    private val mYuedanCompositionTv: TextView

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.layout_yuedan, this, true)
        mYuedanBackgroundIv = find(R.id.iv_yuedan_bg)
        mYuedanTagIv = find(R.id.iv_yuedan_tag)
        mYuedanAuthorTv = find(R.id.tv_yuedan_author)
        mYuedanCompositionTv = find(R.id.tv_yuedan_composition)
    }

    fun setCardBackground(picUrl: String) {
        Glide.with(this)
                .setDefaultRequestOptions(RequestOptions().centerCrop())
                .load(picUrl)
                .into(mYuedanBackgroundIv)
    }

    fun setCardTag(picUrl: String) {
        info { picUrl }
        Glide.with(this)
                .setDefaultRequestOptions(RequestOptions().centerCrop().transform(CircleCrop()))
                .load("http:$picUrl")
                .into(mYuedanTagIv)
    }

    fun setCardAuthor(author: String) {
        mYuedanAuthorTv.text = author
    }

    fun setCardComposition(composition: String) {
        mYuedanCompositionTv.text = composition
    }


}