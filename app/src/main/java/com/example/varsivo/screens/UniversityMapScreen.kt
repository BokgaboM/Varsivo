package com.example.varsivo.screens

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.varsivo.api.RetrofitClient
import com.example.varsivo.ui.theme.VarsivoNavy
import kotlinx.coroutines.launch
import org.maplibre.android.MapLibre
import org.maplibre.android.annotations.MarkerOptions
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView

// Fallback if geocoding fails or no university name was given — central
// South Africa, so the map still shows something sensible.
private val defaultLocation = LatLng(-28.4793, 24.6727)

@Composable
fun UniversityMapScreen(
    universityName: String = "",
    onBackClick: () -> Unit
) {

    var isSearching by remember { mutableStateOf(universityName.isNotBlank()) }
    var resolvedLocation by remember { mutableStateOf<LatLng?>(null) }
    var resolvedLabel by remember { mutableStateOf(universityName) }
    var searchError by remember { mutableStateOf("") }
    var mapRef by remember { mutableStateOf<MapLibreMap?>(null) }

    val scope = rememberCoroutineScope()

    // Geocode the specific university's real location via Nominatim (OpenStreetMap).
    LaunchedEffect(universityName) {
        if (universityName.isBlank()) {
            isSearching = false
            return@LaunchedEffect
        }
        scope.launch {
            try {
                val results = RetrofitClient.geocodingApi.search(query = universityName)
                if (results.isNotEmpty()) {
                    val result = results.first()
                    resolvedLocation = LatLng(result.lat.toDouble(), result.lon.toDouble())
                    resolvedLabel = result.display_name
                } else {
                    searchError = "Couldn't find \"$universityName\" on the map — showing South Africa instead."
                }
            } catch (e: Exception) {
                searchError = "Couldn't reach the map service — showing South Africa instead."
            } finally {
                isSearching = false
            }
        }
    }

    // Once we resolve (or fail to resolve) a location, move the camera and drop a marker.
    LaunchedEffect(resolvedLocation, mapRef) {
        val map = mapRef ?: return@LaunchedEffect
        val target = resolvedLocation ?: defaultLocation
        map.cameraPosition = CameraPosition.Builder()
            .target(target)
            .zoom(if (resolvedLocation != null) 14.0 else 5.0)
            .build()
        map.clear()
        map.addMarker(
            MarkerOptions()
                .position(target)
                .title(resolvedLabel.ifBlank { "South Africa" })
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F8FC))
    ) {

        // HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(VarsivoNavy)
                .padding(start = 8.dp, end = 20.dp, top = 18.dp, bottom = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Column {
                Text(
                    text = "University Map",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (universityName.isNotBlank()) "Locating $universityName..." else "Explore university locations.",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 13.sp
                )
            }
        }

        if (isSearching) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(color = VarsivoNavy, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Finding $universityName...", color = VarsivoNavy, fontSize = 14.sp)
                }
            }
        } else if (searchError.isNotEmpty()) {
            Text(
                text = searchError,
                color = Color(0xFF667085),
                fontSize = 13.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        // MAP
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            factory = { context ->
                MapLibre.getInstance(context)

                MapView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    onCreate(null)

                    getMapAsync { map ->
                        map.setStyle("https://demotiles.maplibre.org/style.json")
                        mapRef = map
                    }
                }
            }
        )
    }
}
