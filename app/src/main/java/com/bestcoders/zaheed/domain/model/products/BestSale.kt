package com.bestcoders.zaheed.domain.model.products

import kotlin.random.Random

data class BestSale(
    val id: Int,
    val discount: String,
    var isFavorite: Boolean,
    val mainPrice: String,
    val name: String,
    val rating: Int,
    val sales: Int,
    val strokedPrice: String,
    val thumbnailImage: String,
    val shopCategory: ShopCategory,
) {
    companion object {

        // Function to generate the dummy BestSale objects
        fun generateDummyBestSales(): List<BestSale> {
            val dummyBestSales = mutableListOf<BestSale>()
            val dummyImage =
                "https://media.istockphoto.com/id/479382464/vector/blue-sport-shoes-for-running.jpg?s=612x612&w=0&k=20&c=v_fkHkodSuuZnH3dswhtKJz8aZmNgwxjfYOQ0ocvOdA="

            for (i in 1..100) {
                val id = i
                val discount = randomDiscount()
                val isFavorite = Random.nextBoolean()
                val mainPrice = randomPrice()
                val name = "ProductStoreDetailsResponse $i"
                val rating = Random.nextInt(1, 6)
                val sales = Random.nextInt(100, 1000)
                val strokedPrice = randomPrice()
                val shopCategory = shopCategories[Random.nextInt(0, shopCategories.size)]

                dummyBestSales.add(
                    BestSale(
                        id,
                        discount,
                        isFavorite,
                        mainPrice,
                        name,
                        rating,
                        sales,
                        strokedPrice,
                        dummyImage,
                        shopCategory
                    )
                )
            }

            return dummyBestSales
        }


        // Dummy shopProductDetailsResponse categories
       private val shopCategories = listOf(
            ShopCategory(1, "New & Featured"),
            ShopCategory(2, "Men"),
            ShopCategory(3, "Women"),
            ShopCategory(4, "Kids")
        )

        // Function to generate a random discount string
        private fun randomDiscount(): String {
            return "${Random.nextInt(5, 51)}"
        }

        // Function to generate a random price with two decimal places
        private fun randomPrice(): String {
            return String.format("%.2f", Random.nextDouble(50.0, 300.0))
        }

    }
}
