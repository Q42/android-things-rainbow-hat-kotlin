package com.example.androidthings.rainbowhat_demo

import java.io.Closeable

/**
 * Created by Remco Veldkamp on 07/02/2017.
 */
interface ILedStrip : Closeable {
    fun write(values: IntArray)
    fun length() : Int
    fun brightness(value: Int)
}
