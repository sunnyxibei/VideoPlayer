package cn.com.timeriver.videoplayer.ui.activity

import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.view.Surface
import android.view.TextureView
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseActivity
import cn.com.timeriver.videoplayer.model.bean.PlayerBean
import org.jetbrains.anko.find

/**
 * TextureView可以认为是SurfaceView的改进版，可以放在列表中，可以实现动效
 *
 * alt+F7快捷键 Find Usages
 */
class TextureVideoPlayerActivity : BaseActivity(), TextureView.SurfaceTextureListener {

    private lateinit var videoView: TextureView
    private lateinit var playerBean: PlayerBean
    private val mediaPlayer by lazy { MediaPlayer() }

    override fun getLayoutId() = R.layout.activity_video_player

    override fun initView() {
        videoView = find(R.id.video_view)
    }

    override fun initData() {
        playerBean = intent.getParcelableExtra("item")
        videoView.surfaceTextureListener = this
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        //大小变化
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
        //视图更新
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        mediaPlayer.let {
            it.stop()
            it.release()
        }
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        playerBean.let {
            mediaPlayer.setDataSource(it.url)
            mediaPlayer.setSurface(Surface(surface))
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
            }
        }
    }

}