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
import com.example.aswcms.ui.overview.StoreOverviewScreen
import com.example.aswcms.ui.stores.StoresScreen
import com.example.aswcms.ui.viewmodels.MainScreenIntent
import com.example.aswcms.ui.viewmodels.MainScreenState
import com.example.aswcms.ui.viewmodels.MainScreenViewModel

@Composable
fun MainScreen(viewModel: MainScreenViewModel = viewModel()) {
    Scaffold(
        Modifier
            .fillMaxSize()
            .consumeWindowInsets(WindowInsets.safeDrawing),
        topBar = {},
        floatingActionButton = {},
    ) {
        val onStoreSelected: (Int) -> Unit = { storeId ->
            viewModel.onIntent(MainScreenIntent.RequestStoreOverView(storeId))
        }
        val state by viewModel.state.collectAsState()

        MainScreenContent(state, onStoreSelected)
    }
}

@Composable
fun MainScreenContent(state: MainScreenState, onStoreSelected: (Int) -> Unit) {
    when(state) {
        MainScreenState.Stores -> {
            StoresScreen(onStoreClicked = onStoreSelected)
        }
        is MainScreenState.Overview -> {
            StoreOverviewScreen()
        }

    }
}

@Preview("Home Screen")
@Composable
fun MainScreenPreview() {
    MainScreen()
}