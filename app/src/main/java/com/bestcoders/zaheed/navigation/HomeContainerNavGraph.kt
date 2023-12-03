package com.bestcoders.zaheed.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.OrderFilter
import com.bestcoders.zaheed.presentation.screens.cart.CartEvent
import com.bestcoders.zaheed.presentation.screens.cart.CartScreen
import com.bestcoders.zaheed.presentation.screens.cart.CartViewModel
import com.bestcoders.zaheed.presentation.screens.category.CategoryEvent
import com.bestcoders.zaheed.presentation.screens.category.CategoryScreen
import com.bestcoders.zaheed.presentation.screens.category.CategoryViewModel
import com.bestcoders.zaheed.presentation.screens.checkout.CheckoutEvent
import com.bestcoders.zaheed.presentation.screens.checkout.CheckoutScreen
import com.bestcoders.zaheed.presentation.screens.checkout.CheckoutViewModel
import com.bestcoders.zaheed.presentation.screens.confirm_order.ConfirmOrderEvent
import com.bestcoders.zaheed.presentation.screens.confirm_order.ConfirmOrderScreen
import com.bestcoders.zaheed.presentation.screens.confirm_order.ConfirmOrderViewModel
import com.bestcoders.zaheed.presentation.screens.favorite.FavoriteEvent
import com.bestcoders.zaheed.presentation.screens.favorite.FavoriteScreen
import com.bestcoders.zaheed.presentation.screens.favorite.FavoriteViewModel
import com.bestcoders.zaheed.presentation.screens.filter.FilterScreen
import com.bestcoders.zaheed.presentation.screens.filter.FilterState
import com.bestcoders.zaheed.presentation.screens.filter.FilterViewModel
import com.bestcoders.zaheed.presentation.screens.home.HomeScreen
import com.bestcoders.zaheed.presentation.screens.home.HomeViewModel
import com.bestcoders.zaheed.presentation.screens.home_container.HomeContainerState
import com.bestcoders.zaheed.presentation.screens.map.MapScreen
import com.bestcoders.zaheed.presentation.screens.map.MapViewModel
import com.bestcoders.zaheed.presentation.screens.order_filter.OrderFilterScreen
import com.bestcoders.zaheed.presentation.screens.order_filter.OrderFilterState
import com.bestcoders.zaheed.presentation.screens.order_filter.OrderFilterViewModel
import com.bestcoders.zaheed.presentation.screens.payment.PaymentScreen
import com.bestcoders.zaheed.presentation.screens.payment_success.PaymentSuccessEvent
import com.bestcoders.zaheed.presentation.screens.payment_success.PaymentSuccessScreen
import com.bestcoders.zaheed.presentation.screens.payment_success.PaymentSuccessViewModel
import com.bestcoders.zaheed.presentation.screens.product_details.ProductDetailsEvent
import com.bestcoders.zaheed.presentation.screens.product_details.ProductDetailsScreen
import com.bestcoders.zaheed.presentation.screens.product_details.ProductDetailsViewModel
import com.bestcoders.zaheed.presentation.screens.profile_screens.about_us.AboutUsEvent
import com.bestcoders.zaheed.presentation.screens.profile_screens.about_us.AboutUsScreen
import com.bestcoders.zaheed.presentation.screens.profile_screens.about_us.AboutUsViewModel
import com.bestcoders.zaheed.presentation.screens.profile_screens.edit_profile.EditProfileScreen
import com.bestcoders.zaheed.presentation.screens.profile_screens.edit_profile.EditProfileViewModel
import com.bestcoders.zaheed.presentation.screens.profile_screens.faq.FAQEvent
import com.bestcoders.zaheed.presentation.screens.profile_screens.faq.FAQScreen
import com.bestcoders.zaheed.presentation.screens.profile_screens.faq.FAQViewModel
import com.bestcoders.zaheed.presentation.screens.profile_screens.order_details.OrderDetailsEvent
import com.bestcoders.zaheed.presentation.screens.profile_screens.order_details.OrderDetailsScreen
import com.bestcoders.zaheed.presentation.screens.profile_screens.order_details.OrderDetailsViewModel
import com.bestcoders.zaheed.presentation.screens.profile_screens.order_history.OrderHistoryEvent
import com.bestcoders.zaheed.presentation.screens.profile_screens.order_history.OrderHistoryScreen
import com.bestcoders.zaheed.presentation.screens.profile_screens.order_history.OrderHistoryViewModel
import com.bestcoders.zaheed.presentation.screens.profile_screens.profile.ProfileScreen
import com.bestcoders.zaheed.presentation.screens.profile_screens.profile.ProfileViewModel
import com.bestcoders.zaheed.presentation.screens.profile_screens.update_password.UpdatePasswordScreen
import com.bestcoders.zaheed.presentation.screens.profile_screens.update_password.UpdatePasswordViewModel
import com.bestcoders.zaheed.presentation.screens.search.SearchScreen
import com.bestcoders.zaheed.presentation.screens.search.SearchViewModel
import com.bestcoders.zaheed.presentation.screens.store_details.StoreDetailsEvent
import com.bestcoders.zaheed.presentation.screens.store_details.StoreDetailsScreen
import com.bestcoders.zaheed.presentation.screens.store_details.StoreDetailsViewModel
import com.bestcoders.zaheed.presentation.screens.store_return_policy.StoreReturnPolicyScreen
import com.bestcoders.zaheed.presentation.screens.subcategory.SubcategoryEvent
import com.bestcoders.zaheed.presentation.screens.subcategory.SubcategoryScreen
import com.bestcoders.zaheed.presentation.screens.subcategory.SubcategoryViewModel
import com.bestcoders.zaheed.presentation.screens.track.OrderTrackEvent
import com.bestcoders.zaheed.presentation.screens.track.OrderTrackScreen
import com.bestcoders.zaheed.presentation.screens.track.OrderTrackViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun HomeContainerNavGraph(
    rootNavController: NavController,
    navController: NavHostController,
    homePadding: PaddingValues,
    homeContainerState: MutableState<HomeContainerState>,
    loadNextItems: (nextPage: Int, filter: Filter) -> Unit,
    onReloadClicked: () -> Unit,
    getHomeLayoutWithCustomLocation: (Double, Double) -> Unit,
    getHomeLayoutWithFilter: (Filter) -> Unit,
    changeBottomBarIndex: (String) -> Unit,
    requestLocationPermission: () -> Unit,
    permissionGranted: MutableState<Boolean>,
    cartItems: MutableState<Int>,
) {

    var _filter: Filter by remember {
        mutableStateOf(Filter())
    }
    var _orderFilter: OrderFilter by remember {
        mutableStateOf(OrderFilter())
    }


    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
        ) { entry ->
            val viewModel = hiltViewModel<HomeViewModel>()
            val uiEvent = viewModel.uiEvent
            val state = viewModel.state
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                changeBottomBarIndex(Screen.Home.route)
            }
            viewModel.resetState(
                state.value.copy(
                    homeLayoutList = homeContainerState.value.homeLayoutList,
                    success = homeContainerState.value.success,
                    userLatitude = homeContainerState.value.userLatitude,
                    userLongitude = homeContainerState.value.userLongitude,
                    userToken = homeContainerState.value.userToken,
                    endReached = homeContainerState.value.endReached,
                    isNearbyLoading = homeContainerState.value.isLoading,
                )
            )
            HomeScreen(
                state = state,
                uiEvent = uiEvent,
                resetState = viewModel::resetState,
                loadNextItems = { page ->
                    loadNextItems(page, _filter)
                },
                homePadding = homePadding,
                onReloadClicked = { onReloadClicked() },
                onEvent = viewModel::onEvent,
                requestLocationPermission = requestLocationPermission,
                permissionGranted = permissionGranted,
                navigateToSearchScreen = {
                    navController.navigate(Screen.Search.route)
                },
                navigateToCategoryScreen = { categoryId, categoryName ->
                    navController.navigate(Screen.Category.withArgs(categoryId, categoryName))
                },
                navigateToFilterScreen = {
                    navController.navigate(Screen.Filter.route)
                },
                navigateToProductDetails = { productId ->
                    navController.navigate(Screen.ProductDetails.withArgs(productId))
                },
                navigateToSignInScreen = {
                    rootNavController.navigate(Screen.SignIn.route)
                },
                navigateToMapScreen = {
                    navController.navigate(Screen.Map.route)
                },
                navigateToStoreDetails = { storeId ->
                    navController.navigate(Screen.StoreDetails.withArgs(storeId))
                },
            )
        }

        composable(
            route = Screen.Cart.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
        ) {
            val viewModel = hiltViewModel<CartViewModel>()
            val state = viewModel.state
            val onEvent = viewModel::onEvent
            LaunchedEffect(Unit) {
                changeBottomBarIndex(Screen.Cart.route)
                viewModel.resetState(
                    state.value.copy(
                        userLatitude = homeContainerState.value.userLatitude,
                        userLongitude = homeContainerState.value.userLongitude
                    )
                )
                onEvent(CartEvent.GetCarts)
                return@LaunchedEffect
            }
            CartScreen(
                state = state,
                onEvent = onEvent,
                uiEvent = viewModel.uiEvent,
                homePadding = homePadding,
                resetState = viewModel::resetState,
                cartItems = cartItems,
                navigateToCheckout = { storeId ->
                    navController.navigate(Screen.Checkout.withArgs(storeId))
                },
                navigateToProductDetails = { productId ->
                    navController.navigate(Screen.ProductDetails.withArgs(productId))
                },
                navigateToHome = {
                    changeBottomBarIndex(Screen.Home.route)
                    navController.navigate(Screen.Home.route)
                }
            )
        }
        composable(
            route = Screen.Checkout.route + "/{storeId}",
            arguments = listOf(
                navArgument("storeId") {
                    type = NavType.IntType
                    nullable = false
                },
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
        ) { entry ->
            val viewModel = hiltViewModel<CheckoutViewModel>()
            val state = viewModel.state
            val onEvent = viewModel::onEvent
            LaunchedEffect(Unit) {
                viewModel.resetState(
                    state.value.copy(
                        userLatitude = homeContainerState.value.userLatitude,
                        userLongitude = homeContainerState.value.userLongitude
                    )
                )
                onEvent(CheckoutEvent.GetCartByStore(storeId = entry.arguments!!.getInt("storeId")))
                return@LaunchedEffect
            }
            CheckoutScreen(
                state = state,
                onEvent = onEvent,
                uiEvent = viewModel.uiEvent,
                resetState = viewModel::resetState,
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToConfirmOrderScreen = { storeId ->
                    navController.navigate(Screen.ConfirmOrder.withArgs(storeId))
                },
                navigateToHome = {
                    changeBottomBarIndex(Screen.Home.route)
                    navController.navigate(Screen.Home.route)
                }
            )
        }


        composable(
            route = Screen.Favorite.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
        ) {
            val viewModel = hiltViewModel<FavoriteViewModel>()
            val state = viewModel.state
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                changeBottomBarIndex(Screen.Favorite.route)
                viewModel.resetState(
                    state.value.copy(
                        stores = mutableStateListOf(),
                        storesWithProducts = mutableStateListOf(),
                        userLatitude = homeContainerState.value.userLatitude,
                        userLongitude = homeContainerState.value.userLongitude
                    )
                )
                viewModel.onEvent(FavoriteEvent.GetFavoriteProducts(1))
                viewModel.onEvent(FavoriteEvent.GetFavoriteStores(1))
                return@LaunchedEffect
            }
            FavoriteScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                resetState = viewModel::resetState,
                paddingValues = homePadding,
                navigateToStoreDetails = { storeId ->
                    navController.navigate(Screen.StoreDetails.withArgs(storeId))
                },
                navigateToProductDetails = { productId ->
                    navController.navigate(Screen.ProductDetails.withArgs(productId))
                },
                navigateToHome = {
                    changeBottomBarIndex(Screen.Home.route)
                    navController.navigate(Screen.Home.route)
                }
            )
        }
        composable(
            route = Screen.Profile.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
        ) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            val state = viewModel.state
            LaunchedEffect(Unit) {
                changeBottomBarIndex(Screen.Profile.route)
                return@LaunchedEffect
            }
            ProfileScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                resetState = viewModel::resetState,
                navigateToLoginScreen = {
                    rootNavController.navigate(Screen.SignIn.route)
                },
                paddingValues = homePadding,
                navigateToSignInScreen = {
                    rootNavController.navigate(Screen.SignIn.route)
                },
                navigateToEditProfileScreen = {
                    navController.navigate(Screen.EditProfile.route)
                },
                navigateToUpdatePasswordScreen = {
                    navController.navigate(Screen.UpdatePassword.route)
                },
                navigateToOrderHistoryScreen = {
                    navController.navigate(Screen.OrderHistory.route)
                },
                navigateToAboutUsScreen = {
                    navController.navigate(Screen.AboutUs.route)
                },
                navigateToFAQScreen = {
                    navController.navigate(Screen.FAQ.route)
                },
                navigateToPrivacyPoliciesScreen = { type ->
                    rootNavController.navigate(Screen.PrivacyPolicy.withArgs(type))
                },
            )
        }

        // Search
        composable(
            route = Screen.Search.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
        ) {
            val viewModel = hiltViewModel<SearchViewModel>()
            val state = viewModel.state
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                viewModel.resetState(
                    state.value.copy(
                        userLatitude = homeContainerState.value.userLatitude,
                        userLongitude = homeContainerState.value.userLongitude
                    )
                )
            }
            SearchScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                resetState = viewModel::resetState,
                onNavigateBack = {
                    navController.popBackStack()
                },
                navigateToProductDetails = { productId ->
                    navController.navigate(Screen.ProductDetails.withArgs(productId))
                },
                navigateToStoreDetails = { storeId ->
                    navController.navigate(Screen.StoreDetails.withArgs(storeId))
                },
                navigateToSignInScreen = {
                    rootNavController.navigate(Screen.SignIn.route)
                },
                navigateToHome = {
                    changeBottomBarIndex(Screen.Home.route)
                    navController.navigate(Screen.Home.route)
                }
            )
        }

        // Category
        composable(
            route = Screen.Category.route + "/{categoryId}" + "/{categoryName}",
            arguments = listOf(
                navArgument("categoryId") {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument("categoryName") {
                    type = NavType.StringType
                    nullable = false
                },
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { entry ->
            val viewModel = hiltViewModel<CategoryViewModel>()
            val uiEvent = viewModel.uiEvent
            val state = viewModel.state
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                viewModel.resetState(
                    state.value.copy(
                        userLatitude = homeContainerState.value.userLatitude,
                        userLongitude = homeContainerState.value.userLongitude,
                        userToken = homeContainerState.value.userToken,
                        endReached = false,
                        isLoading = false,
                        success = false,
                    ),
                )
                viewModel.onEvent(
                    CategoryEvent.GetCategory(
                        categoryId = entry.arguments!!.getInt("categoryId"),
                        page = 1,
                        amountOfDiscount = _filter.amountOfDiscount,
                        sortBy = _filter.sortBy,
                        priceRangeMax = _filter.priceRangeMax,
                        priceRangeMin = _filter.priceRangeMin
                    )
                )
                return@LaunchedEffect
            }
            LaunchedEffect(Lifecycle.Event.ON_STOP) {
                _filter = Filter()
            }
            CategoryScreen(
                state = state,
                uiEvent = uiEvent,
                filter = _filter,
                categoryId = entry.arguments!!.getInt("categoryId"),
                categoryName = entry.arguments!!.getString("categoryName").toString(),
                resetState = viewModel::resetState,
                onEvent = viewModel::onEvent,
                navigateToFilterScreen = {
                    navController.navigate(Screen.Filter.route)
                },
                navigateToSubcategoryScreen = { subcategoryId, subcategoryName ->
                    navController.navigate(
                        Screen.Subcategory.withArgs(
                            subcategoryId,
                            subcategoryName
                        )
                    )
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                navigateToProductDetails = { productId ->
                    navController.navigate(Screen.ProductDetails.withArgs(productId))
                },
                navigateToSelectLocation = {
                    navController.navigate(Screen.Home.route)
                    navController.navigate(Screen.Map.route)
                },
                navigateToSignInScreen = {
                    rootNavController.navigate(Screen.SignIn.route)
                },
                onReloadClicked = {
                    viewModel.onEvent(
                        CategoryEvent.GetCategory(
                            categoryId = entry.arguments!!.getInt("categoryId"),
                            page = 1,
                            amountOfDiscount = _filter.amountOfDiscount,
                            sortBy = _filter.sortBy,
                            priceRangeMax = _filter.priceRangeMax,
                            priceRangeMin = _filter.priceRangeMin
                        )
                    )
                },
                navigateToStoreDetails = { storeId ->
                    navController.navigate(Screen.StoreDetails.withArgs(storeId))
                },
            )
        }
        // Subcategory
        composable(
            route = Screen.Subcategory.route + "/{subcategoryId}" + "/{subcategoryName}",
            arguments = listOf(
                navArgument("subcategoryId") {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument("subcategoryName") {
                    type = NavType.StringType
                    nullable = false
                },
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { entry ->
            val viewModel = hiltViewModel<SubcategoryViewModel>()
            val uiEvent = viewModel.uiEvent
            val state = viewModel.state
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                viewModel.resetState(
                    state.value.copy(
                        userLatitude = homeContainerState.value.userLatitude,
                        userLongitude = homeContainerState.value.userLongitude,
                        userToken = homeContainerState.value.userToken,
                        stores = mutableStateListOf(),
                        endReachedProducts = false,
                        isLoading = false,
                        success = false,
                        paginationMetaProducts = null
                    )
                )
                viewModel.onEvent(
                    SubcategoryEvent.GetSubcategory(
                        subcategoryId = entry.arguments!!.getInt("subcategoryId"),
                        page = 1,
                        amountOfDiscount = _filter.amountOfDiscount,
                        sortBy = _filter.sortBy,
                        priceRangeMax = _filter.priceRangeMax,
                        priceRangeMin = _filter.priceRangeMin
                    )
                )
                return@LaunchedEffect
            }
            LaunchedEffect(Lifecycle.Event.ON_STOP) {
                _filter = Filter()
            }
            SubcategoryScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = uiEvent,
                resetState = viewModel::resetState,
                subcategoryId = entry.arguments!!.getInt("subcategoryId"),
                subcategoryName = entry.arguments!!.getString("subcategoryName").toString(),
                navigateToFilterScreen = {
                    navController.navigate(Screen.Filter.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                navigateToProductDetails = { productId ->
                    navController.navigate(Screen.ProductDetails.withArgs(productId))
                },
                navigateToStoreDetails = { storeId ->
                    navController.navigate(Screen.StoreDetails.withArgs(storeId))
                },
                navigateToSelectLocation = {
                    navController.navigate(Screen.Home.route)
                    navController.navigate(Screen.Map.route)
                },
                navigateToSignInScreen = {
                    rootNavController.navigate(Screen.SignIn.route)
                },
                onReloadClicked = {
                    viewModel.onEvent(
                        SubcategoryEvent.GetSubcategory(
                            subcategoryId = entry.arguments!!.getInt("subcategoryId"),
                            page = 1,
                            amountOfDiscount = _filter.amountOfDiscount,
                            sortBy = _filter.sortBy,
                            priceRangeMax = _filter.priceRangeMax,
                            priceRangeMin = _filter.priceRangeMin
                        )
                    )
                }
            )
        }

        // Filter
        composable(
            route = Screen.Filter.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { entry ->
            val viewModel = hiltViewModel<FilterViewModel>()
            val state = viewModel.state
            val showSortByFilter = rememberSaveable {
                mutableStateOf(true)
            }
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                if (navController.previousBackStackEntry?.destination?.route == Screen.StoreDetails.route + "/{storeId}") {
                    showSortByFilter.value = false
                }
            }
            FilterScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                resetState = viewModel::resetState,
                showSortByFilter = showSortByFilter,
                onNavigateBackWithFilter = { filter: Filter ->
                    if (navController.previousBackStackEntry?.destination?.route == Screen.Home.route) {
                        getHomeLayoutWithFilter(filter)
                    }
                    _filter = filter
                    navController.popBackStack()
                },
                clearFilter = {
                    viewModel.resetState(FilterState())
                    _filter = Filter()
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }


        // Product Details
        composable(
            route = Screen.ProductDetails.route + "/{productId}",
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                    nullable = false
                },
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { entry ->
            val viewModel = hiltViewModel<ProductDetailsViewModel>()
            val state = viewModel.state
            val onEvent = viewModel::onEvent
            val uiEvent = viewModel.uiEvent
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                viewModel.resetState(
                    state.value.copy(
                        userLatitude = homeContainerState.value.userLatitude,
                        userLongitude = homeContainerState.value.userLongitude
                    )
                )
                onEvent(ProductDetailsEvent.GetProductDetails(productId = entry.arguments!!.getInt("productId")))
                return@LaunchedEffect
            }
            ProductDetailsScreen(
                state = state,
                onEvent = onEvent,
                uiEvent = uiEvent,
                resetState = viewModel::resetState,
                navigateToStoreDetails = { storeId ->
                    navController.navigate(Screen.StoreDetails.withArgs(storeId))
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        // Store Details
        composable(
            route = Screen.StoreDetails.route + "/{storeId}",
            arguments = listOf(
                navArgument("storeId") {
                    type = NavType.IntType
                    nullable = false
                },
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { entry ->
            val viewModel = hiltViewModel<StoreDetailsViewModel>()
            val state = viewModel.state
            val onEvent = viewModel::onEvent
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                viewModel.resetState(
                    state.value.copy(
                        userLatitude = homeContainerState.value.userLatitude,
                        userLongitude = homeContainerState.value.userLongitude
                    )
                )
                onEvent(
                    StoreDetailsEvent.GetStoreDetails(
                        storeId = entry.arguments!!.getInt("storeId"),
                        page = 1
                    )
                )
                return@LaunchedEffect
            }
            StoreDetailsScreen(
                state = state,
                onEvent = onEvent,
                uiEvent = viewModel.uiEvent,
                resetState = viewModel::resetState,
                onBackClicked = {
                    navController.popBackStack()
                },
                navigateToReturnPolicy = { storeReturnPolicy ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "returnPolicy",
                        storeReturnPolicy
                    )
                    navController.navigate(Screen.StoreReturnPolicy.route)
                },
                navigateToCheckout = { storeId ->
                    navController.navigate(Screen.Checkout.withArgs(storeId))
                },
                navigateToProductDetails = { productId ->
                    navController.navigate(Screen.ProductDetails.withArgs(productId))
                }
            )
        }

        composable(
            route = Screen.StoreReturnPolicy.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { entry ->
            StoreReturnPolicyScreen(
                returnPolicy = if (entry.destination.route == Screen.StoreReturnPolicy.route) {
                    navController.previousBackStackEntry?.savedStateHandle?.get<String>("returnPolicy")
                        ?: ""
                } else {
                    ""
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.ConfirmOrder.route + "/{storeId}",
            arguments = listOf(
                navArgument("storeId") {
                    type = NavType.IntType
                    nullable = false
                },
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { entry ->
            val viewModel = hiltViewModel<ConfirmOrderViewModel>()
            val state = viewModel.state
            val onEvent = viewModel::onEvent
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                viewModel.resetState(
                    state.value.copy(
                        userLatitude = homeContainerState.value.userLatitude,
                        userLongitude = homeContainerState.value.userLongitude
                    )
                )
                onEvent(ConfirmOrderEvent.GetCartByStore(storeId = entry.arguments!!.getInt("storeId")))
                onEvent(
                    ConfirmOrderEvent.GetStorePickupLocations(
                        storeId = entry.arguments!!.getInt(
                            "storeId"
                        )
                    )
                )
                return@LaunchedEffect
            }
            ConfirmOrderScreen(
                state = state,
                onEvent = onEvent,
                uiEvent = viewModel.uiEvent,
                resetState = viewModel::resetState,
                onBackClick = {
                    navController.popBackStack()
                },
                navigateToPaymentMethod = { telrUrl, orderId ->
                    val encodedUrl = URLEncoder.encode(telrUrl, StandardCharsets.UTF_8.toString())
                    navController.navigate(Screen.Payment.withArgs(orderId, encodedUrl))
                }
            )
        }

        composable(
            route = Screen.Payment.route + "/{orderId}" + "/{telrUrl}",
            arguments = listOf(
                navArgument("orderId") {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument("telrUrl") {
                    type = NavType.StringType
                    nullable = false
                },
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { entry ->
            PaymentScreen(
                paymentMethodUrl = URLDecoder.decode(
                    entry.arguments!!.getString("telrUrl"),
                    StandardCharsets.UTF_8.toString()
                ),
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToPaymentSuccess = {
                    navController.navigate(Screen.PaymentSuccess.withArgs(entry.arguments!!.getInt("orderId")))
                }
            )
        }

        composable(
            route = Screen.PaymentSuccess.route + "/{orderId}",
            arguments = listOf(
                navArgument("orderId") {
                    type = NavType.IntType
                    nullable = false
                },
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { entry ->
            val viewModel = hiltViewModel<PaymentSuccessViewModel>()
            val state = viewModel.state
            val onEvent = viewModel::onEvent
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                viewModel.onEvent(
                    PaymentSuccessEvent.GetOrderDetails(
                        orderId = entry.arguments!!.getInt("orderId")
                    )
                )
            }
            PaymentSuccessScreen(
                state = state,
                onEvent = onEvent,
                uiEvent = viewModel.uiEvent,
                resetState = viewModel::resetState,
                navigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                navigateToTrackOrder = { orderId ->
                    navController.navigate(Screen.OrderTrack.withArgs(orderId))
                }
            )
        }


        composable(
            route = Screen.OrderTrack.route + "/{orderId}",
            arguments = listOf(
                navArgument("orderId") {
                    type = NavType.IntType
                    nullable = false
                },
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { entry ->
            val viewModel = hiltViewModel<OrderTrackViewModel>()
            val state = viewModel.state
            val onEvent = viewModel::onEvent
            LaunchedEffect(Unit) {
                onEvent(OrderTrackEvent.GetOrderTrack(orderId = entry.arguments!!.getInt("orderId")))
                return@LaunchedEffect
            }
            OrderTrackScreen(
                state = state,
                onEvent = onEvent,
                uiEvent = viewModel.uiEvent,
                resetState = viewModel::resetState,
                onReloadClicked = {
                    onEvent(OrderTrackEvent.GetOrderTrack(orderId = entry.arguments!!.getInt("orderId")))
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.EditProfile.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { entry ->
            val viewModel = hiltViewModel<EditProfileViewModel>()
            val state = viewModel.state
            val onEvent = viewModel::onEvent
            EditProfileScreen(
                state = state,
                onEvent = onEvent,
                uiEvent = viewModel.uiEvent,
                resetState = viewModel::resetState,
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToVerifyCodeScreen = { verifyCodeType ->
                    rootNavController.navigate(Screen.VerifyCode.withArgs(null, verifyCodeType))
                }
            )
        }
        composable(
            route = Screen.Map.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Up
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.Down
                )
            },
        ) { entry ->
            val viewModel = hiltViewModel<MapViewModel>()
            val state = viewModel.state
            MapScreen(
                state = state,
                onBackClick = { lat, lng ->
                    viewModel.resetState(
                        state.value.copy(
                            userLatitude = lat,
                            userLongitude = lng
                        )
                    )
                    homeContainerState.value = homeContainerState.value.copy(
                        userLatitude = lat,
                        userLongitude = lng
                    )
                    getHomeLayoutWithCustomLocation(lat, lng)
                    navController.popBackStack()
                },
            )
        }
        composable(
            route = Screen.UpdatePassword.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
        ) {
            val viewModel = hiltViewModel<UpdatePasswordViewModel>()
            val uiEvent = viewModel.uiEvent
            val state = viewModel.state
            UpdatePasswordScreen(
                state = state,
                uiEvent = uiEvent,
                onEvent = viewModel::onEvent,
                resetState = viewModel::resetState,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.OrderHistory.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
        ) {
            val viewModel = hiltViewModel<OrderHistoryViewModel>()
            val uiEvent = viewModel.uiEvent
            val state = viewModel.state
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                viewModel.resetState(
                    state.value.copy(
                        orders = mutableStateListOf(),
                        paginationMeta = null
                    )
                )
                viewModel.onEvent(
                    OrderHistoryEvent.GetOrderHistory(
                        page = 1,
                        orderStatus = _orderFilter.orderFiltersStatus,
                        paymentStatus = _orderFilter.orderPaymentStatus,
                        searchValue = ""
                    )
                )
            }
            OrderHistoryScreen(
                state = state,
                uiEvent = uiEvent,
                onEvent = viewModel::onEvent,
                resetState = viewModel::resetState,
                orderFilter = _orderFilter,
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToOrderFilterScreen = {
                    navController.navigate(Screen.OrderFilter.route)
                },
                navigateToOrderDetails = { orderId ->
                    navController.navigate(Screen.OrderDetails.withArgs(orderId))
                }
            )
        }

        composable(
            route = Screen.AboutUs.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
        ) {
            val viewModel = hiltViewModel<AboutUsViewModel>()
            val uiEvent = viewModel.uiEvent
            val state = viewModel.state
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                viewModel.onEvent(AboutUsEvent.GetAboutUs)
            }
            AboutUsScreen(
                state = state,
                uiEvent = uiEvent,
                onEvent = viewModel::onEvent,
                resetState = viewModel::resetState,
                navigateBack = {
                    navController.popBackStack()
                },
                onReloadClicked = {
                    viewModel.onEvent(AboutUsEvent.GetAboutUs)
                }
            )
        }

        composable(
            route = Screen.FAQ.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
        ) {
            val viewModel = hiltViewModel<FAQViewModel>()
            val uiEvent = viewModel.uiEvent
            val state = viewModel.state
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                viewModel.onEvent(FAQEvent.GetFAQ)
            }
            FAQScreen(
                state = state,
                uiEvent = uiEvent,
                onEvent = viewModel::onEvent,
                resetState = viewModel::resetState,
                navigateBack = {
                    navController.popBackStack()
                },
                onReloadClicked = {
                    viewModel.onEvent(FAQEvent.GetFAQ)
                }
            )
        }

        // Filter
        composable(
            route = Screen.OrderFilter.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
        ) { entry ->
            val viewModel = hiltViewModel<OrderFilterViewModel>()
            val state = viewModel.state
            OrderFilterScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                resetState = viewModel::resetState,
                onNavigateBackWithOrderFilter = { orderFilter: OrderFilter ->
                    _orderFilter = orderFilter
                    navController.popBackStack()
                },
                clearOrderFilter = {
                    viewModel.resetState(OrderFilterState())
                    _orderFilter = OrderFilter()
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Order Details
        composable(
            route = Screen.OrderDetails.route + "/{orderId}",
            arguments = listOf(
                navArgument("orderId") {
                    type = NavType.IntType
                    nullable = false
                },
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        Constants.ANIMATION_DURATION, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
        ) { entry ->
            val viewModel = hiltViewModel<OrderDetailsViewModel>()
            val state = viewModel.state
            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                viewModel.onEvent(
                    OrderDetailsEvent.GetOrderDetails(
                        orderId = entry.arguments!!.getInt(
                            "orderId"
                        )
                    )
                )
            }
            OrderDetailsScreen(
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                resetState = viewModel::resetState,
                navigateToStoreDetailsScreen = { storeId ->
                    navController.navigate(Screen.StoreDetails.withArgs(storeId))
                },
                navigateToTrackScreen = { orderId ->
                    navController.navigate(Screen.OrderTrack.withArgs(orderId))
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

data class Filter(
    val priceRangeMax: String = Constants.settings.layoutsFiltersDefaultMaxPrice,
    val priceRangeMin: String = Constants.settings.layoutsFiltersDefaultMinPrice,
    val sortBy: String = Constants.settings.layoutsFiltersDefaultSortBy,
    val amountOfDiscount: String = Constants.settings.layoutsFiltersDefaultDiscount
)