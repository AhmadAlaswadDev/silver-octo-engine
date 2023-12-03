package com.bestcoders.zaheed.data.repository

import android.content.Context
import android.util.Log
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.toJson
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.network.getMessageFromRequestCode
import com.bestcoders.zaheed.data.remote.ProductsApi
import com.bestcoders.zaheed.data.remote.model.order.CreateOrderRequest
import com.bestcoders.zaheed.data.remote.model.order.GetPurchaseHistoryRequest
import com.bestcoders.zaheed.data.remote.model.order.GetTelrUrlRequest
import com.bestcoders.zaheed.data.remote.model.order.UpdateOrderRequest
import com.bestcoders.zaheed.data.remote.model.products.AddRemoveProductFavoriteRequest
import com.bestcoders.zaheed.data.remote.model.products.AddRemoveStoreFavoriteRequest
import com.bestcoders.zaheed.data.remote.model.products.HomeLayoutRequest
import com.bestcoders.zaheed.data.remote.model.products.ProductVariationDetailsRequest
import com.bestcoders.zaheed.data.remote.model.products.cart.AddProductToCartRequest
import com.bestcoders.zaheed.data.remote.model.products.cart.ChangeProductQuantityRequest
import com.bestcoders.zaheed.data.remote.model.products.cart.RemoveProductFromCartRequest
import com.bestcoders.zaheed.domain.model.products.CartByStoreModel
import com.bestcoders.zaheed.domain.model.products.CartModel
import com.bestcoders.zaheed.domain.model.products.Favorite
import com.bestcoders.zaheed.domain.model.products.GetPurchaseHistory
import com.bestcoders.zaheed.domain.model.products.HomeLayout
import com.bestcoders.zaheed.domain.model.products.ProductDetails
import com.bestcoders.zaheed.domain.model.products.ProductVariationDetails
import com.bestcoders.zaheed.domain.model.products.Search
import com.bestcoders.zaheed.domain.model.products.StoreDetails
import com.bestcoders.zaheed.domain.model.products.Subcategory
import com.bestcoders.zaheed.domain.model.track.Order
import com.bestcoders.zaheed.domain.model.track.OrderTrack
import com.bestcoders.zaheed.domain.model.track.PickupPoint
import com.bestcoders.zaheed.domain.repository.ProductsRepository


