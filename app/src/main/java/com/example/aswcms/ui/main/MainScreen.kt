@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.aswcms.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.aswcms.R
import com.example.aswcms.ui.components.LoadingSection
import com.example.aswcms.ui.main.MainNavigationState.*
import com.example.aswcms.ui.overview.OverviewItemId
import com.example.aswcms.ui.overview.StoreOverviewScreen
import com.example.aswcms.ui.stores.StoresScreen
import com.example.aswcms.ui.viewmodels.MainScreenIntent
import com.example.aswcms.ui.viewmodels.MainScreenState
import com.example.aswcms.ui.viewmodels.MainScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun MainMenuItem.resolveMainMenuItemString(): String {
    return when (this) {
        MainMenuItem.Account -> stringResource(R.string.account)
        MainMenuItem.Logout -> stringResource(R.string.logout)
        MainMenuItem.Stores -> stringResource(R.string.stores)
    }
}

val menuItems = listOf(MainMenuItem.Account, MainMenuItem.Stores, MainMenuItem.Logout)

sealed interface MainMenuItem {
    data object Account : MainMenuItem
    data object Stores : MainMenuItem
    data object Logout : MainMenuItem
}

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
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val confirmLogoutState = remember { mutableStateOf(false) }
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(drawerState) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    for (item in menuItems) {
                        NavigationDrawerItem(
                            selected = false,
                            label = { Text(item.resolveMainMenuItemString()) },
                            onClick = {
                                when (item) {
                                    MainMenuItem.Account -> {

                                    }

                                    MainMenuItem.Logout -> {
                                        confirmLogoutState.value = true
                                    }

                                    MainMenuItem.Stores -> {
                                        viewModel.onIntent(MainScreenIntent.RequestStores)
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            Modifier
                .fillMaxSize(),
            topBar = {
                MainTopAppBar(navigationState.isTopLevel) { isTopLevel ->
                    if (isTopLevel) {
                        scope.launch {
                            if (drawerState.isOpen) {
                                drawerState.close()
                            } else {
                                drawerState.open()
                            }
                        }
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
            val onOverviewItemSelected: (OverviewItemId) -> Unit = { id ->
                when (id) {
                    OverviewItemId.ORDERS -> navigationState.navigate(Orders)
                    OverviewItemId.PRODUCTS -> navigationState.navigate(Products)
                    OverviewItemId.CUSTOMERS -> navigationState.navigate(Customers)
                    OverviewItemId.INVENTORY -> navigationState.navigate(Inventory)
                }
            }
            MainNavigationDisplay(
                Modifier.padding(innerPadding),
                navigationState.backstack,
                onStoreSelected,
                onOverviewItemSelected
            )
        }
    }
    CMSSimpleDialog(
        show = confirmLogoutState.value,
        onConfirmSelected = {
            viewModel.onIntent(MainScreenIntent.RequestLogout)
        },
        onDismissRequest = {
            confirmLogoutState.value = false
        })
}

@Composable
fun CMSSimpleDialog(show: Boolean, onConfirmSelected: () -> Unit, onDismissRequest: () -> Unit) {
    if (show) {
        BasicAlertDialog(onDismissRequest = onDismissRequest) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("Would you like to logout?")
                    Spacer(Modifier.height(24.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        TextButton(
                            onClick = onDismissRequest,
                            modifier = Modifier
                        ) {
                            Text("cancel")
                        }
                        TextButton(
                            onClick = onConfirmSelected,
                            modifier = Modifier
                        ) {
                            Text("confirm")
                        }
                    }

                }

            }
        }
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
                    contentDescription = if (isTopLevel) stringResource(R.string.menu) else stringResource(
                        R.string.navigate_up
                    )
                )
            }
        }
    )
}

@Composable
fun MainNavigationDisplay(
    modifier: Modifier,
    backstack: SnapshotStateList<NavKey>,
    onStoreSelected: (Int) -> Unit,
    onOverviewItemSelected: (OverviewItemId) -> Unit
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
                StoreOverviewScreen(key.storeId, onOverviewItemSelected)
            }
            entry<Loading> {
                LoadingSection()
            }
            entry<Orders> {
                Text(text = "fe")
            }
            entry<Products> {
                Text(text = "fi")
            }
            entry<Customers> {
                Text(text = "fo")
            }
            entry<Inventory> {
                Text("fum")
            }
        }
    )
}

@Preview("Home Screen", showSystemUi = true)
@Composable
fun MainScreenPreview() {
    CMSSimpleDialog(true, {}) {}
}