package com.bestcoders.zaheed.navigation


sealed class Screen(val route: String) {

    object SignIn : Screen(route = "sign_in")
    object Signup : Screen(route = "signup")
    object VerifyCode : Screen(route = "verify_code")
    object HomeContainer : Screen(route = "home_container")


    // Home Container
    object Home : Screen(route = "home")
    object Cart : Screen(route = "cart_screen")
    object Checkout : Screen(route = "checkout")
    object ConfirmOrder : Screen(route = "confirm_order")
    object Favorite : Screen(route = "favorite_screen")
    object Profile : Screen(route = "profile_screen")
    object ProductDetails : Screen(route = "product_details")
    object StoreDetails : Screen(route = "store_details")
    object PrivacyPolicy : Screen(route = "privacy_policy")
    object Search : Screen(route = "search")
    object Category : Screen(route = "categoryProductDetailsResponse")
    object Subcategory : Screen(route = "subcategory")
    object Filter : Screen(route = "filter")
    object OrderFilter : Screen(route = "order_filter")
    object StoreReturnPolicy : Screen(route = "store_return_policy")
    object Payment : Screen(route = "payment")
    object PaymentSuccess : Screen(route = "payment_success")
    object Map : Screen(route = "map")
    object OrderTrack : Screen(route = "order_track")
    object OrderDetails : Screen(route = "order_details")
    object EditProfile : Screen(route = "edit_profile")
    object UpdatePassword : Screen(route = "update_password")
    object OrderHistory : Screen(route = "order_history")
    object AboutUs : Screen(route = "about_us")
    object FAQ : Screen(route = "faq")




    fun withArgs(vararg args: Any?): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}