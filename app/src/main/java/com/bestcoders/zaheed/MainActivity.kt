package com.bestcoders.zaheed

import android.app.LocaleManager
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.LocalConnectionState
import com.bestcoders.zaheed.core.util.NetworkConnectionAwareContent
import com.bestcoders.zaheed.core.util.NoInternetDialog
import com.bestcoders.zaheed.data.local.data_store.SettingsDataStore
import com.bestcoders.zaheed.navigation.Screen
import com.bestcoders.zaheed.navigation.SetupNavGraph
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.ZaheedTheme
import com.bestcoders.zaheed.ui.theme.rememberWindowSizeClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Variables
            val viewModel = hiltViewModel<MainViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val window = rememberWindowSizeClass()
            var isSplashLoading by remember { mutableStateOf(true) }
            val settingsDataStore = SettingsDataStore(LocalContext.current)


            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                settingsDataStore.getAppLanguage.collectLatest { appLanguage ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (appLanguage == Constants.ARABIC_LANGUAGE_CODE || appLanguage == Constants.SAUDI_LANGUAGE_CODE) {
                            applicationContext.getSystemService(LocaleManager::class.java).applicationLocales =
                                LocaleList.forLanguageTags(Constants.ARABIC_LANGUAGE_CODE)
                            Constants.DEFAULT_LANGUAGE = Constants.SAUDI_LANGUAGE_CODE
                        } else {
                            applicationContext.getSystemService(LocaleManager::class.java).applicationLocales =
                                LocaleList.forLanguageTags(Constants.ENGLISH_LANGUAGE_CODE)
                            Constants.DEFAULT_LANGUAGE = Constants.ENGLISH_LANGUAGE_CODE
                        }
                    } else {
                        val locale = Locale(appLanguage)
                        Locale.setDefault(locale)
                        val resources = applicationContext.resources
                        val config: Configuration = resources.configuration
                        config.setLocale(locale)
                        resources.updateConfiguration(config, resources.displayMetrics)
                        if (appLanguage == Constants.ARABIC_LANGUAGE_CODE || appLanguage == Constants.SAUDI_LANGUAGE_CODE) {
                            Constants.DEFAULT_LANGUAGE = Constants.SAUDI_LANGUAGE_CODE
                        } else {
                            Constants.DEFAULT_LANGUAGE = Constants.ENGLISH_LANGUAGE_CODE
                        }
                    }
                }
            }

            LaunchedEffect(Lifecycle.Event.ON_CREATE) {
                viewModel.getUserToken()
                viewModel.getSettings()
                viewModel.checkUserLoggedIn()

            }


            LaunchedEffect(state.settings) {
                if (state.settings != null) {
                    Constants.settings = state.settings!!
                } else {
                    viewModel.getSettings()
                }
            }

            LaunchedEffect(key1 = true) {
                delay(3000)
                isSplashLoading = false
            }

            // Theme
            ZaheedTheme(window) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        installSplashScreen().apply {
                            setKeepOnScreenCondition {
                                state.isLoading || isSplashLoading
                            }
                        }
                    } else {
                        if (state.isLoading || isSplashLoading) {
                            Image(
                                modifier = Modifier.padding(AppTheme.dimens.large),
                                painter = painterResource(id = R.drawable.logo_splash),
                                contentDescription = getString(R.string.logo_splash)
                            )
                        }
                    }

                    if (!state.isLoading && !isSplashLoading && state.settings != null) {
                        MainScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun MainScreen() {
        val context = LocalContext.current
        val locale = context.resources.configuration.locales[0]
        val language = locale.language
        val direction =
            if (language == Constants.ARABIC_LANGUAGE_CODE || language == Constants.SAUDI_LANGUAGE_CODE) {
                Constants.DEFAULT_LANGUAGE = Constants.SAUDI_LANGUAGE_CODE
                LayoutDirection.Rtl
            } else {
                Constants.DEFAULT_LANGUAGE = Constants.ENGLISH_LANGUAGE_CODE
                LayoutDirection.Ltr
            }
        val viewModel = hiltViewModel<MainViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val deleteHomeData = remember {
            mutableStateOf(false)
        }

        OnLifecycleEvent { owner, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    deleteHomeData.value = true
                }

                Lifecycle.Event.ON_START -> {
                    deleteHomeData.value = false
                }

                else -> {}
            }
        }

        CompositionLocalProvider(LocalLayoutDirection provides direction) {
            val navController = rememberNavController()
            NetworkConnectionAwareContent {
                val connectionState by remember {
                    derivedStateOf { LocalConnectionState }
                }
                if (!connectionState.current) {
                    NoInternetDialog()
                } else {
                    SetupNavGraph(
                        navController = navController,
                        deleteHomeData = deleteHomeData,
                        screen = if (state.isUserLoggedIn) {
                            Screen.HomeContainer
                        } else {
                            Screen.SignIn
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}
