package com.bestcoders.zaheed.presentation.screens.home_container

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.navigation.HomeContainerNavGraph
import com.bestcoders.zaheed.navigation.Screen
import com.bestcoders.zaheed.presentation.screens.home_container.components.PrimaryBottomBar
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun HomeContainerScreen(
    navController: NavController,
    state: MutableState<HomeContainerState>,
    onEvent: (HomeContainerEvent) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    resetState: (HomeContainerState) -> Unit,
) {

    val navHostController = rememberNavController()
    val cartItems = remember {
        mutableStateOf(0)
    }
    val currentScreen = rememberSaveable {
        mutableStateOf(Screen.Home.route)
    }
    val context = LocalContext.current

    val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    fun checkLocationPermissionGranted(): Boolean {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fineLocationGranted && coarseLocationGranted
    }

    val permissionGranted = remember { mutableStateOf(checkLocationPermissionGranted()) }

    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        if (fineLocationGranted || coarseLocationGranted) {
            permissionGranted.value = true
        }
    }
    LaunchedEffect(permissionGranted.value) {
        if (permissionGranted.value) {
            Log.e("ASFIASFAOI", "HomeScreen: DONE")
            onEvent(HomeContainerEvent.GetUserLocationWithHomeData)
        }
    }
    fun requestLocationPermission() {
        locationPermissionRequest.launch(permissionsToRequest)
    }



    Scaffold(
        bottomBar = {
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            if (navBackStackEntry?.destination?.route == Screen.Home.route ||
                navBackStackEntry?.destination?.route == Screen.Cart.route ||
                navBackStackEntry?.destination?.route == Screen.Favorite.route ||
                navBackStackEntry?.destination?.route == Screen.Profile.route
            ) {
                PrimaryBottomBar(
                    modifier = Modifier,
                    navController = navHostController,
                    currentScreen = currentScreen,
                    cartItems = cartItems,
                    navigateToSignIn = {
                        navController.navigate(Screen.SignIn.route)
                    }
                )
            }
        },
        content = { padding ->
            HomeContainerNavGraph(
                rootNavController = navController,
                navController = navHostController,
                homePadding = padding,
                homeContainerState = state,
                cartItems = cartItems,
                loadNextItems = { nextPage, filter ->
                    onEvent(
                        HomeContainerEvent.LoadNextItems(
                            nextPage,
                            amountOfDiscount = filter.amountOfDiscount,
                            sortBy = filter.sortBy,
                            priceRangeMax = filter.priceRangeMax,
                            priceRangeMin = filter.priceRangeMin
                        )
                    )
                },
                onReloadClicked = {
                    onEvent(
                        HomeContainerEvent.GetUserLocationWithHomeData
                    )
                },
                getHomeLayoutWithCustomLocation = { latitude, longitude ->
                    resetState(
                        state.value.copy(
                            userLatitude = latitude,
                            userLongitude = longitude
                        )
                    )
                    onEvent(
                        HomeContainerEvent.GetHomeLayoutWithCustomLocation(
                            latitude,
                            longitude
                        )
                    )
                },
                getHomeLayoutWithFilter = { filter ->
                    resetState(
                        state.value.copy(
                            homeLayoutList = mutableStateListOf(),
                            endReached = false
                        )
                    )
                    onEvent(
                        HomeContainerEvent.GetHomeLayoutWithFilter(
                            page = 1,
                            amountOfDiscount = filter.amountOfDiscount,
                            sortBy = filter.sortBy,
                            priceRangeMax = filter.priceRangeMax,
                            priceRangeMin = filter.priceRangeMin
                        )
                    )
                },
                changeBottomBarIndex = { screen ->
                    currentScreen.value = screen
                },
                requestLocationPermission = { requestLocationPermission() },
                permissionGranted = permissionGranted,
            )
        }
    )
}



