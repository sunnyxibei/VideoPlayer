package cn.com.timeriver.videoplayer.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * 自定义View的过程，就是一步一步地拆分需求地过程
 * 布局
 * 绘制
 * 触摸反馈
 *
 * 注意理解 draw 方法，其中注释说明了绘制的过程和调度过程
 * 注意理解 onSizeChange 方法
 */
class LyricView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        listener = {

        }
    }

    lateinit var listener: ((progress: Int) -> Unit)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }

}