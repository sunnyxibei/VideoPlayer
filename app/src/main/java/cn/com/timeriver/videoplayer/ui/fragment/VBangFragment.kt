package cn.com.timeriver.videoplayer.ui.fragment

import android.annotation.SuppressLint
import android.content.AsyncQueryHandler
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ListView
import android.widget.TextView
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find

class VBangFragment : BaseFragment() {

    private lateinit var listView: ListView
    private lateinit var vBangAdapter: VBangAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_vbang
    }

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
            titleTv?.text = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            artistTv?.text = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            sizeTv?.text = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)).toString()
        }
    }

}
