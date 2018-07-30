package cn.com.timeriver.videoplayer.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseFragment
import org.jetbrains.anko.support.v4.find

class MvPlayerFragment : BaseFragment() {

    companion object {
        fun newInstance(args: Bundle): MvPlayerFragment {
            val fragment = MvPlayerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mv_payer
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = find<TextView>(R.id.tv_pager)
        arguments?.let {
            textView.text = "我是第${arguments?.getInt("position")}张"
        }
    }

}