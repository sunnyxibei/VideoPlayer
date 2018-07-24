package cn.com.timeriver.videoplayer.model.command

/**
 * 使用命令模式把复杂的Bean转换成适配页面的简单的Bean类
 */
interface Command<out T> {
    fun execute(): T
}