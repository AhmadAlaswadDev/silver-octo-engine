package com.bestcoders.zaheed.navigation


import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.presentation.screens.home_container.HomeContainerScreen
import com.bestcoders.zaheed.presentation.screens.home_container.HomeContainerViewModel
import com.bestcoders.zaheed.presentation.screens.privacy_policies.PrivacyPoliciesEvent
import com.bestcoders.zaheed.presentation.screens.privacy_policies.PrivacyPoliciesViewModel
import com.bestcoders.zaheed.presentation.screens.privacy_policies.PrivacyPolicyScreen
import com.bestcoders.zaheed.presentation.screens.sign_in.SignInScreen
import com.bestcoders.zaheed.presentation.screens.sign_in.SignInViewModel
import com.bestcoders.zaheed.presentation.screens.signup.SignupScreen
import com.bestcoders.zaheed.presentation.screens.signup.SignupViewModel
import com.bestcoders.zaheed.presentation.screens.verify_code.VerifyCodeScreen
import com.bestcoders.zaheed.presentation.screens.verify_code.VerifyCodeViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    screen: Screen,
    deleteHomeData: MutableState<Boolean>,
) {

    NavHost(
        navController = navController,
        startDestination = screen.route
    ) {

        composable(
            route = Screen.SignIn.route,
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
            val viewModel = hiltViewModel<SignInViewModel>()
            val uiEvent = viewModel.uiEvent
            SignInScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                resetState = viewModel::resetState,
                uiEvent = uiEvent,
                navigateToHomeScreen = {
                    navController.navigate(Screen.HomeContainer.route) {
                        popUpTo(Screen.SignIn.route) {
                            inclusive = true
                        }
                    }
                },
                navigateToVerifyScreenScreen = { phoneNumber, verifyCodeType ->
                    navController.navigate(Screen.VerifyCode.withArgs(phoneNumber, verifyCodeType))
                },
            )
        }

        composable(
            route = Screen.Signup.route + "/{phoneNumber}" + "/{registrationRequestId}",
            arguments = listOf(
                navArgument("phoneNumber") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("registrationRequestId") {
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
            val viewModel = hiltViewModel<SignupViewModel>()
            val uiEvent = viewModel.uiEvent
            SignupScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                navigateBack = { navController.popBackStack() },
                resetState = viewModel::resetState,
                uiEvent = uiEvent,
                phoneNumber = entry.arguments!!.getString("phoneNumber")!!,
                registrationRequestId = entry.arguments!!.getInt("registrationRequestId"),
                navigateToTermsConditionsAndPrivacyScreen = { type ->
                    navController.navigate(Screen.PrivacyPolicy.withArgs(type))
                },
                navigateToHomeScreen = {
                    navController.navigate(Screen.HomeContainer.route) {
                        popUpTo(Screen.SignIn.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable(
                route = Screen.VerifyCode.route + "/{phoneNumber}" + "/{type}",
            arguments = listOf(
                navArgument("phoneNumber") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("type") {
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
            val viewModel = hiltViewModel<VerifyCodeViewModel>()
            val uiEvent = viewModel.uiEvent
            val timer by viewModel.timer.collectAsStateWithLifecycle()
            LaunchedEffect(Lifecycle.Event.ON_CREATE){
                viewModel.resetState(
                    viewModel.state.copy(
                        phoneNumber = entry.arguments?.getString("phoneNumber") ?: "",
                        verifyCodeType = entry.arguments!!.getInt("type"),
                    )
                )
            }
            VerifyCodeScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                resetState = viewModel::resetState,
                uiEvent = uiEvent,
                timer = timer,
                navigateToHomeScreen = {
                    navController.navigate(Screen.HomeContainer.route) {
                        popUpTo(Screen.VerifyCode.route) {
                            inclusive = true
                        }
                    }
                },
                navigateToSignupScreen = { phoneNumber, registrationRequestId ->
                    navController.navigate(Screen.Signup.withArgs(phoneNumber, registrationRequestId))
                },
                navigateToSignInScreen = {
                    navController.navigate(Screen.SignIn.route)
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }


        composable(
            route = Screen.PrivacyPolicy.route + "/{termsType}",
            arguments = listOf(
                navArgument("termsType") {
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
            val viewModel = hiltViewModel<PrivacyPoliciesViewModel>()
            val uiEvent = viewModel.uiEvent
            LaunchedEffect(Lifecycle.Event.ON_CREATE){
                val termsType = entry.arguments!!.getString("termsType")!!
                if (termsType == "0") {
                    viewModel.onEvent(PrivacyPoliciesEvent.GetTermsConditions)
                } else {
                    viewModel.onEvent(PrivacyPoliciesEvent.GetPrivacyPolicies)
                }
            }
            PrivacyPolicyScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                resetState = viewModel::resetState,
                uiEvent = uiEvent,
                onReloadClicked = {
                    val termsType = entry.arguments!!.getString("termsType")!!
                    if (termsType == "0") {
                        viewModel.onEvent(PrivacyPoliciesEvent.GetTermsConditions)
                    } else {
                        viewModel.onEvent(PrivacyPoliciesEvent.GetPrivacyPolicies)
                    }
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.HomeContainer.route
        ) {
            val viewModel = hiltViewModel<HomeContainerViewModel>()
            LaunchedEffect(deleteHomeData.value) {
                if (deleteHomeData.value) {
                    Log.e("AKLFAS", "MainScreen deleteHomeData: ${deleteHomeData.value}")
                    viewModel.state.value =
                        viewModel.state.value.copy(homeLayoutList = mutableStateListOf())
                }
            }
            HomeContainerScreen(
                navController = navController,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent,
                resetState = viewModel::resetState
            )
        }
    }
}