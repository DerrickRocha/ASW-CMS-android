package com.example.aswcms.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import com.example.aswcms.ui.main.MainScreen
import com.example.aswcms.ui.login.LoginScreen
import com.example.aswcms.ui.splash.SplashScreen
import com.example.aswcms.ui.theme.ASWCMSTheme
import com.example.aswcms.ui.viewmodels.CMSAppIntent
import com.example.aswcms.ui.viewmodels.CMSAppState
import com.example.aswcms.ui.viewmodels.CMSAppViewModel
import kotlinx.serialization.Serializable

@Composable
fun CMSApp(viewModel: CMSAppViewModel = viewModel()) {

    val state by viewModel.cmsAppState.collectAsState()

    ASWCMSTheme {
        Surface(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            when(state) {
                CMSAppState.Splash -> {
                    SplashScreen {
                        viewModel.onIntent(CMSAppIntent.IsLoggedInRequested)
                    }
                }
                CMSAppState.Login -> LoginScreen {
                    viewModel.onIntent(CMSAppIntent.HomeScreenRequested)
                }
                CMSAppState.Main -> {
                    MainScreen()
                }
            }
        }

    }
}