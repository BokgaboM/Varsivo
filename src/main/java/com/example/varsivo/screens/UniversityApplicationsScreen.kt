
package com.example.varsivo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.varsivo.api.RetrofitClient
import com.example.varsivo.api.University
import com.example.varsivo.components.NavTab
import com.example.varsivo.components.VarsivoBottomNav
import com.example.varsivo.ui.theme.VarsivoGold
import com.example.varsivo.ui.theme.VarsivoNavy

@Composable
fun UniversityApplicationsScreen(
    onApplyClick: (String) -> Unit,
    onMapClick: (String) -> Unit,
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onApplicationsClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    var searchText by remember { mutableStateOf("") }
    var universities by remember {
        mutableStateOf<List<University>>(emptyList())
    }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }


    // LOAD UNIVERSITIES FROM REST API


    LaunchedEffect(Unit) {

        try {

            universities =
                RetrofitClient.universityApi
                    .getUniversities("South Africa")

            isLoading = false

        } catch (e: Exception) {

            errorMessage =
                "Unable to load universities. Please check your internet connection."

            isLoading = false
        }
    }


    // SEARCH FILTER


    val filteredUniversities =
        universities.filter {
            it.name.contains(
                searchText,
                ignoreCase = true
            )
        }


    // MAIN SCREEN


    Scaffold(
        containerColor = Color(0xFFF5F8FC),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VarsivoNavy)
                    .padding(
                        start = 8.dp,
                        end = 20.dp,
                        top = 18.dp,
                        bottom = 22.dp
                    )
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Find Your University",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Explore universities and start your application journey.",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 48.dp)
                )
            }
        },
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


        // CONTENT


        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(18.dp))


            // SEARCH BAR


            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Search universities")
                },
                placeholder = {
                    Text("e.g. University of Johannesburg")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = VarsivoNavy
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VarsivoNavy,
                    unfocusedBorderColor = Color(0xFF9AA8B5),
                    focusedLabelColor = VarsivoNavy,
                    cursorColor = VarsivoNavy
                )
            )

            Spacer(modifier = Modifier.height(12.dp))


            // MAP BUTTON


            OutlinedButton(
                onClick = {
                    onMapClick(searchText)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = VarsivoNavy
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = "Map"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "VIEW UNIVERSITIES ON MAP",
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            // LOADING

            if (isLoading) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(30.dp))

                    CircularProgressIndicator(
                        color = VarsivoNavy
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Finding universities...",
                        color = Color(0xFF667085),
                        fontSize = 14.sp
                    )
                }


                // ERROR


            } else if (errorMessage.isNotEmpty()) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Something went wrong",
                            color = VarsivoNavy,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = errorMessage,
                            color = Color(0xFF667085),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }


                // UNIVERSITY LIST


            } else {

                Text(
                    text = if (searchText.isBlank()) {
                        "Universities in South Africa"
                    } else {
                        "Search Results"
                    },
                    color = VarsivoNavy,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (filteredUniversities.isEmpty()) {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = VarsivoGold,
                                modifier = Modifier.size(42.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "No universities found",
                                color = VarsivoNavy,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Try searching using a different university name.",
                                color = Color(0xFF667085),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                } else {

                    filteredUniversities.forEach { university ->
                        UniversityCard(
                            university = university,
                            onApplyClick = {
                                onApplyClick(university.name)
                            },
                            onMapClick = {
                                onMapClick(university.name)
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}



// UNIVERSITY CARD


@Composable
private fun UniversityCard(
    university: University,
    onApplyClick: () -> Unit,
    onMapClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // University icon
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = "University",
                    tint = VarsivoGold,
                    modifier = Modifier.size(42.dp)
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = university.name,
                        color = VarsivoNavy,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = university.country,
                        color = Color(0xFF667085),
                        fontSize = 13.sp
                    )
                }

                IconButton(onClick = onMapClick) {
                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = "View ${university.name} on map",
                        tint = VarsivoNavy
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onApplyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VarsivoNavy,
                    contentColor = Color.White
                )
            ) {

                Text(
                    text = "APPLY NOW",
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}

