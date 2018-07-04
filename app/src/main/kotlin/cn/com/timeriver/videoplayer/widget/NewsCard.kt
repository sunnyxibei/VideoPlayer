package cn.com.timeriver.videoplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import cn.com.timeriver.videoplayer.R

/**
 * 注意继承父类的构造方法
 * Kotlin不能默认生成代码。。。
 * 了解并学习Kotlin的构造方法
 */
class NewsCard : RelativeLayout {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.layout_news_card, this, true)
    }

}