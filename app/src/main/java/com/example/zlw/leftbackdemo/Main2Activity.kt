package com.example.zlw.leftbackdemo

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

class Main2Activity : AppCompatActivity() {

    private lateinit var mRootView:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        mRootView = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        Log.e(TAG, "rootview = $mRootView")

    }

    companion object {
        val TAG = Main2Activity::class.java.simpleName
    }

    private var mDownX:Int = 0

    private val mScreenX:Int by lazy {
        getScreenSize(this).widthPixels
    }

    fun getScreenSize(activity: Activity): DisplayMetrics {
        val outMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {
                mDownX = event.rawX.toInt()

            }

            MotionEvent.ACTION_MOVE -> {
                if (mDownX <= 20) {
                    val curX = event.rawX.toInt()
                    if (curX.compareTo(mDownX) > 0) {
                        mRootView.translationX = (curX - mDownX).toFloat()
                    }

                }
            }

            MotionEvent.ACTION_UP -> {
                val mRootViewTransX = mRootView.translationX
                if (mRootViewTransX >= mScreenX / 2) {
                    finishActivityInAnimation()
                }
            }

            else -> {}
        }
        return true

    }

    private fun finishActivityInAnimation() {
        ObjectAnimator.ofFloat(mRootView,"translationX",mRootView.translationX,mScreenX.toFloat()).apply {
            duration = 500
            addUpdateListener {
                val value = Math.round(it.animatedValue.toString().toFloat())
                Log.e(TAG,"value = $value screenX = $mScreenX ${value == mScreenX}")
                if (value == mScreenX) {
                    finish()
                }
            }
        }.start()
    }
}
