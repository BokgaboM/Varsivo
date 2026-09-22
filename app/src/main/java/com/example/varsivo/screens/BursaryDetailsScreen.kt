
package com.example.varsivo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.varsivo.ui.theme.VarsivoGold
import com.example.varsivo.ui.theme.VarsivoNavy

@Composable
fun BursaryDetailsScreen(
    bursaryName: String,
    onBackClick: () -> Unit,
    onApplyClick: () -> Unit
) {

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
                .padding(
                    start = 8.dp,
                    end = 20.dp,
                    top = 18.dp,
                    bottom = 18.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Column {

                Text(
                    text = "Bursary Details",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Learn more about this funding opportunity.",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 13.sp
                )
            }
        }


        // CONTENT


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            // Bursary title card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Default.AccountBalance,
                        contentDescription = "Bursary",
                        tint = VarsivoGold,
                        modifier = Modifier.size(55.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = bursaryName,
                        color = VarsivoNavy,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Funding Opportunity",
                        color = Color(0xFF667085),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Information card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
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
                        .padding(20.dp)
                ) {

                    Text(
                        text = "About This Bursary",
                        color = VarsivoNavy,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "This bursary provides financial assistance to eligible students to support their studies.",
                        color = Color(0xFF667085),
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Requirements",
                        color = VarsivoNavy,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RequirementItem(
                        text = "South African citizenship"
                    )

                    RequirementItem(
                        text = "Academic results"
                    )

                    RequirementItem(
                        text = "Proof of household income"
                    )

                    RequirementItem(
                        text = "Acceptance or registration at an institution"
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Status
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Open",
                            tint = Color(0xFF1F6F54),
                            modifier = Modifier.size(22.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Application Status: Open",
                            color = Color(0xFF1F6F54),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            // APPLY BUTTON


            Button(
                onClick = onApplyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VarsivoNavy,
                    contentColor = Color.White
                )
            ) {

                Text(
                    text = "APPLY FOR THIS BURSARY",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
private fun RequirementItem(
    text: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = VarsivoGold,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = text,
            color = Color(0xFF17212B),
            fontSize = 14.sp
        )
    }
}

