package cn.com.timeriver.videoplayer.ui.activity

import android.Manifest
import android.os.Build
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseActivity
import cn.com.timeriver.videoplayer.util.FragmentFactory
import cn.com.timeriver.videoplayer.util.ToolbarUtil
import com.roughike.bottombar.BottomBar
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * 此处使用kotlin提供的委托lazy实现懒加载，且lazy是线程安全的
 */
class MainActivity : BaseActivity(), ToolbarUtil {

    companion object {
        private val REQUEST_MUST_PERMISSION = 1
        private val MUST_PERMISSION = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        requestMustPermission()
        initMainToolbar()
        setSupportActionBar(toolbar)
    }

    private fun requestMustPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(MUST_PERMISSION, REQUEST_MUST_PERMISSION)
        }
    }

    override fun initListener() {
        //初始化BottomBar的监听
        val bottomBar = find<BottomBar>(R.id.bottomBar)
        bottomBar.setOnTabSelectListener {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, FragmentFactory.instance.getFragment(it), it.toString())
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.settings) {
            startActivity<SettingsActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

}
