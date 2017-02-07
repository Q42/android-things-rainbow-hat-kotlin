

package com.example.androidthings.rainbowhat_demo

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.ht16k33.Ht16k33
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService
import java.io.IOException


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        Log.d(TAG, "Hello!!! from Android Things in Kotlin!!!!")


        val manager = PeripheralManagerService()
        val portList = manager.getGpioList()
        if (portList.isEmpty()) {
            Log.i(TAG, "No GPIO port available on this device.");
        } else {
            Log.i(TAG, "List of available ports: " + portList);
        }

        // Light up the Red LED.
        var led: Gpio? = null
        try {
            led = RainbowHat.openLed(RainbowHat.LED_GREEN)
            led!!.value = false
            led.close()

            led = RainbowHat.openLed(RainbowHat.LED_BLUE)
            led!!.value = false
            led.close()


            // Play a note on the buzzer.
            val buzzer = RainbowHat.openPiezo()
            buzzer.play(880.0)

            Thread.sleep(500)

            // Stop the buzzer.
            buzzer.stop()
            // Close the device when done.
            buzzer.close()

            // Log the current temperature
            val sensor = RainbowHat.openSensor()
            sensor.setTemperatureOversampling(Bmx280.OVERSAMPLING_1X)
            Log.d(TAG, "temperature:" + sensor.readTemperature())
            // Close the device when done.

            // Display a string on the segment display.
            val segment = RainbowHat.openDisplay()
            segment.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX)
            segment.display(Math.round(sensor.readTemperature()))
            segment.setEnabled(true)
            // Close the device when done.
            segment.close()
            sensor.close()


            // Light up the rainbow


            val ledstrip = RainbowHat.openLedStrip()
            ledstrip.setBrightness(1)
            val rainbow = IntArray(RainbowHat.LEDSTRIP_LENGTH)
            for (i in rainbow.indices) {
                rainbow[i] = Color.HSVToColor(254, arrayOf(i * 360f / RainbowHat.LEDSTRIP_LENGTH , 1f, 1f ).toFloatArray() )
            }
            ledstrip.write(rainbow)
            // Close the device when done.
            ledstrip.close()

            // Detect button press.
            val button = RainbowHat.openButton(RainbowHat.BUTTON_A)
            button.setOnButtonEventListener { button, pressed -> Log.d(TAG, "button A pressed:" + pressed) }
            // Close the device when done.
            //
            //            // Detect button press.
            val buttonb = RainbowHat.openButton(RainbowHat.BUTTON_B)
            buttonb.setOnButtonEventListener { button, pressed -> Log.d(TAG, "button B pressed:" + pressed) }
            // Close the device when done.
            //
            //            // Detect button press.
            val buttonc = RainbowHat.openButton(RainbowHat.BUTTON_C)
            buttonc.setOnButtonEventListener { button, pressed -> Log.d(TAG, "button C pressed:" + pressed) }
            // Close the device when done.

            // Continously report temperature.
//            val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//
//            var cb = object: SensorManager.DynamicSensorCallback() {
//
//                override fun onDynamicSensorConnected(sensor: Sensor) {
//                    if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
//                        sensorManager.registerListener(
//                                object: SensorEventListener {
//                                    override fun onSensorChanged(event: SensorEvent) {
//                                        Log.i(TAG, "sensor changed: " + event.values[0])
//                                    }
//                                    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
//                                        Log.i(TAG, "accuracy changed: " + accuracy)
//                                    }
//                                },
//                                sensor, SensorManager.SENSOR_DELAY_NORMAL);
//                    }
//                }
//            }
//
//            sensorManager.registerDynamicSensorCallback( cb)

        } catch (ex: RuntimeException) {

        } catch (e: IOException) {
            Log.e(TAG, e.message)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    companion object {
        private val TAG = MainActivity::class.java!!.getSimpleName()
    }
}
