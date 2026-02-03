@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.aswcms.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aswcms.R
import com.example.aswcms.extensions.resolveMainMenuItemString
import com.example.aswcms.ui.components.CMSSimpleDialog
import com.example.aswcms.ui.navigation.MainNavHost
import com.example.aswcms.ui.navigation.Routes
import com.example.aswcms.ui.viewmodels.MainMenuItem
import com.example.aswcms.ui.viewmodels.MainScreenIntent
import com.example.aswcms.ui.viewmodels.MainScreenState
import com.example.aswcms.ui.viewmodels.MainScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val confirmLogoutState = rememberSaveable { mutableStateOf(false) }
    val navController = rememberNavController()

    val onNavIconClicked: () -> Unit = {
        if (navController.previousBackStackEntry == null) {
            scope.launch {
                if (drawerState.isOpen) drawerState.close() else drawerState.open()
            }
        } else {
            navController.navigateUp()
        }
    }

    val onNavigationDrawerItemClicked: (MainMenuItem) -> Unit = { item ->
        when (item) {
            MainMenuItem.Account -> {}
            MainMenuItem.Logout -> confirmLogoutState.value = true
            MainMenuItem.Stores -> {
                viewModel.onIntent(MainScreenIntent.RequestStores)
                scope.launch {
                    drawerState.close()
                }
            }
        }
    }

    val onStoreClicked: (Int) -> Unit = { storeId ->
        viewModel.onIntent(MainScreenIntent.RequestStoreOverView(storeId))
    }
    val navString = when (state) {
        MainScreenState.Loading -> Routes.LOADING
        is MainScreenState.Overview -> Routes.storeOverview((state as MainScreenState.Overview).storeId)
        MainScreenState.Stores -> Routes.STORES
    }
    val onConfirmLogout = {
        viewModel.onIntent(MainScreenIntent.RequestLogout)
    }
    val onDismissLogout = {
        confirmLogoutState.value = false
    }
    MainScreenContent(
        navController,
        drawerState,
        navString,
        viewModel.menuItems,
        onNavIconClicked,
        onNavigationDrawerItemClicked,
        onStoreClicked,
        onConfirmLogout,
        onDismissLogout,
        confirmLogoutState.value
    )
}

@Composable
fun MainScreenContent(
    navController: NavHostController,
    drawerState: DrawerState,
    navString: String,
    menuItems: List<MainMenuItem>,
    onNavIconClicked: () -> Unit,
    onNavigationDrawerItemClicked: (MainMenuItem) -> Unit,
    onStoreClicked: (Int) -> Unit,
    onConfirmLogout: () -> Unit,
    onDismissLogout: () -> Unit,
    showLogout: Boolean
) {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(drawerState) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    for (item in menuItems) {
                        NavigationDrawerItem(
                            selected = false,
                            label = { Text(stringResource(item.resolveMainMenuItemString())) },
                            onClick = { onNavigationDrawerItemClicked(item) }
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
                val title =
                    when (navController.currentBackStackEntryAsState().value?.destination?.route) {
                        Routes.STORES -> "Stores"
                        Routes.ORDERS -> "Orders"
                        Routes.CUSTOMERS -> "Customers"
                        Routes.INVENTORY -> "Inventory"
                        Routes.STORE_OVERVIEW -> "Welcome"
                        Routes.PRODUCTS -> "Products"
                        else -> ""
                    }
                MainTopAppBar(
                    navController.previousBackStackEntry == null,
                    onNavIconClicked,
                    title = title
                )
            },
            floatingActionButton = {},
        ) { innerPadding ->
            MainNavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startRoute = navString,
                onStoreClicked
            )
        }
    }
    CMSSimpleDialog(
        message = stringResource(R.string.would_you_like_to_logout),
        show = showLogout,
        onConfirmSelected = onConfirmLogout,
        onDismissRequest = onDismissLogout)
}

@Composable
fun MainTopAppBar(isTopLevel: Boolean, onNavIconClicked: () -> Unit, title: String?) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = title ?: "") },
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavIconClicked()
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

@Preview("Home Screen", showSystemUi = true)
@Composable
fun MainScreenPreview() {
    CMSSimpleDialog("", true, {}) {}
}