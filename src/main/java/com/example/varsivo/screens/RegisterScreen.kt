package com.example.varsivo.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.varsivo.SecurityUtils
import com.example.varsivo.ui.theme.VarsivoBackground
import com.example.varsivo.ui.theme.VarsivoNavy
import androidx.compose.runtime.rememberCoroutineScope
import com.example.varsivo.api.RegisterRequest
import com.example.varsivo.api.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onBackToLogin: () -> Unit
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var schoolName by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var agreedToTerms by remember { mutableStateOf(true) }
    var message by remember { mutableStateOf("") }

    // The wireframe's Sign Up screen never collects an email — only Student
    // ID — so Student ID is the account identifier used for login. We keep
    // storing it under the "email" SharedPreferences key so LoginScreen's
    // lookup logic doesn't need a separate code path; the key name is just
    // legacy, the *value* stored is the student ID.
    val loginIdentifier = studentId

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.White,
        unfocusedBorderColor = Color.White.copy(alpha = 0.6f),
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        cursorColor = Color.White,
        focusedPlaceholderColor = Color.White.copy(alpha = 0.6f),
        unfocusedPlaceholderColor = Color.White.copy(alpha = 0.6f)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VarsivoBackground)
    ) {

        // Plain header above the navy card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 24.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.Surface(
                shape = CircleShape,
                color = Color.Transparent,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF17212B)),
                modifier = Modifier
                    .size(36.dp)
                    .clickable(onClick = onBackToLogin)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Login",
                    tint = Color(0xFF17212B),
                    modifier = Modifier.padding(7.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Login", color = Color(0xFF17212B), fontSize = 20.sp)
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
            colors = CardDefaults.cardColors(containerColor = VarsivoNavy)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 28.dp, vertical = 32.dp)
            ) {

                Text("Full Name", color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it; message = "" },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(50),
                    colors = fieldColors
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text("School Name", color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = schoolName,
                    onValueChange = { schoolName = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(50),
                    colors = fieldColors
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text("Student ID", color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = studentId,
                    onValueChange = { studentId = it; message = "" },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(50),
                    colors = fieldColors
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text("Password", color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; message = "" },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(50),
                    colors = fieldColors
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text("Confirm Password", color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it; message = "" },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(50),
                    colors = fieldColors
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = agreedToTerms,
                        onCheckedChange = { agreedToTerms = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.White,
                            checkmarkColor = VarsivoNavy,
                            uncheckedColor = Color.White
                        )
                    )
                    Text("I agree to the terms and conditions", color = Color.White, fontSize = 14.sp)
                }

                if (message.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = message,
                        color = if (message.contains("successful")) Color(0xFFB6E3A1) else Color(0xFFFFB4AB),
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        when {
                            name.isBlank() || studentId.isBlank() || password.isBlank() -> {
                                message = "Please complete all required fields."
                            }
                            password != confirmPassword -> {
                                message = "Passwords do not match."
                            }
                            !agreedToTerms -> {
                                message = "Please agree to the terms and conditions."
                            }
                            else -> {
                                coroutineScope.launch {
                                    try {
                                        val response = RetrofitClient.varsivoApi.register(
                                            RegisterRequest(
                                                email = studentId,
                                                password = password,
                                                fullName = name,
                                                schoolName = schoolName,
                                                studentId = studentId
                                            )
                                        )

                                        if (response.isSuccessful && response.body() != null) {
                                            val authResponse = response.body()!!

                                            val prefs = context.getSharedPreferences("varsivo_auth", Context.MODE_PRIVATE)
                                            prefs.edit()
                                                .putString("token", authResponse.token)
                                                .putString("uid", authResponse.user.uid)
                                                .apply()

                                            message = "Registration successful!"
                                            onBackToLogin()

                                        } else {
                                            message = "Registration failed. Try a different Student ID."
                                        }

                                    } catch (e: Exception) {
                                        message = "Unable to connect to the server."
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = VarsivoNavy
                    )
                ) {
                    Text(text = "Create Account", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
