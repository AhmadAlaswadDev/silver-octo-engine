package com.bestcoders.zaheed.presentation.screens.home_container.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.navigation.Screen
import com.bestcoders.zaheed.ui.theme.AppTheme

data class BottomBarItem(
    val label: Int,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val screen: Screen,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryBottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentScreen: MutableState<String>,
    navigateToSignIn: () -> Unit,
    cartItems: MutableState<Int>,
) {

    val dimens = AppTheme.dimens

    val items = remember {
        listOf(
            BottomBarItem(
                label = R.string.home,
                selectedIcon = R.drawable.home_selected_icon,
                unselectedIcon = R.drawable.home_unselected_icon,
                screen = Screen.Home
            ),
            BottomBarItem(
                label = R.string.cart,
                selectedIcon = R.drawable.cart_selected_icon,
                unselectedIcon = R.drawable.cart_unselected_icon,
                screen = Screen.Cart
            ),
            BottomBarItem(
                label = R.string.favorite,
                selectedIcon = R.drawable.favorite_selected_icon,
                unselectedIcon = R.drawable.border_favorite_icon,
                screen = Screen.Favorite
            ),
            BottomBarItem(
                label = R.string.profile,
                selectedIcon = R.drawable.user_selected_icon,
                unselectedIcon = R.drawable.user_unselected_icon,
                screen = Screen.Profile
            )
        )
    }

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                shadowElevation = 50f
            }
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)),
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            NavigationBarItem(
                modifier = Modifier.weight(1f),
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Red,
                    selectedTextColor = Color.Red,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                    unselectedIconColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                    indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        LocalAbsoluteTonalElevation.current
                    ),
                ),
                icon = {
                    if (currentScreen.value == item.screen.route) {
                        if (currentScreen.value == Screen.Cart.route) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        modifier = Modifier
                                            .size(AppTheme.dimens.large)
                                            .clip(shape = CircleShape)
                                            .align(Alignment.Center),
                                    ) {
                                        Text(
                                            cartItems.value.toString(),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.SemiBold,
                                            ),
                                        )
                                    }
                                },
                            ) {
                                Icon(
                                    modifier = Modifier.size(AppTheme.dimens.navigationBarItemIconSize),
                                    painter = painterResource(id = item.selectedIcon),
                                    contentDescription = "",
                                )
                            }
                        } else {
                            Icon(
                                modifier = Modifier.size(AppTheme.dimens.navigationBarItemIconSize),
                                painter = painterResource(id = item.selectedIcon),
                                contentDescription = "",
                            )
                        }
                    } else {
                        Icon(
                            modifier = Modifier.size(dimens.navigationBarItemIconSize),
                            painter = painterResource(id = item.unselectedIcon),
                            contentDescription = "",
                        )
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = item.label),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                        ),
                    )
                },
                selected = currentScreen.value == item.screen.route,
                onClick = {
                    if (item.screen.route == Screen.Favorite.route && Constants.userToken.isEmpty()) {
                        navigateToSignIn()
                        return@NavigationBarItem
                    } else if (currentScreen.value != item.screen.route) {
                        currentScreen.value = item.screen.route
                        navController.navigate(item.screen.route)
                    }
                }
            )
        }
    }
}
