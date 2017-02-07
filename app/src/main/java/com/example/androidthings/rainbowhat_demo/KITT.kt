package com.example.androidthings.rainbowhat_demo

import java.io.Closeable

/**
 * Created by Remco Veldkamp on 07/02/2017.
 */

class Kitt(val ledStrip : ILedStrip) : Behavior(), Closeable {
    override val delay: Long
        get() = 100

    val state = IntArray(ledStrip.length())
    var leader = 0
    var direction = +1

    init {
        init()
    }

    override fun init() {
        for (index in state.indices) {
            state[index] = 0
        }

        update()

        charDisplay("KITT")
    }

    /**
     Commit the current state to the hardware
     */
    override fun update() {
        // for red, shift left 16 bits
        // https://developer.android.com/reference/android/graphics/Color.html
        ledStrip.write(state.map { it shl 16 }.toIntArray())
    }

    /**
     Advance the current state
     */
    override fun step() {
        for (index in state.indices) {
            state[index] = Math.round(state[index] * 0.25f)
        }

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
}
