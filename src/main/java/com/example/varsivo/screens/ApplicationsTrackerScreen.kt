package com.example.varsivo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.varsivo.R
import com.example.varsivo.components.NavTab
import com.example.varsivo.components.VarsivoBottomNav
import com.example.varsivo.components.VarsivoHeader
import com.example.varsivo.components.VarsivoSearchBar
import com.example.varsivo.ui.theme.VarsivoBackground
import com.example.varsivo.ui.theme.VarsivoNavy
import com.example.varsivo.ui.theme.VarsivoOfferBg
import com.example.varsivo.ui.theme.VarsivoPendingBg
import com.example.varsivo.ui.theme.VarsivoSubmittedBg

enum class ApplicationStatus(val label: String, val bg: Color, val fg: Color) {
    OFFER("Offer", VarsivoOfferBg, VarsivoNavy),
    PENDING("Pending", VarsivoPendingBg, Color.White),
    SUBMITTED("Submitted", VarsivoSubmittedBg, VarsivoNavy)
}

data class TrackedApplication(
    val institution: String,
    val programme: String,
    val status: ApplicationStatus,
    val synced: Boolean
)

// TODO(backend team): swap this local list for GET/POST /applications once the
// endpoint exists. Keeping the shape (institution/programme/status/synced)
// the same means the UI below doesn't need to change when you wire it up —
// `synced = false` is what should show "Offline, will sync..." per the
// offline-first requirement from the design doc.
private val mockApplications = listOf(
    TrackedApplication("UCT", "BScIT", ApplicationStatus.OFFER, synced = true),
    TrackedApplication("Rosebank College", "Diploma", ApplicationStatus.PENDING, synced = false),
    TrackedApplication("NSFAS", "Funding", ApplicationStatus.SUBMITTED, synced = true)
)

@Composable
fun ApplicationsTrackerScreen(
    onBackClick: () -> Unit,
    onBrowseUniversitiesClick: () -> Unit,
    onHomeClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    var searchText by remember { mutableStateOf("") }

    val filteredApplications = mockApplications.filter {
        it.institution.contains(searchText, ignoreCase = true) ||
            it.programme.contains(searchText, ignoreCase = true)
    }

    Scaffold(
        containerColor = VarsivoBackground,
        topBar = { VarsivoHeader(title = "Applications", onBackClick = onBackClick) },
        bottomBar = {
            VarsivoBottomNav(
                selectedTab = NavTab.APPLICATIONS,
                onHomeClick = onHomeClick,
                onSearchClick = onSearchClick,
                onAddClick = onBrowseUniversitiesClick,
                onApplicationsClick = { /* already here */ },
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {

            Spacer(modifier = Modifier.height(4.dp))
            VarsivoSearchBar(value = searchText, onValueChange = { searchText = it })

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.applications_illustration),
                contentDescription = "Applications illustration",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onBrowseUniversitiesClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = VarsivoNavy)
            ) {
                Icon(imageVector = Icons.Default.School, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Browse universities to apply", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredApplications.isEmpty()) {
                Text(
                    text = "No applications found for \"$searchText\".",
                    color = Color(0xFF667085),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            filteredApplications.forEach { app ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = VarsivoNavy),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${app.institution} - ${app.programme}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 19.sp
                            )
                            Text(
                                text = if (app.synced) "Synced" else "Offline will sync...",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 13.sp
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = app.status.bg
                        ) {
                            Text(
                                text = app.status.label,
                                modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
                                color = app.status.fg,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
