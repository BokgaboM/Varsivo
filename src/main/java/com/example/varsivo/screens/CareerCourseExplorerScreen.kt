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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.varsivo.R
import com.example.varsivo.components.NavTab
import com.example.varsivo.components.VarsivoBottomNav
import com.example.varsivo.components.VarsivoHeader
import com.example.varsivo.ui.theme.VarsivoBackground
import com.example.varsivo.ui.theme.VarsivoNavy
import kotlin.math.roundToInt

/**
 * NSC subject % -> APS points, per the standard 7-point admission scale used
 * by South African universities. Backend/data team: swap this out for a
 * database-driven mapping if institutions need custom scales later — for
 * the prototype this covers the common case.
 */
private fun markToApsPoint(mark: Int): Int = when {
    mark >= 80 -> 7
    mark >= 70 -> 6
    mark >= 60 -> 5
    mark >= 50 -> 4
    mark >= 40 -> 3
    mark >= 30 -> 2
    else -> 1
}

private data class MatchedInstitution(val name: String, val minAps: Int)

private val institutionPool = listOf(
    MatchedInstitution("University of Pretoria", 30),
    MatchedInstitution("Wits University", 32),
    MatchedInstitution("University of Johannesburg", 26),
    MatchedInstitution("Stellenbosch University", 28),
    MatchedInstitution("UNISA", 22)
)

private val popularCareers = listOf(
    "Software Developer",
    "Computer Science",
    "Economics",
    "Mechanical Engineering",
    "Nursing"
)

private const val MAX_SUBJECTS = 6
private const val INITIAL_SUBJECTS = 3
private const val INITIAL_INSTITUTIONS_SHOWN = 2
private const val INITIAL_CAREERS_SHOWN = 3

@Composable
fun CareerCourseExplorerScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onApplicationsClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    // Six subject marks — the standard NSC APS calculation (best 6 of 7 subjects).
    // Only INITIAL_SUBJECTS fields show at first; "Add subject" reveals more,
    // up to MAX_SUBJECTS, so the calculator card starts short and easy to reach.
    var subjectMarks by remember { mutableStateOf(List(MAX_SUBJECTS) { "" }) }
    var visibleSubjectCount by remember { mutableStateOf(INITIAL_SUBJECTS) }
    var subjectsUsedForScore by remember { mutableStateOf(MAX_SUBJECTS) }

    var apsScore by remember { mutableStateOf<Int?>(null) }
    var showCalculator by remember { mutableStateOf(false) }
    var calculatorMessage by remember { mutableStateOf("") }

    var showAllInstitutions by remember { mutableStateOf(false) }
    var showAllCareers by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = VarsivoBackground,
        topBar = { VarsivoHeader(title = "Explore", onBackClick = onBackClick) },
        bottomBar = {
            VarsivoBottomNav(
                selectedTab = NavTab.SEARCH,
                onHomeClick = onHomeClick,
                onSearchClick = { /* already here */ },
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
                .padding(20.dp)
        ) {

            // APS SCORE CARD — with the real illustration from the wireframe
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFFF9A03C), Color(0xFFB26187))
                            )
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.explore_illustration),
                        contentDescription = "Student illustration",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxHeight()
                            .width(160.dp)
                    )

                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "APS score",
                            color = Color.White,
                            fontSize = 13.sp
                        )
                        Text(
                            text = if (apsScore != null) "Your score: $apsScore" else "Check your score",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (apsScore != null && subjectsUsedForScore < MAX_SUBJECTS) {
                            Text(
                                text = "(estimated from $subjectsUsedForScore subjects)",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = { showCalculator = !showCalculator },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = VarsivoNavy
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = if (showCalculator) "Hide Calculator" else "Calculate APS",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // EXPANDABLE CALCULATOR INPUTS
            if (showCalculator) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {

                        Text(
                            text = "Enter your best subject marks (%)",
                            color = VarsivoNavy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "$visibleSubjectCount of $MAX_SUBJECTS added — more subjects gives a more accurate score",
                            color = Color(0xFF667085),
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        for (index in 0 until visibleSubjectCount) {
                            OutlinedTextField(
                                value = subjectMarks[index],
                                onValueChange = { newValue ->
                                    if (newValue.length <= 3 && newValue.all { it.isDigit() }) {
                                        subjectMarks = subjectMarks.toMutableList().also {
                                            it[index] = newValue
                                        }
                                    }
                                },
                                label = { Text("Subject ${index + 1} mark") },
                                singleLine = true,
                                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )
                        }

                        if (visibleSubjectCount < MAX_SUBJECTS) {
                            OutlinedButton(
                                onClick = {
                                    visibleSubjectCount = (visibleSubjectCount + 1).coerceAtMost(MAX_SUBJECTS)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = VarsivoNavy)
                            ) {
                                Text("+ Add subject")
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        if (calculatorMessage.isNotEmpty()) {
                            Text(
                                text = calculatorMessage,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        Button(
                            onClick = {
                                val enteredMarks = subjectMarks
                                    .take(visibleSubjectCount)
                                    .mapNotNull { it.toIntOrNull() }

                                if (enteredMarks.size < visibleSubjectCount) {
                                    calculatorMessage = "Please fill in all $visibleSubjectCount subject marks."
                                } else {
                                    // Scored on the same 0-42 (7x6) scale universities
                                    // use, even if fewer than 6 subjects were entered —
                                    // averaging and scaling to 6 keeps "Match" / "Below
                                    // min" comparisons fair regardless of how many
                                    // subjects the person bothered to add.
                                    val averagePoints = enteredMarks.map { markToApsPoint(it) }.average()
                                    apsScore = (averagePoints * MAX_SUBJECTS).roundToInt()
                                    subjectsUsedForScore = visibleSubjectCount
                                    calculatorMessage = ""
                                    showCalculator = false
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = VarsivoNavy,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Calculate", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // MATCHED INSTITUTIONS
            Text(
                text = "Matched Institution",
                color = VarsivoNavy,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            val institutionsToShow = if (showAllInstitutions) institutionPool else institutionPool.take(INITIAL_INSTITUTIONS_SHOWN)

            institutionsToShow.forEach { institution ->
                val eligible = apsScore != null && apsScore!! >= institution.minAps

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, VarsivoNavy),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = institution.name,
                                color = VarsivoNavy,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "Min APS: ${institution.minAps}",
                                color = Color(0xFF667085),
                                fontSize = 12.sp
                            )
                        }

                        if (apsScore != null) {
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = if (eligible) Color(0xFF4CAF50) else Color(0xFFE57373)
                            ) {
                                Text(
                                    text = if (eligible) "Match" else "Below min",
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (institutionPool.size > INITIAL_INSTITUTIONS_SHOWN) {
                OutlinedButton(
                    onClick = { showAllInstitutions = !showAllInstitutions },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = VarsivoNavy)
                ) {
                    Text(
                        if (showAllInstitutions) "View less" else "View more institutions",
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // POPULAR CAREERS
            Text(
                text = "Popular Careers",
                color = VarsivoNavy,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            val careersToShow = if (showAllCareers) popularCareers else popularCareers.take(INITIAL_CAREERS_SHOWN)

            careersToShow.forEach { career ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, VarsivoNavy),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text = career,
                        modifier = Modifier.padding(16.dp),
                        color = VarsivoNavy,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (popularCareers.size > INITIAL_CAREERS_SHOWN) {
                OutlinedButton(
                    onClick = { showAllCareers = !showAllCareers },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = VarsivoNavy)
                ) {
                    Text(
                        if (showAllCareers) "View less" else "View more careers",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
