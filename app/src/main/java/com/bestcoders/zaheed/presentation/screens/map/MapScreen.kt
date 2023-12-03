package com.bestcoders.zaheed.presentation.screens.map


import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.presentation.components.PrimaryButton
import com.bestcoders.zaheed.presentation.components.SpacerHeightMedium
import com.bestcoders.zaheed.presentation.components.SpacerHeightSmall
import com.bestcoders.zaheed.presentation.components.SpacerWidthMedium
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun MapScreen(
    state: MutableState<MapState>,
    onBackClick: (Double, Double) -> Boolean
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val markerPosition = remember {
        mutableStateOf(
            LatLng(
                state.value.userLatitude,
                state.value.userLongitude
            )
        )
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerPosition.value, 8f)
    }


    var locationDetails by remember { mutableStateOf<Address?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            coroutineScope.launch {
                locationDetails = getLocationDetails(
                    cameraPositionState.position.target.latitude,
                    cameraPositionState.position.target.longitude,
                    context
                )
            }
            return@LaunchedEffect
        }
    }

    val mapProperties = MapProperties(isMyLocationEnabled = true)

    Scaffold { padding ->
       if(state.value.success){
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .zIndex(5f)
           ) {
               Column(
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(65.dp)
                       .background(MaterialTheme.colorScheme.background),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {
                   Box(
                       modifier = Modifier
                           .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
                           .height(5.dp)
                           .width(40.dp)
                           .clip(RoundedCornerShape(100.dp)),
                   )
                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Center,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           modifier = Modifier.fillMaxWidth(0.9f),
                           text = stringResource(R.string.choose_location),
                           style = MaterialTheme.typography.headlineSmall.copy(
                               color = MaterialTheme.colorScheme.primary,
                               fontWeight = FontWeight.Bold,
                               textAlign = TextAlign.Center,
                           )
                       )
                       IconButton(
                           modifier = Modifier.fillMaxWidth(0.5f),
                           colors = IconButtonDefaults.filledIconButtonColors(
                               containerColor = Color.Transparent,
                           ),
                           onClick = {
                               onBackClick(
                                   cameraPositionState.position.target.latitude,
                                   cameraPositionState.position.target.longitude
                               )
                           }) {
                           Icon(
                               tint = MaterialTheme.colorScheme.secondaryContainer,
                               modifier = Modifier
                                   .size(20.dp),
                               painter = painterResource(id = R.drawable.exit_icon),
                               contentDescription = "",
                           )
                       }
                   }
               }
               Box(
                   modifier = Modifier
                       .fillMaxHeight(0.72f)
                       .fillMaxWidth(),
               ) {
                   GoogleMap(
                       modifier = Modifier.fillMaxSize(),
                       cameraPositionState = cameraPositionState,
                       properties = mapProperties,
                       onMapLoaded = {
                           if(state.value.success){
                               markerPosition.value = LatLng(state.value.userLatitude, state.value.userLongitude)
                               cameraPositionState.move(CameraUpdateFactory.newLatLng(markerPosition.value))
                           }
                       }
                   )
                   Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                       Icon(
                           modifier = Modifier.size(25.dp),
                           painter = painterResource(id = R.drawable.marker_icon),
                           contentDescription = null,
                           tint = MaterialTheme.colorScheme.primaryContainer
                       )
                   }
               }
               Column(
                   modifier = Modifier
                       .fillMaxHeight()
                       .fillMaxWidth()
                       .background(CustomColor.White)
                       .padding(horizontal = AppTheme.dimens.paddingHorizontal)
                       .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
                   horizontalAlignment = Alignment.Start,
                   verticalArrangement = Arrangement.Top
               ) {
                   SpacerHeightMedium()
                   Row(
                       modifier = Modifier
                           .wrapContentHeight()
                           .fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start,
                       verticalAlignment = Alignment.Top
                   ) {
                       Icon(
                           modifier = Modifier
                               .wrapContentHeight()
                               .size(30.dp),
                           painter = painterResource(id = R.drawable.location_icon),
                           tint = MaterialTheme.colorScheme.onTertiary,
                           contentDescription = "",
                       )
                       SpacerWidthMedium()
                       Column(
                           modifier = Modifier.wrapContentHeight(),
                           verticalArrangement = Arrangement.Top,
                           horizontalAlignment = Alignment.Start
                       ) {
                           Text(
                               text = locationDetails?.locality
                                   ?: stringResource(R.string.you_are_in_unsupported_location),
                               maxLines = 1,
                               minLines = 1,
                               style = MaterialTheme.typography.bodyLarge.copy(
                                   color = MaterialTheme.colorScheme.primary,
                                   fontWeight = FontWeight.SemiBold,
                                   textAlign = TextAlign.Start,
                               )
                           )
                           Text(
                               text = locationDetails?.getAddressLine(0) ?: "",
                               maxLines = 2,
                               minLines = 2,
                               style = MaterialTheme.typography.bodyMedium.copy(
                                   color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                                   fontWeight = FontWeight.Normal,
                                   textAlign = TextAlign.Start,
                               )
                           )
                       }
                   }
                   SpacerHeightSmall()
                   PrimaryButton(
                       modifier = Modifier.fillMaxWidth(),
                       text = stringResource(R.string.change_location),
                       onClick = {
                           onBackClick(
                               cameraPositionState.position.target.latitude,
                               cameraPositionState.position.target.longitude
                           )
                       },
                       iconSize = AppTheme.dimens.topBarIconStoreDetailsSize,
                   )
                   SpacerHeightMedium()
               }
           }
       }
    }
}

private fun getLocationDetails(latitude: Double, longitude: Double, context: Context): Address? {
    return try {
        val addresses: List<Address>?
        val geocoder = Geocoder(context, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            latitude,
            longitude,
            1,
        )
        val address = addresses.isNullOrEmpty()
        if (!address) addresses!![0] else null
    } catch (e: Exception) {
        Log.e(Constants.EXCEPTION_TAG, "${e.message.toString()}")
        null
    }
}
