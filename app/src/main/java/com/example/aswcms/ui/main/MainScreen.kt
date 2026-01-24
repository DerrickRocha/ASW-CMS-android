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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.aswcms.R
import com.example.aswcms.ui.overview.StoreOverviewScreen
import com.example.aswcms.ui.stores.StoresScreen
import com.example.aswcms.ui.viewmodels.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainScreenViewModel = viewModel()) {
    val navigationState = remember { MainNavigationState(MainNavigationState.Stores) }
    Scaffold(
        Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(text = stringResource(R.string.welcome)) },
                navigationIcon = {
                    IconButton(onClick = {
                        if(navigationState.isTopLevel) {
                            // todo Open menu
                        }
                        else {
                            navigationState.navigateUp()
                        }
                    }) {
                        Icon(
                            imageVector = if (navigationState.isTopLevel) Icons.Filled.Menu else Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        floatingActionButton = {},
    ) { innerPadding ->
        val onStoreSelected: (Int) -> Unit = { storeId ->
            navigationState.navigateUp()
            navigationState.navigate(MainNavigationState.StoreOverview(storeId))
        }

        NavDisplay(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            backStack = navigationState.backstack,
            entryProvider = entryProvider {
                entry<MainNavigationState.Stores> {
                    StoresScreen(onStoreClicked = onStoreSelected)
                }
                entry<MainNavigationState.StoreOverview> { key ->
                    StoreOverviewScreen()
                }
            }
        )
    }
}

@Preview("Home Screen", showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}