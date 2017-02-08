package com.example.androidthings.rainbowhat_demo

import com.google.android.things.contrib.driver.ht16k33.Ht16k33
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import java.io.Closeable

/**
 * Created by Remco Veldkamp on 07/02/2017.
 */

class Kitt(val displayText : String, val color : Int) : Behavior(), Closeable {
    override val delay: Long
        get() = 100

    val ledStrip = RainbowHatLedStrip()
    val state = IntArray(ledStrip.length())
    var leader = 0
    var direction = +1


    init {
        init()
    }

    override fun init() {
        ledStrip.brightness(1)
        for (index in state.indices) {
            state[index] = 0
        }

        update()

        charDisplay(displayText)
    }

    /**
     Commit the current state to the hardware
     */

    // for red, shift left 16 bits
    override fun update() {

        // https://developer.android.com/reference/android/graphics/Color.html
        ledStrip.write(state.map { it shl (color*8) }.toIntArray())
    }


    /**
     Advance the current state
     */
    override fun step() {
        // fade everything out a bit
        for (index in state.indices) {
            state[index] = Math.round(state[index] * 0.25f)
        }

        // advance the leading position and set it to max brightness
        leader += direction
        state[leader] = 255


        // reverse direction at the beginning and the end
        if (leader == ledStrip.length()-1 || leader == 0)
            direction *= -1
    }

    override fun close() {
        super.close()
        ledStrip.close()
    }

    fun charDisplay(str : String) {
        val segment = RainbowHat.openDisplay()
        segment.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX)
        segment.display(str)
        segment.setEnabled(true)
        segment.close()
    }
}
