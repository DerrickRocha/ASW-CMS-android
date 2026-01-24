@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.aswcms.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.aswcms.R
import com.example.aswcms.ui.main.MainNavigationState.*
import com.example.aswcms.ui.overview.StoreOverviewScreen
import com.example.aswcms.ui.stores.LoadingSection
import com.example.aswcms.ui.stores.StoresScreen
import com.example.aswcms.ui.viewmodels.MainScreenIntent
import com.example.aswcms.ui.viewmodels.MainScreenState
import com.example.aswcms.ui.viewmodels.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainScreenViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    val key = when (state) {
        is MainScreenState.Overview -> StoreOverview((state as MainScreenState.Overview).storeId)
        MainScreenState.Stores -> Stores
        MainScreenState.Loading -> Loading
    }
    val navigationState = remember(key) { MainNavigationState(key) }

    Scaffold(
        Modifier
            .fillMaxSize(),
        topBar = {
            MainTopAppBar(navigationState.isTopLevel) { isTopLevel ->
                if (isTopLevel) {
                    // todo Open menu
                } else {
                    navigationState.navigateUp()
                }
            }
        },
        floatingActionButton = {},
    ) { innerPadding ->
        val onStoreSelected: (Int) -> Unit = { storeId ->
            viewModel.onIntent(MainScreenIntent.RequestStoreOverView(storeId))
        }
        MainNavigationDisplay(
            Modifier.padding(innerPadding),
            navigationState.backstack,
            onStoreSelected
        )
    }
}

@Composable
fun MainTopAppBar(isTopLevel: Boolean, onNavIconClicked: (Boolean) -> Unit) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = stringResource(R.string.welcome)) },
        navigationIcon = {
            IconButton(onClick = {
                onNavIconClicked(isTopLevel)
            }) {
                Icon(
                    imageVector = if (isTopLevel) Icons.Filled.Menu else Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

@Composable
fun MainNavigationDisplay(
    modifier: Modifier,
    backstack: SnapshotStateList<NavKey>,
    onStoreSelected: (Int) -> Unit
) {
    NavDisplay(
        modifier = modifier
            .fillMaxSize(),
        backStack = backstack,
        entryProvider = entryProvider {
            entry<Stores> {
                StoresScreen(onStoreClicked = onStoreSelected)
            }
            entry<StoreOverview> { key ->
                StoreOverviewScreen(key.storeId)
            }
            entry<Loading> {
                LoadingSection()
            }
        }
    )
}

@Preview("Home Screen", showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}