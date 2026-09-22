package com.example.varsivo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.varsivo.ui.theme.VarsivoNavy

/**
 * The five destinations shown in the Figma bottom nav (home / search / + / book / profile).
 *
 * NOTE: the center "+" icon isn't clearly specced beyond its look (white
 * rounded-square FAB). Mapped here to the Applications tracker (closest
 * thing to a "quick add" action) — confirm with whoever did the Figma if it
 * should go elsewhere (e.g. a "log a new application" sheet).
 */
enum class NavTab {
    HOME, SEARCH, ADD, APPLICATIONS, PROFILE
}

@Composable
fun VarsivoBottomNav(
    selectedTab: NavTab,
    onHomeClick: () -> Unit,
    onSearchClick: () -> Unit,
    onAddClick: () -> Unit,
    onApplicationsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        color = VarsivoNavy,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavIcon(Icons.Default.Home, "Home", onHomeClick)
            NavIcon(Icons.Default.Search, "Search", onSearchClick)

            // Center "+" — white rounded-square FAB, matching the wireframe
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                modifier = Modifier
                    .size(52.dp)
                    .clickable(onClick = onAddClick)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(52.dp)) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Quick action",
                        tint = VarsivoNavy
                    )
                }
            }

            NavIcon(Icons.Default.MenuBook, "Applications", onApplicationsClick)
            NavIcon(Icons.Default.Person, "Profile", onProfileClick)
        }
    }
}

@Composable
private fun NavIcon(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(26.dp)
        )
    }
}
