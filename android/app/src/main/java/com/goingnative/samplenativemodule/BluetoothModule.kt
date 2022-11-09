package com.goingnative.samplenativemodule
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class BluetoothModule(context: ReactApplicationContext) : ReactContextBaseJavaModule()
{

    private val _mainHandler = Handler(Looper.getMainLooper())
    private val rContext: ReactApplicationContext = context

    var secondsCount = 0

    var androidContext = rContext.applicationContext // This is where you get the context


    @RequiresApi(Build.VERSION_CODES.O)
    @ReactMethod
    fun getCurrentTime(promise: Promise) {
        val date = ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT )
        promise.resolve(date)
    }

    private fun sendEvent(
        reactContext: ReactContext,
        eventName: String,
        params: WritableMap
    ) {
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }

    override fun getName(): String {
        return "Bluetooth"
    }
}