package com.example.aswcms.domain.models

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val basePrice: Int,
    val isActive: Boolean,
    val imageUrl: String,
    val options: List<ProductOption>
)

data class ProductOption(val id: Int, val productId: Int, val name: String, val choices: List<ProductOptionChoice>)

data class ProductOptionChoice(val id: Int, val productId: Int, val productOptionId: Int, val name: String, val priceDelta: Int, val salePriceDelta: Int)

data class ProductImage(val id: Int, val productId: Int, val url: String, val isMain: Boolean)
