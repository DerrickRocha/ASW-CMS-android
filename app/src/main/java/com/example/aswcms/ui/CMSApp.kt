package com.example.aswcms.ui

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.aswcms.ui.home.HomeScreen
import com.example.aswcms.ui.login.LoginScreen
import com.example.aswcms.ui.splash.SplashScreen
import com.example.aswcms.ui.theme.ASWCMSTheme
import kotlinx.serialization.Serializable

@Serializable
data object Login : NavKey

@Serializable
data object Home : NavKey

@Composable
fun CMSApp() {

    val onShowNextScreen = {

    }
    ASWCMSTheme {
        SplashScreen(onShowNextScreen = onShowNextScreen)


        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            val backstack = rememberNavBackStack (Login)

            NavDisplay(
                modifier = Modifier
                    .consumeWindowInsets(innerPadding)
                    .padding(innerPadding),
                backStack = backstack,
                onBack = { backstack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<Login> {
                        LoginScreen(
                            onLoginComplete = {
                                backstack.removeLastOrNull()
                                backstack.add(Home)
                            })
                    }
                    entry<Home> { HomeScreen() }
                },
            )
        }
    }
}