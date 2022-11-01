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


class SampleModule(context: ReactApplicationContext) : ReactContextBaseJavaModule()
{

    private val _mainHandler = Handler(Looper.getMainLooper())
    private val rContext: ReactApplicationContext = context
    var wifiManager: WifiManager = rContext.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager

    var secondsCount = 0

    var androidContext = rContext.applicationContext // This is where you get the context

    @ReactMethod
    fun enableWifi() {
        println("Enabling Wifi")
        wifiManager.isWifiEnabled = true
    }
    @ReactMethod
    fun disableWifi() {
        println("Disabling Wifi")
        wifiManager.isWifiEnabled = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @ReactMethod
    fun getCurrentTime(promise: Promise) {
        val date = ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT )
        promise.resolve(date)
    }


    @ReactMethod
    fun addListener(eventName: String?) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

    @ReactMethod
    fun removeListeners(count: Int?) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

    @ReactMethod
    open fun getBatteryLevel(promise: Promise) {
        println("start get battery")
        if (Build.VERSION.SDK_INT >= 21) {
            val bm = androidContext.getSystemService(BATTERY_SERVICE) as BatteryManager
            println("sdk>21 result ${bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)}")
            return promise.resolve(bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY))
        } else {
            val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus: Intent? = androidContext.registerReceiver(null, iFilter)
            val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
            val batteryPct = level / scale.toDouble()
            println("else result ${(batteryPct * 100).toInt()}")
            return promise.resolve((batteryPct * 100).toInt())
        }
    }


    @ReactMethod
    open fun getBatteryStatus(promise: Promise) {
        println("start get battery")

            val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus: Intent? = androidContext.registerReceiver(null, iFilter)
            val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL

            // How are we charging?
            val chargePlug: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1
            val usbCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
            val acCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_AC

            val resultData: WritableMap = WritableNativeMap()
            resultData.putBoolean("isCharging", isCharging)
            resultData.putInt("chargePlug", chargePlug)
            resultData.putBoolean("usbCharge", usbCharge)
            resultData.putBoolean("acCharge", acCharge)

            return promise.resolve(resultData)

    }

    @ReactMethod
    open fun getDockedStatus(promise: Promise) {
        println("start get Dock Status")

        val dockStatus: Intent? = IntentFilter(Intent.ACTION_DOCK_EVENT).let { ifilter ->
            androidContext.registerReceiver(null, ifilter)
        }
        val dockState: Int = dockStatus?.getIntExtra(EXTRA_DOCK_STATE, -1) ?: -1
        val isDocked: Boolean = dockState != Intent.EXTRA_DOCK_STATE_UNDOCKED

        val isCar: Boolean = dockState == EXTRA_DOCK_STATE_CAR
        val isDesk: Boolean = dockState == EXTRA_DOCK_STATE_DESK
                || dockState == EXTRA_DOCK_STATE_LE_DESK
                || dockState == EXTRA_DOCK_STATE_HE_DESK

        val resultData: WritableMap = WritableNativeMap()
        resultData.putBoolean("isDocked", isDocked)
        resultData.putBoolean("isCar", isCar)
        resultData.putBoolean("isDesk", isDesk)

        return promise.resolve(resultData)

    }

    @ReactMethod
    fun dispatchEventEverySecond() {
        _mainHandler.post(object : Runnable {
            override fun run() {
                secondsCount += 1
                val event = Arguments.createMap()
                event.putInt("count", secondsCount)
                sendEvent(
                    rContext,
                    "onTimeUpdated",
                    event
                )
                _mainHandler.postDelayed(this, 1000)
            }
        })
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
        return "Clock"
    }
}