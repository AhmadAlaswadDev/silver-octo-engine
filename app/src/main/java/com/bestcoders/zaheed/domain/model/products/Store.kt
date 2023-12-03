package com.bestcoders.zaheed.domain.model.products

import androidx.compose.runtime.snapshots.SnapshotStateList


data class Store(
    val id: Int,
    val address: String,
    val distance: Double,
    var isFavorite: Boolean,
    val logo: String,
    val name: String,
    val rating: Int,
    val saved: String,
    val category: ShopCategory?,
    val products: SnapshotStateList<Product>?,
    val branchName: String?,
    val isSubscribed: Boolean?
)

/*
 companion object{
        fun createDummyCategories(): List<ShopCategory> {
            return listOf(
                ShopCategory(1, "Electronics"),
                ShopCategory(2, "Clothing"),
                ShopCategory(3, "Grocery"),
                ShopCategory(4, "Books"),
                ShopCategory(5, "Sports")
            )
        }

        fun createDummyProducts(): List<Product> {
            return listOf<Product>(
                Product(
                    id = 1,
                    discount = "10",
                    isFavorite = false,
                    mainPrice = "599.99",
                    name = "Smartphone",
                    rating = 4,
                    sales = 50,
                    strokedPrice = "699.99",
                    thumbnailImage = "https://media.istockphoto.com/id/479382464/vector/blue-sport-shoes-for-running.jpg?s=612x612&w=0&k=20&c=v_fkHkodSuuZnH3dswhtKJz8aZmNgwxjfYOQ0ocvOdA="
                ),
                Product(
                    id = 2,
                    discount = "10",
                    isFavorite = false,
                    mainPrice = "599.99",
                    name = "Smartphone",
                    rating = 4,
                    sales = 50,
                    strokedPrice = "699.99",
                    thumbnailImage = "https://media.istockphoto.com/id/479382464/vector/blue-sport-shoes-for-running.jpg?s=612x612&w=0&k=20&c=v_fkHkodSuuZnH3dswhtKJz8aZmNgwxjfYOQ0ocvOdA="
                ),
                Product(
                    id = 3,
                    discount = "10",
                    isFavorite = false,
                    mainPrice = "599.99",
                    name = "Smartphone",
                    rating = 4,
                    sales = 50,
                    strokedPrice = "699.99",
                    thumbnailImage = "https://media.istockphoto.com/id/479382464/vector/blue-sport-shoes-for-running.jpg?s=612x612&w=0&k=20&c=v_fkHkodSuuZnH3dswhtKJz8aZmNgwxjfYOQ0ocvOdA="
                ),
                Product(
                    id = 4,
                    discount = "10",
                    isFavorite = false,
                    mainPrice = "599.99",
                    name = "Smartphone",
                    rating = 4,
                    sales = 50,
                    strokedPrice = "699.99",
                    thumbnailImage = "https://media.istockphoto.com/id/479382464/vector/blue-sport-shoes-for-running.jpg?s=612x612&w=0&k=20&c=v_fkHkodSuuZnH3dswhtKJz8aZmNgwxjfYOQ0ocvOdA="
                ),
                // Add other dummy products here with their respective image resource IDs
            )
        }

        fun createDummyStores(): List<Store> {
            val dummyCategories = createDummyCategories()
            val dummyProducts = createDummyProducts()

            val stores = mutableListOf<Store>()
            var storeId = 1

            for (i in 1..8) {
                val category = dummyCategories.random()

                // Generate random products for each store
                val randomProducts = dummyProducts.shuffled().take((1))

                stores.add(
                    Store(
                        id = storeId,
                        address = "Address ${storeId}",
                        distance = (1..10).random().toDouble(),
                        isFavorite = false,
                        logo = "https://i.insider.com/53d29d5c6bb3f7a80617ada8?width=1000&format=jpeg&auto=webp",
                        name = "Store ${storeId}",
                        rating = (3..5).random(),
                        saved = "2023-08-06",
                        category = category,
                        products = randomProducts.toMutableStateList()
                    )
                )

                storeId++
            }

            return stores
        }
    }
 */
