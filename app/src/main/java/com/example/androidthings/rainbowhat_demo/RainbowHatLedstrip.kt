package com.example.androidthings.rainbowhat_demo

import com.google.android.things.contrib.driver.apa102.Apa102
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat

/**
 * Created by Remco Veldkamp on 07/02/2017.
 */

// TODO: should be singleton as it wraps a static service
class RainbowHatLedStrip : ILedStrip {
    var ledStrip : Apa102 = RainbowHat.openLedStrip()
    var closed : Boolean = false


    override fun brightness(value: Int) {
        ledStrip.setBrightness(value)
    }

    override fun close() {
        if (closed) throw Exception("Cannot close an already closed resource")

        ledStrip.close()
        closed = true
    }

    override fun write(values: IntArray) {
        ledStrip.write(values)
    }

    override fun length() : Int = RainbowHat.LEDSTRIP_LENGTH
}
