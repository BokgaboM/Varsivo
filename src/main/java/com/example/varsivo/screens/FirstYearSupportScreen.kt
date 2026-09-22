package com.example.varsivo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.varsivo.ui.theme.VarsivoBackground
import com.example.varsivo.ui.theme.VarsivoGold
import com.example.varsivo.ui.theme.VarsivoNavy
import com.example.varsivo.ui.theme.VarsivoOfferBg
import com.example.varsivo.ui.theme.VarsivoSubmittedBg

private data class TimetableEntry(val time: String, val subject: String)
private data class Deadline(val title: String, val daysLeft: Int)

// TODO(backend team): replace these placeholders with data pulled from the
// student's real timetable / assignment endpoints once available.
private val mockTimetable = listOf(
    TimetableEntry("09:00", "Data Structures"),
    TimetableEntry("14:00", "Software Eng")
)

private val mockDeadlines = listOf(
    Deadline("Assignment 2", 2),
    Deadline("Maths test", 5),
    Deadline("Assignment 3", 10)
)

@Composable
fun FirstYearSupportScreen(
    onBackClick: () -> Unit,
    isVerified: Boolean = true, // TODO(backend): drive this from the real student-email verification status
    onHomeClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onApplicationsClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VarsivoBackground)
    ) {

        VarsivoHeader(
            title = "UniAssist",
            onBackClick = onBackClick,
            trailingContent = {
                if (isVerified) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = VarsivoOfferBg
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(shape = CircleShape, color = Color(0xFF7FB335)) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Verified", color = VarsivoNavy, fontSize = 14.sp)
                        }
                    }
                }
            }
        )

        if (!isVerified) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = VarsivoGold,
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Verify your student email to unlock UniAssist",
                    color = VarsivoNavy,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "UniAssist gives you your timetable, deadlines and exam countdowns once you're enrolled.",
                    color = Color(0xFF667085),
                    fontSize = 13.sp
                )
            }
        } else {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                // Real UniAssist illustration
                Image(
                    painter = painterResource(id = R.drawable.uniassist_illustration),
                    contentDescription = "UniAssist illustration",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // TODAY'S TIMETABLE — with the bell notification badge from the wireframe
                Box {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = VarsivoNavy)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = "Today's timetable",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            mockTimetable.forEach { entry ->
                                Text(
                                    text = "${entry.time} ${entry.subject}",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 19.sp,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Surface(
                        shape = CircleShape,
                        color = VarsivoGold,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 4.dp)
                            .offset(y = (-10).dp)
                            .size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = "Notifications",
                                tint = VarsivoNavy,
                                modifier = Modifier.padding(7.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Upcoming deadlines",
                    color = VarsivoNavy,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Single gold card with dividers, matching the Figma export
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = VarsivoGold)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                        mockDeadlines.forEachIndexed { index, deadline ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = deadline.title,
                                    color = Color.White,
                                    fontSize = 17.sp
                                )
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = VarsivoSubmittedBg
                                ) {
                                    Text(
                                        text = "${deadline.daysLeft} days",
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                        color = VarsivoNavy,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                            if (index != mockDeadlines.lastIndex) {
                                Divider(color = Color.White.copy(alpha = 0.5f), thickness = 1.dp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        VarsivoBottomNav(
            selectedTab = NavTab.PROFILE,
            onHomeClick = onHomeClick,
            onSearchClick = onSearchClick,
            onAddClick = onAddClick,
            onApplicationsClick = onApplicationsClick,
            onProfileClick = { /* already viewing account-tied content */ }
        )
    }
}
