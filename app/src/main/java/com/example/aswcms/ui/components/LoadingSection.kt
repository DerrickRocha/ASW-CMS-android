package com.example.aswcms.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun LoadingSection() {
    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            // Consume all pointer events
            awaitPointerEventScope {
                while (true) {
                    awaitPointerEvent()
                }
            }
        }, contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}