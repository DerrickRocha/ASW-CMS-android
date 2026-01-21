package com.example.aswcms.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.aswcms.ui.home.HomeScreen
import com.example.aswcms.ui.login.LoginScreen
import com.example.aswcms.ui.splash.SplashScreen
import com.example.aswcms.ui.theme.ASWCMSTheme
import com.example.aswcms.ui.viewmodels.CMSAppIntent
import com.example.aswcms.ui.viewmodels.CMSAppState
import com.example.aswcms.ui.viewmodels.CMSAppViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Composable
fun CMSApp(viewModel: CMSAppViewModel = viewModel()) {

    val state by viewModel.cmsAppState.collectAsState()

    ASWCMSTheme {
        when(state) {
            CMSAppState.Splash -> {
                SplashScreen {
                    viewModel.onIntent(CMSAppIntent.IsLoggedInRequested)
                }
            }
            CMSAppState.Login -> LoginScreen {
                viewModel.onIntent(CMSAppIntent.HomeScreenRequested)
            }
            CMSAppState.Home -> {
                val backstack = rememberNavBackStack (Home)

                NavDisplay(
                    modifier = Modifier
                        .fillMaxSize()
                        .consumeWindowInsets(WindowInsets.safeDrawing),
                    backStack = backstack,
                    onBack = { backstack.removeLastOrNull() },
                    entryProvider = entryProvider {
                        entry<Home> { HomeScreen() }
                    },
                )
            }
        }
    }
}