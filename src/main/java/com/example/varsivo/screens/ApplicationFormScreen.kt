package com.example.varsivo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.varsivo.ui.theme.VarsivoGold
import com.example.varsivo.ui.theme.VarsivoNavy

@Composable
fun ApplicationFormScreen(
    universityName: String,
    onBackClick: () -> Unit
) {

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F8FC))
    ) {

        // Header
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
                    text = "University Application",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Complete your application details below.",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 13.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Selected university
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "University",
                        tint = VarsivoGold,
                        modifier = Modifier.size(42.dp)
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {

                        Text(
                            text = "Applying to",
                            color = Color(0xFF667085),
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = universityName,
                            color = VarsivoNavy,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Form
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
                        .padding(20.dp)
                ) {

                    Text(
                        text = "Your Details",
                        color = VarsivoNavy,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Enter your information to continue.",
                        color = Color(0xFF667085),
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Full Name
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = {
                            fullName = it
                            message = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Full Name")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Full Name",
                                tint = VarsivoNavy
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = VarsivoNavy,
                            unfocusedBorderColor = Color(0xFF9AA8B5),
                            focusedLabelColor = VarsivoNavy,
                            cursorColor = VarsivoNavy
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            message = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Email Address")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                tint = VarsivoNavy
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = VarsivoNavy,
                            unfocusedBorderColor = Color(0xFF9AA8B5),
                            focusedLabelColor = VarsivoNavy,
                            cursorColor = VarsivoNavy
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Course
                    OutlinedTextField(
                        value = course,
                        onValueChange = {
                            course = it
                            message = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Course / Programme")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = "Course",
                                tint = VarsivoNavy
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = VarsivoNavy,
                            unfocusedBorderColor = Color(0xFF9AA8B5),
                            focusedLabelColor = VarsivoNavy,
                            cursorColor = VarsivoNavy
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Message
                    if (message.isNotEmpty()) {

                        Text(
                            text = message,
                            color = if (message.contains("successfully")) {
                                Color(0xFF1F6F54)
                            } else {
                                MaterialTheme.colorScheme.error
                            },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Submit
                    Button(
                        onClick = {

                            if (
                                fullName.isBlank() ||
                                email.isBlank() ||
                                course.isBlank()
                            ) {

                                message = "Please complete all fields."

                            } else {

                                message =
                                    "Application submitted successfully!"
                            }
                        },
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
                            text = "SUBMIT APPLICATION",
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Your information will be used to prepare your university application.",
                color = Color(0xFF667085),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}