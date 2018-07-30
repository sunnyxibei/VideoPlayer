package cn.com.timeriver.videoplayer.model.command

import android.database.Cursor
import android.provider.MediaStore
import cn.com.timeriver.videoplayer.model.bean.MusicBean

class MusicBeanCommand(var cursor: Cursor) : Command<MusicBean> {

    override fun execute(): MusicBean {
        val data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
        val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
        val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
        val size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
        return MusicBean(data, title, artist, size)
    }

}