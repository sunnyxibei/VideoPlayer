package cn.com.timeriver.videoplayer.ui.fragment

import android.annotation.SuppressLint
import android.content.AsyncQueryHandler
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ListView
import android.widget.TextView
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseFragment
import cn.com.timeriver.videoplayer.model.bean.MusicBean
import cn.com.timeriver.videoplayer.model.command.MusicBeanCommand
import cn.com.timeriver.videoplayer.ui.activity.MusicPlayerActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity

class VBangFragment : BaseFragment() {

    private lateinit var listView: ListView
    private lateinit var vBangAdapter: VBangAdapter

    override fun getLayoutId() = R.layout.fragment_vbang


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = find(R.id.vbang_list)

        val resolver = context?.contentResolver
        val queryHandler = @SuppressLint("HandlerLeak")
        object : AsyncQueryHandler(resolver) {
            override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
                super.onQueryComplete(token, cookie, cursor)
                val vBangAdapter = cookie as VBangAdapter
                vBangAdapter.swapCursor(cursor)
            }
        }
        vBangAdapter = VBangAdapter(context, null)
        listView.adapter = vBangAdapter
        queryHandler.startQuery(0, vBangAdapter,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.SIZE),
                null, null, null)
        listView.setOnItemClickListener { parent, _, position, _ ->
            //获取数据集合和当前点击的position，发送到MusicPlayer界面
            val musicBeanList = arrayListOf<MusicBean>()
            val adapter = parent.adapter as CursorAdapter
            val cursor = adapter.cursor
            //重置cursor指针的位置
            cursor.moveToPosition(-1)
            while (cursor.moveToNext()) {
                musicBeanList.add(MusicBeanCommand(cursor).execute())
            }
            startActivity<MusicPlayerActivity>("list" to musicBeanList, "position" to position)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vBangAdapter.changeCursor(null)
    }

}

class VBangAdapter(context: Context?, cursor: Cursor?) : CursorAdapter(context, cursor, false) {
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context)
                .inflate(R.layout.item_vbang, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val titleTv = view?.find<TextView>(R.id.tv_title)
        val artistTv = view?.find<TextView>(R.id.tv_artist)
        val sizeTv = view?.find<TextView>(R.id.tv_size)
        cursor?.let {
            val musicBean = MusicBeanCommand(cursor).execute()
            titleTv?.text = musicBean.title
            artistTv?.text = musicBean.artist
            sizeTv?.text = Formatter.formatShortFileSize(context, musicBean.size)
        }
    }

}