class ProductsRepositoryImpl(
    private val api: ProductsApi,
    private val context: Context
) : ProductsRepository {

    override suspend fun getHomeLayout(
        userToken: String?,
        page: Int,
        latitude: String,
        longitude: String,
        priceRangeMax: String,
        priceRangeMin: String,
        sortBy: String,
        distance: String,
        amountOfDiscount: String
    ): Resource<HomeLayout> {
        return try {
            val response = api.getHomeLayout(
                token = if (userToken.isNullOrEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + userToken
                },
                page = page,
                latitude = latitude,
                longitude = longitude,
                homeLayoutRequest = HomeLayoutRequest(
                    distance = distance,
                    amountOfDiscount = amountOfDiscount,
                    priceRangeMax = priceRangeMax,
                    priceRangeMin = priceRangeMin,
                    sortBy = sortBy
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toHomeLayout())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.err?.firstNotNullOfOrNull { it.value[0] }
                            ?: body!!.message
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getMainCategory(
        userToken: String?,
        page: Int,
        categoryId: Int,
        latitude: String,
        longitude: String,
        priceRangeMax: String,
        priceRangeMin: String,
        sortBy: String,
        distance: String,
        amountOfDiscount: String
    ): Resource<HomeLayout> {
        return try {
            val response = api.getMainCategory(
                token = if (userToken.isNullOrEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + userToken
                },
                page = page,
                latitude = latitude,
                longitude = longitude,
                categoryId = categoryId,
                homeLayoutRequest = HomeLayoutRequest(
                    distance = distance,
                    amountOfDiscount = amountOfDiscount,
                    priceRangeMax = priceRangeMax,
                    priceRangeMin = priceRangeMin,
                    sortBy = sortBy
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toHomeLayout())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.err?.firstNotNullOfOrNull { it.value[0] }
                            ?: body!!.message.toString()
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getSubcategory(
        userToken: String?,
        page: Int,
        subcategoryId: Int,
        latitude: String,
        longitude: String,
        priceRangeMax: String,
        priceRangeMin: String,
        sortBy: String,
        distance: String,
        amountOfDiscount: String
    ): Resource<Subcategory> {
        return try {
            val response = api.getSubcategory(
                token = if (userToken.isNullOrEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + userToken
                },
                page = page,
                latitude = latitude,
                longitude = longitude,
                subcategoryId = subcategoryId,
                homeLayoutRequest = HomeLayoutRequest(
                    distance = distance,
                    amountOfDiscount = amountOfDiscount,
                    priceRangeMax = priceRangeMax,
                    priceRangeMin = priceRangeMin,
                    sortBy = sortBy
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toSubcategory())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.`null`?.firstNotNullOfOrNull { it.value.toString() }
                            ?: context.getString(R.string.un_known_error))
                    Resource.Error(body?.`null`?.firstNotNullOfOrNull { it.value.toString() }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getWishlist(
        page: Int,
        userToken: String,
        latitude: String,
        longitude: String
    ): Resource<Favorite> {
        return try {
            val response = api.getWishlist(
                token = Constants.BEARER_TOKEN + userToken,
                page = page,
                latitude = latitude,
                longitude = longitude,
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toFavorite())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.err?.firstNotNullOfOrNull { it.value[0] }
                            ?: context.getString(R.string.un_known_error))
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getFavoriteShops(
        page: Int,
        userToken: String,
        latitude: String,
        longitude: String
    ): Resource<Favorite> {
        return try {
            val response = api.getFavoriteShops(
                token = Constants.BEARER_TOKEN + userToken,
                page = page,
                latitude = latitude,
                longitude = longitude,
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toFavorite())
                } else {
                    Log.e(Constants.EXCEPTION_TAG, body?.err?.firstNotNullOfOrNull { it.value[0] } ?: context.getString(R.string.un_known_error))
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }


    override suspend fun addProductToWishlist(userToken: String, productId: Int): Resource<Unit> {
        return try {
            val response = api.addProductToWishlist(
                token = Constants.BEARER_TOKEN + userToken,
                addRemoveProductFavoriteRequest = AddRemoveProductFavoriteRequest(productId.toString())
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(Unit)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.errors?.firstNotNullOfOrNull { it.value.toString() }
                            ?: context.getString(R.string.un_known_error))
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.errors?.firstNotNullOfOrNull { it.value.toString() }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun removeProductFromWishlist(
        userToken: String,
        productId: Int
    ): Resource<Unit> {
        return try {
            val response = api.removeProductFromWishlist(
                token = Constants.BEARER_TOKEN + userToken,
                addRemoveProductFavoriteRequest = AddRemoveProductFavoriteRequest(productId.toString())
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(Unit)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.errors?.firstNotNullOfOrNull { it.value.toString() }
                            ?: context.getString(R.string.un_known_error))
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.errors?.firstNotNullOfOrNull { it.value.toString() }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }


    override suspend fun addStoreToFavorite(userToken: String, storeId: Int): Resource<Unit> {
        return try {
            val response = api.addStoreToFavorite(
                token = Constants.BEARER_TOKEN + userToken,
                addRemoveStoreFavoriteRequest = AddRemoveStoreFavoriteRequest(storeId = storeId)
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(Unit)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.errors?.firstNotNullOfOrNull { it.value.toString() }
                            ?: context.getString(R.string.un_known_error))
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.errors?.firstNotNullOfOrNull { it.value.toString() }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun removeStoreFromFavorite(
        userToken: String,
        storeId: Int
    ): Resource<Unit> {
        return try {
            val response = api.removeStoreFromFavorite(
                token = Constants.BEARER_TOKEN + userToken,
                addRemoveStoreFavoriteRequest = AddRemoveStoreFavoriteRequest(storeId = storeId)
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(Unit)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.errors?.firstNotNullOfOrNull { it.value.toString() }
                            ?: context.getString(R.string.un_known_error))
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.errors?.firstNotNullOfOrNull { it.value.toString() }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getSearchedProducts(
        userToken: String?,
        page: Int,
        productName: String,
        latitude: String,
        longitude: String
    ): Resource<Search> {
        return try {
            val response = api.getSearchedProducts(
                token = if (userToken.isNullOrEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + userToken
                },
                page = page,
                latitude = latitude,
                longitude = longitude,
                name = productName
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toSearch())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.err?.firstNotNullOfOrNull { it.value[0] }
                            ?: context.getString(R.string.un_known_error))
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getSearchedStores(
        userToken: String?,
        page: Int,
        storeName: String,
        latitude: String,
        longitude: String
    ): Resource<Search> {
        return try {
            val response = api.getSearchedStores(
                token = if (userToken.isNullOrEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + userToken
                },
                page = page,
                latitude = latitude,
                longitude = longitude,
                name = storeName
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toSearch())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.err?.firstNotNullOfOrNull { it.value[0] }
                            ?: context.getString(R.string.un_known_error))
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getStoreDetails(
        storeId: Int,
        latitude: String,
        longitude: String,
        page: Int
    ): Resource<StoreDetails> {
        return try {
            val response = api.getStoreDetails(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                latitude = latitude,
                longitude = longitude,
                storeId = storeId,
                page = page
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toStoreDetails())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun followStore(storeId: Int): Resource<Unit> {
        return try {
            val response = api.followStore(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                storeId = storeId
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(Unit)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun unfollowStore(storeId: Int): Resource<Unit> {
        return try {
            val response = api.unfollowStore(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                storeId = storeId
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(Unit)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getProductDetails(
        userToken: String?,
        latitude: String,
        longitude: String,
        productId: Int
    ): Resource<ProductDetails> {
        return try {
            val response = api.getProductDetails(
                token = if (userToken.isNullOrEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + userToken
                },
                latitude = latitude,
                longitude = longitude,
                productId = productId
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toProductDetails())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.err?.firstNotNullOfOrNull { it.value[0] }
                            ?: context.getString(R.string.un_known_error))
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getProductVariationDetails(
        productId: Int,
        variant: String
    ): Resource<ProductVariationDetails> {
        return try {
            val response = api.getProductVariationDetails(
                productId = productId,
                productVariationDetailsRequest = ProductVariationDetailsRequest(variant = variant),
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toProductVariationDetails())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun addProductToCart(
        productId: Int,
        variant: String,
        quantity: Int
    ): Resource<String> {
        return try {
            val response = api.addProductToCart(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                tempToken = Constants.tempToken.ifEmpty {
                    null
                },
                addProductToCartRequest = AddProductToCartRequest(
                    id = productId,
                    variant = variant,
                    quantity = quantity
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.temp_user ?: "")
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun removeProductFromCart(
        productId: Int,
        variant: String,
    ): Resource<Unit> {
        return try {
            val response = api.removeProductFromCart(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                tempToken = Constants.tempToken.ifEmpty {
                    null
                },
                removeProductFromCartRequest = RemoveProductFromCartRequest(
                    id = productId,
                    variant = variant,
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(Unit)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun changeProductQuantity(
        productId: String,
        quantity: String,
        variant: String
    ): Resource<Unit> {
        return try {
            val response = api.changeProductQuantity(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                tempToken = Constants.tempToken.ifEmpty {
                    null
                },
                changeProductQuantityRequest = ChangeProductQuantityRequest(
                    id = productId,
                    variant = variant,
                    quantity = quantity
                )
            )
            Log.e(
                "AFSDVRGESD",
                "changeProductQuantity: ${
                    ChangeProductQuantityRequest(
                        id = productId,
                        variant = variant,
                        quantity = quantity
                    ).toJson()
                }",
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(Unit)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.err?.firstNotNullOfOrNull { it.value[0] }
                            ?: body?.message.toString()
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getCarts(latitude: String, longitude: String): Resource<CartModel> {
        return try {
            val response = api.getCarts(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                tempToken = Constants.tempToken.ifEmpty {
                    null
                },
                latitude = latitude,
                longitude = longitude,
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toCartModel())
                } else {
                    Log.e(Constants.EXCEPTION_TAG, body?.message ?: context.getString(R.string.un_known_error))
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getCartByStore(
        storeId: Int,
        latitude: String,
        longitude: String
    ): Resource<CartByStoreModel> {
        return try {
            val response = api.getCartByStore(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                tempToken = Constants.tempToken.ifEmpty {
                    null
                },
                latitude = latitude,
                longitude = longitude,
                storeId = storeId,
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toCartByStoreModel())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getStorePickupLocations(storeId: Int): Resource<List<PickupPoint>> {
        return try {
            val response = api.getStorePickupLocations(
                storeId = storeId,
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.data!!.map { it.toStorePickupLocation() })
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun createOrder(
        storeId: String,
        paymentType: String,
        preferredTimeToPickUp: String,
        pickupLocationId: String,
    ): Resource<Int> {
        return try {
            val response = api.createOrder(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                createOrderRequest = CreateOrderRequest(
                    storeId = storeId,
                    paymentType = paymentType,
                    preferredTimeToPickUp = preferredTimeToPickUp,
                    pickupLocationId = pickupLocationId,
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    Resource.Success(body.data.order_id)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun updateOrder(
        orderId: String,
        paymentType: String,
        preferredTimeToPickUp: String,
        pickupLocationId: String,
    ): Resource<Int> {
        return try {
            val response = api.updateOrder(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                updateOrderRequest = UpdateOrderRequest(
                    orderId = orderId,
                    paymentType = paymentType,
                    preferredTimeToPickUp = preferredTimeToPickUp,
                    pickupLocationId = pickupLocationId,
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    Resource.Success(body.data.order_id)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message ?: context.getString(R.string.un_known_error)
                    )
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getTelrUrl(orderId: String): Resource<String> {
        return try {
            val response = api.getTelrUrl(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                getTelrUrlRequest = GetTelrUrlRequest(
                    orderId = orderId,
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    Resource.Success(body.data.redirect_url)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.errors?.firstNotNullOfOrNull { it.value.toString() }
                            ?: body?.message.toString())
                    Resource.Error(body?.errors?.firstNotNullOfOrNull { it.value.toString() }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getOrderTrack(orderId: Int): Resource<OrderTrack> {
        return try {
            val response = api.getOrderTrack(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                orderId = orderId,
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toOrderTrack())
                } else {
                    Log.e(Constants.EXCEPTION_TAG, body?.message.toString())
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getPurchaseHistory(
        page: Int,
        paymentStatus: String,
        orderStatus: String,
        searchValue: String
    ): Resource<GetPurchaseHistory> {
        return try {
            val response = api.getPurchaseHistory(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                page = page,
                getPurchaseHistoryRequest = GetPurchaseHistoryRequest(
                    paymentStatus = paymentStatus,
                    orderStatus = orderStatus,
                    searchValue = searchValue
                ),
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toPurchaseHistory())
                } else {
                    Log.e(Constants.EXCEPTION_TAG, body?.message.toString())
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getPurchaseHistoryDetails(
        orderId: Int,
    ): Resource<Order> {
        return try {
            val response = api.getPurchaseHistoryDetails(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                orderId = orderId,
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    Resource.Success(body.data.toOrder())
                } else {
                    Log.e(Constants.EXCEPTION_TAG, body?.message.toString())
                    Resource.Error(body?.message ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }
}
