package com.bestcoders.zaheed

import android.app.Application
import com.google.android.gms.maps.MapsInitializer
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class MainApp : Application(){
    override fun onCreate() {
        super.onCreate()
        MapsInitializer.initialize(applicationContext, MapsInitializer.Renderer.LATEST){

        }
    }
}