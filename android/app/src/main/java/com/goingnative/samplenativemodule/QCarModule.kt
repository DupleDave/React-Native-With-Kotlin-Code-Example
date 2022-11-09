package com.goingnative.samplenativemodule

import android.content.Context.BATTERY_SERVICE
import android.content.Context.WIFI_SERVICE
import android.content.Intent
import android.content.Intent.*
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import com.quectel.qcarapi.QCarVersion.qcarGetVersion

class QCarModule(context: ReactApplicationContext) : ReactContextBaseJavaModule()
{
    private val _mainHandler = Handler(Looper.getMainLooper())
    private val rContext: ReactApplicationContext = context

    @RequiresApi(Build.VERSION_CODES.O)
    @ReactMethod
    fun getSdkVersion(promise: Promise) {  promise.resolve(qcarGetVersion()) }

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
        return "QCar"
    }
}