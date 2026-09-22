
package com.example.varsivo.screens
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.varsivo.R
import com.example.varsivo.api.LoginRequest
import com.example.varsivo.api.RetrofitClient
import com.example.varsivo.ui.theme.VarsivoBackground
import com.example.varsivo.ui.theme.VarsivoNavy
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var studentId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var selectedGrade by remember { mutableStateOf("Grade 12") }
    var selectedLanguage by remember { mutableStateOf("English") }

    var showForgotPasswordDialog by remember { mutableStateOf(false) }
    var showGoogleDialog by remember { mutableStateOf(false) }
    var forgotPasswordInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VarsivoBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        // Varsivo logo
        Image(
            painter = painterResource(id = R.drawable.varsivo_logo),
            contentDescription = "Varsivo logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 90.dp)
        )

        Spacer(modifier = Modifier.height(36.dp))

        OutlinedTextField(
            value = studentId,
            onValueChange = {
                studentId = it
                message = ""
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Email") },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = VarsivoNavy,
                unfocusedBorderColor = Color(0xFF9AA8B5)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                message = ""
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = VarsivoNavy,
                unfocusedBorderColor = Color(0xFF9AA8B5)
            )
        )

        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // LOGIN BUTTON
        Button(
            onClick = {
                if (studentId.isBlank() || password.isBlank()) {
                    message = "Please enter your Student ID and password."
                } else {
                    coroutineScope.launch {
                        try {
                            val response = RetrofitClient.varsivoApi.login(
                                LoginRequest(
                                    email = studentId,
                                    password = password
                                )
                            )

                            if (response.isSuccessful && response.body() != null) {

                                val authResponse = response.body()!!

                                // Save token and UID
                                val prefs = context.getSharedPreferences(
                                    "varsivo_auth",
                                    Context.MODE_PRIVATE
                                )

                                prefs.edit()
                                    .putString("token", authResponse.token)
                                    .putString("uid", authResponse.user.uid)
                                    .apply()

                                message = ""

                                onLoginSuccess()

                            } else {
                                message = "Incorrect Student ID or password."
                            }

                        } catch (e: Exception) {
                            message = "Unable to connect to the server."
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VarsivoNavy,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Sign In",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Forgot Password?",
            color = VarsivoNavy,
            fontSize = 14.sp,
            modifier = Modifier.clickableText {
                forgotPasswordInput = studentId
                showForgotPasswordDialog = true
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Don't you have an account? ",
                color = Color(0xFF444444),
                fontSize = 14.sp
            )

            Text(
                text = "Sign up",
                color = VarsivoNavy,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickableText(onRegisterClick)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // GOOGLE BUTTON
        OutlinedButton(
            onClick = { showGoogleDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = VarsivoNavy
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_logo_real),
                contentDescription = "Google logo",
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Continue with Google",
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Grade",
                color = Color(0xFF667085),
                fontSize = 15.sp
            )

            Surface(
                shape = RoundedCornerShape(50),
                color = Color(0xFFFAEEDA)
            ) {
                Text(
                    text = selectedGrade,
                    modifier = Modifier.padding(
                        horizontal = 18.dp,
                        vertical = 10.dp
                    ),
                    color = VarsivoNavy,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Language",
                color = Color(0xFF667085),
                fontSize = 15.sp
            )

            Text(
                text = selectedLanguage,
                color = VarsivoNavy,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }

    // FORGOT PASSWORD
    if (showForgotPasswordDialog) {
        AlertDialog(
            onDismissRequest = {
                showForgotPasswordDialog = false
            },
            title = {
                Text("Reset your password")
            },
            text = {
                Column {
                    Text(
                        "Enter your Student ID and we'll send reset instructions."
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = forgotPasswordInput,
                        onValueChange = {
                            forgotPasswordInput = it
                        },
                        placeholder = {
                            Text("Student ID")
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showForgotPasswordDialog = false
                    }
                ) {
                    Text(
                        "Send reset link",
                        color = VarsivoNavy
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showForgotPasswordDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    // GOOGLE SIGN-IN DEMO
    if (showGoogleDialog) {
        AlertDialog(
            onDismissRequest = {
                showGoogleDialog = false
            },
            title = {
                Text("Continue with Google")
            },
            text = {
                Text(
                    "This is a demo. Real Google Sign-In needs backend OAuth setup — for now, continuing will sign you in as a demo student."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showGoogleDialog = false
                        onLoginSuccess()
                    }
                ) {
                    Text(
                        "Continue",
                        color = VarsivoNavy
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showGoogleDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

private fun Modifier.clickableText(
    onClick: () -> Unit
): Modifier =
    this.clickable(onClick = onClick)

