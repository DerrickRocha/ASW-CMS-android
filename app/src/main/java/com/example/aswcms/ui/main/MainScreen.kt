package com.example.aswcms.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aswcms.R
import com.example.aswcms.ui.overview.StoreOverviewScreen
import com.example.aswcms.ui.stores.StoresScreen
import com.example.aswcms.ui.viewmodels.MainScreenIntent
import com.example.aswcms.ui.viewmodels.MainScreenState
import com.example.aswcms.ui.viewmodels.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainScreenViewModel = viewModel()) {
    Scaffold(
        Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(text = stringResource(R.string.welcome)) },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
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
    when (state) {
        MainScreenState.Stores -> {
            StoresScreen(onStoreClicked = onStoreSelected)
        }

        is MainScreenState.Overview -> {
            StoreOverviewScreen()
        }

    }
}

@Preview("Home Screen", showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}