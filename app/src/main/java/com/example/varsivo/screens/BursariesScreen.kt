package com.example.varsivo.screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.varsivo.R
import com.example.varsivo.api.Bursary
import com.example.varsivo.api.RetrofitClient
import com.example.varsivo.components.NavTab
import com.example.varsivo.components.VarsivoBottomNav
import com.example.varsivo.components.VarsivoHeader
import com.example.varsivo.components.VarsivoSearchBar
import com.example.varsivo.ui.theme.VarsivoBackground
import com.example.varsivo.ui.theme.VarsivoNavy
import kotlinx.coroutines.launch

// Card background tints sampled from the Figma export — gold for the
// closing-soon item, alternating light blue for the rest.
private val goldCardTint = Color(0xFFECC986)
private val blueCardTint = Color(0xFFA8BFDE)

// The three bursaries shown by default, matching the wireframe exactly.
// Anything the real API returns shows up under "View more" instead of
// replacing these, so the screen always matches the design at a glance.
private val wireframeBursaries = listOf(
    Bursary(id = "wf1", name = "Sasol Bursary", closingDate = "2 Days left"),
    Bursary(id = "wf2", name = "NSFAS", closingDate = "31 May"),
    Bursary(id = "wf3", name = "Deloitte", closingDate = "30 June")
)

@Composable
fun BursariesScreen(
    onBursaryClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onApplicationsClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    var searchText by remember { mutableStateOf("") }
    var apiBursaries by remember { mutableStateOf<List<Bursary>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    var showAll by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                apiBursaries = RetrofitClient.bursaryApi.getBursaries()
                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
                isLoading = false
            }
        }
    }

    // Once the person starts typing, search the FULL pool (wireframe + api)
    // even if "View more" hasn't been tapped yet — otherwise a search for
    // something only in the API list would show nothing and look broken.
    val isSearching = searchText.isNotBlank()
    val basePool = if (showAll || isSearching) wireframeBursaries + apiBursaries else wireframeBursaries

    val filteredBursaries = basePool.filter {
        it.name.contains(searchText, ignoreCase = true)
    }

    Scaffold(
        containerColor = VarsivoBackground,
        topBar = { VarsivoHeader(title = "Bursaries", onBackClick = onBackClick) },
        bottomBar = {
            VarsivoBottomNav(
                selectedTab = NavTab.SEARCH,
                onHomeClick = onHomeClick,
                onSearchClick = onSearchClick,
                onAddClick = onAddClick,
                onApplicationsClick = onApplicationsClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(4.dp))
            VarsivoSearchBar(value = searchText, onValueChange = { searchText = it })
            Spacer(modifier = Modifier.height(20.dp))

            when {
                isLoading && (showAll || isSearching) -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = VarsivoNavy)
                    }
                }

                errorMessage.isNotEmpty() && (showAll || isSearching) -> {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                filteredBursaries.isEmpty() -> {
                    Text(
                        text = "No bursaries found for \"$searchText\".",
                        color = Color(0xFF667085),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    filteredBursaries.forEachIndexed { index, bursary ->
                        BursaryTintedCard(
                            bursaryName = bursary.name,
                            closingDate = bursary.closingDate,
                            isClosingSoon = index == 0,
                            tint = if (index == 0) goldCardTint else blueCardTint,
                            onClick = { onBursaryClick(bursary.name) }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    if (!isSearching && apiBursaries.isNotEmpty()) {
                        OutlinedButton(
                            onClick = { showAll = !showAll },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = VarsivoNavy)
                        ) {
                            Text(
                                if (showAll) "View less" else "View more bursaries",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun BursaryTintedCard(
    bursaryName: String,
    closingDate: String,
    isClosingSoon: Boolean,
    tint: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Real graduation photo, tinted to match the wireframe's colored overlay
            Image(
                painter = painterResource(id = R.drawable.bursaries_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(tint.copy(alpha = 0.72f))
            )

            // Content laid out in a Column (not absolute-positioned) so long
            // bursary names push layout down instead of overlapping the date.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = closingDate.ifBlank { "TBC" },
                    color = if (isClosingSoon) VarsivoNavy else Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.End)
                )

                Column {
                    Text(
                        text = if (isClosingSoon) "Closing soon" else "Closes",
                        color = if (isClosingSoon) VarsivoNavy else Color.White,
                        fontSize = 15.sp
                    )
                    Text(
                        text = bursaryName,
                        color = VarsivoNavy,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2
                    )
                }
            }
        }
    }
}
