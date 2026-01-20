package com.example.aswcms.ui.viewmodels

import androidx.lifecycle.ViewModel

class CMSAppViewModel: ViewModel() {

    fun onIntent(intent: CMSAppIntent) {
        when(intent){
            CMSAppIntent.IsLoggedInRequested -> {

            }
        }
    }
}

sealed interface CMSAppIntent {
    object IsLoggedInRequested: CMSAppIntent
}