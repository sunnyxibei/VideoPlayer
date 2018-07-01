package cn.com.timeriver.videoplayer.presenter.ui.activity

import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
