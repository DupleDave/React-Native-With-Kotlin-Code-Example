package com.goingnative.samplecomponent

import android.graphics.Color
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