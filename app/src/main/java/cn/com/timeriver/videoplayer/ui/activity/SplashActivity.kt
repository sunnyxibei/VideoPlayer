package cn.com.timeriver.videoplayer.ui.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.widget.ImageView
import cn.com.timeriver.videoplayer.R
import cn.com.timeriver.videoplayer.base.BaseActivity
import org.jetbrains.anko.find

class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        val imageView = find<ImageView>(R.id.iv_splash_bg)

        val scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 1.5f, 1.0f)
        val scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 1.5f, 1.0f)

        val animator = ObjectAnimator.ofPropertyValuesHolder(imageView, scaleXHolder, scaleYHolder)
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                startMainActivity()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animator.setDuration(3000)
                .start()
    }

    private fun startMainActivity() {
        startActivityAndFinish<MainActivity>()
    }

}