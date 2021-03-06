package cn.com.timeriver.videoplayer.util

/**
 * ClassName:StringUtil
 * Description:处理时间
 */
object StringUtil {

    private const val HOUR = 60 * 60 * 1000
    private const val MIN = 60 * 1000
    private const val SEC = 1000

    fun parseDuration(progress: Int): String {
        val hour = progress / HOUR
        val min = progress % HOUR / MIN
        val sec = progress % MIN / SEC
        return if (hour == 0)
        //不足1小时 不显示小时
            String.format("%02d:%02d", min, sec)
        else
            String.format("%02d:%02d:%02d", hour, min, sec)
    }
}