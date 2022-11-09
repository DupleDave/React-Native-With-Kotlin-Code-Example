package com.goingnative.samplenativemodule
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.facebook.react.bridge.*
import com.quectel.qcarapi.QCarVersion.qcarGetVersion

class QCarModule(context: ReactApplicationContext) : ReactContextBaseJavaModule()
{
    private val _mainHandler = Handler(Looper.getMainLooper())
    private val rContext: ReactApplicationContext = context

    @RequiresApi(Build.VERSION_CODES.O)
    @ReactMethod
    fun getSdkVersion(promise: Promise) {  promise.resolve(qcarGetVersion()) }

    override fun getName(): String {
        return "QCar"
    }
}