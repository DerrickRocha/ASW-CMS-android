package com.example.aswcms.ui.main

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
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.aswcms.ui.store.StoresScreen
import com.example.aswcms.ui.viewmodels.MainScreenState
import com.example.aswcms.ui.viewmodels.MainScreenViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Overview : NavKey
@Composable
fun MainScreen(viewModel: MainScreenViewModel = viewModel()) {
    Scaffold(
        Modifier
            .fillMaxSize()
            .consumeWindowInsets(WindowInsets.safeDrawing),
        topBar = {},
        floatingActionButton = {},
    ) {
        val state by viewModel.state.collectAsState()
        MainScreenContent(state)
    }
}

@Composable
fun MainScreenContent(state: MainScreenState) {
    when(state) {
        MainScreenState.Stores -> {
            StoresScreen()
        }
        is MainScreenState.Overview -> {

        }

    }
}

@Preview("Home Screen")
@Composable
fun MainScreenPreview() {
    MainScreen()
}