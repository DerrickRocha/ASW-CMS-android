package com.example.aswcms.ui.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.aswcms.ui.Home
import com.example.aswcms.ui.store.StoresScreen
import com.example.aswcms.ui.viewmodels.HomeScreenState
import com.example.aswcms.ui.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = viewModel()) {
    Scaffold(
        Modifier
            .fillMaxSize()
            .consumeWindowInsets(WindowInsets.safeDrawing),
        topBar = {},
        floatingActionButton = {},
    ) {
        val state by viewModel.state.collectAsState()
        HomeScreenContent(state)
    }
}

@Composable
fun HomeScreenContent(state: HomeScreenState) {
    when(state) {
        is HomeScreenState.Overview -> {
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
        HomeScreenState.Stores -> {
            StoresScreen()
        }
    }
}

@Preview("Home Screen")
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}