package com.example.aswcms.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aswcms.ui.overview.StoreOverviewScreen
import com.example.aswcms.ui.stores.StoresScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.STORES,
        modifier = modifier.fillMaxSize()
    ) {

        composable(route = Routes.STORES) {
            StoresScreen { storeId ->
                navController.navigate(Routes.storeOverview(storeId))
            }
        }
        composable(
            route = Routes.STORE_OVERVIEW,
            arguments = listOf(navArgument("storeId") { type = NavType.IntType })
        ) {
            StoreOverviewScreen(storeId = -1) { }
        }
        composable(route = Routes.PRODUCTS) {

        }
        composable(route = Routes.PRODUCT) {

        }

    }
}