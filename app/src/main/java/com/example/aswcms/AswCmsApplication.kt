package com.example.aswcms

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AswCmsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CMSDependencies.init(this)
    }

}