package com.example.aswcms.ui.navigation

object Routes {
    const val PRODUCT_IMAGES = "product_images/{productId}"
    const val STORES = "stores"
    const val STORE_OVERVIEW = "store_overview/{storeId}"
    const val PRODUCTS = "products/{storeId}"
    const val PRODUCT = "product/{productId}"
    const val ORDERS = "orders"
    const val CUSTOMERS = "customers"
    const val INVENTORY = "inventory"
    const val LOADING = "loading"
    const val ACCOUNT = "account"

    fun storeOverview(storeId: Int) = "store_overview/$storeId"
    fun products(storeId: Int) = "products/$storeId"
    fun product(productId: Int) = "product/$productId"
    fun productImages(productId: Int) = "product_images/$productId"
}