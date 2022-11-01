package com.goingnative.samplecomponent

import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.BatteryManager
import android.os.Build
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp



class SampleNativeComponentViewManager : SimpleViewManager<SampleNativeComponent>() {
    override fun getName(): String {
        return "SampleComponent"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): SampleNativeComponent {
        return SampleNativeComponent(reactContext)
    }

    override fun getExportedCustomDirectEventTypeConstants(): MutableMap<String, Any> {
        return MapBuilder.of(
            "onUpdate", MapBuilder.of("registrationName","onUpdate")
        )
    }

    @ReactProp(name = "myColor")
    fun setMyColor(view: SampleNativeComponent, myColor: String) {
        view.setBackgroundColor(Color.parseColor(myColor))
    }


}