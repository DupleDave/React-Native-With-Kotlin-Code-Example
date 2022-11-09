package com.goingnative.samplenativemodule
import android.os.Handler
import android.os.Looper
import com.facebook.react.bridge.*

class BluetoothModule(context: ReactApplicationContext) : ReactContextBaseJavaModule()
{

    private val _mainHandler = Handler(Looper.getMainLooper())
    private val rContext: ReactApplicationContext = context


    var androidContext = rContext.applicationContext // This is where you get the context


//    @RequiresApi(Build.VERSION_CODES.O)
//    @ReactMethod
//    fun getCurrentTime(promise: Promise) {
//        val date = ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT )
//        promise.resolve(date)
//    }

    override fun getName(): String {
        return "Bluetooth"
    }
}