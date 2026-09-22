package com.example.varsivo.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.varsivo.R
import com.example.varsivo.components.NavTab
import com.example.varsivo.components.VarsivoBottomNav
import com.example.varsivo.components.VarsivoSearchBar
import com.example.varsivo.ui.theme.VarsivoBackground
import com.example.varsivo.ui.theme.VarsivoGold
import com.example.varsivo.ui.theme.VarsivoNavy

@Composable
fun HomeScreen(
    onUniversityApplicationsClick: () -> Unit,
    onBursariesClick: () -> Unit,
    onFirstYearSupportClick: () -> Unit,
    onExploreClick: () -> Unit,
    onAddClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    val context = LocalContext.current

    val sharedPreferences = remember {
        context.getSharedPreferences("VarsivoUser", Context.MODE_PRIVATE)
    }

    val userName = sharedPreferences
        .getString("name", "")
        ?.substringBefore(" ")
        ?.ifBlank { "Student" }
        ?: "Student"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VarsivoBackground)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Hi $userName!",
                color = VarsivoNavy,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Good Morning",
                color = Color(0xFF667085),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            var homeSearchQuery by remember { mutableStateOf("") }
            VarsivoSearchBar(
                value = homeSearchQuery,
                onValueChange = { homeSearchQuery = it },
                onSearchSubmit = onSearchClick
            )

            Spacer(modifier = Modifier.height(20.dp))

            // "Most urgent" banner — illustration overlaps the bottom-right, matching the wireframe
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .align(Alignment.TopStart),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = VarsivoNavy)
                ) {
                    Column(modifier = Modifier.padding(22.dp)) {
                        Text(
                            text = "Most urgent",
                            color = Color.White,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "NFSAS closing soon",
                            color = Color.White,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "3 Days left",
                            color = Color.White,
                            fontSize = 15.sp
                        )
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.study_dashboard),
                    contentDescription = "Student illustration",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .height(210.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 2x2 action grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                HomeActionCard(
                    label = "Applications",
                    containerColor = VarsivoNavy,
                    textColor = Color.White,
                    modifier = Modifier.weight(1f),
                    onClick = onUniversityApplicationsClick
                )
                HomeActionCard(
                    label = "Bursaries",
                    containerColor = VarsivoNavy,
                    textColor = Color.White,
                    modifier = Modifier.weight(1f),
                    onClick = onBursariesClick
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                HomeActionCard(
                    label = "Explore",
                    containerColor = VarsivoNavy,
                    textColor = Color.White,
                    modifier = Modifier.weight(1f),
                    onClick = onExploreClick
                )
                HomeActionCard(
                    label = "UniAssist",
                    containerColor = VarsivoGold,
                    textColor = Color.White,
                    modifier = Modifier.weight(1f),
                    onClick = onFirstYearSupportClick
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        VarsivoBottomNav(
            selectedTab = NavTab.HOME,
            onHomeClick = { /* already here */ },
            onSearchClick = onSearchClick,
            onAddClick = onAddClick,
            onApplicationsClick = onUniversityApplicationsClick,
            onProfileClick = onProfileClick
        )
    }
}

@Composable
private fun HomeActionCard(
    label: String,
    containerColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = label,
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
