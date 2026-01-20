package com.example.aswcms

import android.app.Application

class AswCmsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CMSDependencies.init(this)
    }

}