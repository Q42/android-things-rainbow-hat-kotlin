package com.example.androidthings.rainbowhat_demo

import android.os.Handler
import android.support.annotation.CallSuper
import com.google.android.things.contrib.driver.ht16k33.Ht16k33
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat

/**
 * Created by Remco Veldkamp on 07/02/2017.
 */
abstract class Behavior {
    var runnable : Runnable = Runnable {
        start()
    }

    var handler: Handler = android.os.Handler()



    abstract val delay : Long

    abstract fun init()

    abstract fun update()

    abstract fun step()

    fun start() {
        step()
        update()
        handler.postDelayed(runnable, delay)
    }

    @CallSuper
    open fun close() {
        handler.removeCallbacks(runnable)
    }
}
