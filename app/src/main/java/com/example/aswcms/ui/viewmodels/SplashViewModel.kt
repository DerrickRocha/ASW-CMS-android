package com.example.aswcms.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SplashViewModel(): ViewModel() {

    private val _effects = MutableSharedFlow<SplashEffect>()
    val effects: SharedFlow<SplashEffect> = _effects

    init{
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            delay(3_000)
            _effects.emit(SplashEffect.ShowNextScreen)
        }
    }

}

sealed interface SplashEffect {
    data object ShowNextScreen : SplashEffect
}