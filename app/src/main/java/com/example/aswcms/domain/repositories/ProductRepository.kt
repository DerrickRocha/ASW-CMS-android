package com.example.aswcms.domain.repositories

import com.example.aswcms.di.IoDispatcher
import com.example.aswcms.domain.models.Product
import com.example.aswcms.domain.models.ProductImage
import com.example.aswcms.domain.models.ProductOption
import com.example.aswcms.domain.models.ProductOptionChoice
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(@param:IoDispatcher val dispatcher: CoroutineDispatcher) {

    suspend fun getProduct(id: Int): Product {
        return withContext(dispatcher) {
            val choices = listOf(ProductOptionChoice(1, 1, 1, "5 Seeds", 100, 0))
            val options = listOf(ProductOption(1, 1, "Quantity", choices))
            delay(500)
            Product(
                1,
                "Blue Dream",
                "hello world",
                0,
                true,
                "https://agilesouthwest-images.s3.us-east-2.amazonaws.com/0627c781-22e7-4f8f-81b4-63b9be8aa682.jpeg",
                options,
                mainImage = ProductImage(
                    1,
                    1,
                    "https://agilesouthwest-images.s3.us-east-2.amazonaws.com/0627c781-22e7-4f8f-81b4-63b9be8aa682.jpeg",
                    true
                ),
                additionImages = listOf(
                    ProductImage(
                        1,
                        1,
                        "https://agilesouthwest-images.s3.us-east-2.amazonaws.com/0627c781-22e7-4f8f-81b4-63b9be8aa682.jpeg",
                        false
                    ),
                    ProductImage(
                        2,
                        1,
                        "https://agilesouthwest-images.s3.us-east-2.amazonaws.com/0627c781-22e7-4f8f-81b4-63b9be8aa682.jpeg",
                        false
                    ),
                    ProductImage(
                        3,
                        1,
                        "https://agilesouthwest-images.s3.us-east-2.amazonaws.com/0627c781-22e7-4f8f-81b4-63b9be8aa682.jpeg",
                        false
                    )
                )
            )
        }
    }
}