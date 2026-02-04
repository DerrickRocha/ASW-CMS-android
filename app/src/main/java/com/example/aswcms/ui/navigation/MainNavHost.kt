package com.example.aswcms.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aswcms.ui.components.LoadingSection
import com.example.aswcms.ui.overview.StoreOverviewScreen
import com.example.aswcms.ui.products.ProductDetailsScreen
import com.example.aswcms.ui.products.ProductImagesScreen
import com.example.aswcms.ui.products.ProductsScreen
import com.example.aswcms.ui.stores.StoresScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onStoreSelected: (storeId: Int) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOADING,
        modifier = modifier.fillMaxSize()
    ) {

        composable(route = Routes.STORES) {
            StoresScreen(onStoreClicked = onStoreSelected)
        }
        composable(
            route = Routes.STORE_OVERVIEW,
            arguments = listOf(navArgument("storeId") { type = NavType.IntType })
        ) { backstackEntry ->
            val storeId = backstackEntry.arguments?.getInt("storeId")?: return@composable
            StoreOverviewScreen(
                onOrdersClicked = { navController.navigate(Routes.ORDERS) },
                onProductsClicked = {
                    navController.navigate(Routes.products(storeId))
                },
                onCustomersClicked = {},
                onInventoryClicked = {}
            )
        }
        composable(
            route = Routes.PRODUCTS,
            arguments = listOf(navArgument("storeId") { type = NavType.IntType })
        ) {
            ProductsScreen(onProductSelected = { productId ->
                navController.navigate(
                    Routes.product(
                        productId = productId
                    )
                )
            })
        }
        composable(
            route = Routes.PRODUCT,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backstackEntry ->
            val storeId = backstackEntry.arguments?.getInt("productId")?: return@composable
            ProductDetailsScreen {
                navController.navigate(Routes.productImages(storeId))
            }
        }
        composable(
            route = Routes.LOADING) {
            LoadingSection()
        }
        composable(
            route = Routes.PRODUCT_IMAGES,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) {
            ProductImagesScreen()
        }

    }
}