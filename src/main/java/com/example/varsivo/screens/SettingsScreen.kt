package com.example.varsivo.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.varsivo.components.NavTab
import com.example.varsivo.components.VarsivoBottomNav
import com.example.varsivo.ui.theme.VarsivoGold
import com.example.varsivo.ui.theme.VarsivoNavy
import androidx.compose.runtime.rememberCoroutineScope
import com.example.varsivo.api.RetrofitClient
import com.example.varsivo.api.SettingsRequest
import kotlinx.coroutines.launch

// Matches the languages listed in Section B's requirements table.
// NOTE: this screen only *stores* the chosen language for now — actual
// string translation needs values-zu / values-af resource files, which is
// its own follow-up task once we know who's doing the isiZulu/Afrikaans copy.
private val supportedLanguages = listOf("English", "isiZulu", "Afrikaans")

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onHomeClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onApplicationsClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val prefs = remember {
        context.getSharedPreferences("VarsivoUser", Context.MODE_PRIVATE)
    }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf(prefs.getString("name", "") ?: "") }
    var email by remember { mutableStateOf(prefs.getString("email", "") ?: "") }
    var selectedLanguage by remember {
        mutableStateOf(prefs.getString("language", "English") ?: "English")
    }
    var languageMenuExpanded by remember { mutableStateOf(false) }
    var notificationsEnabled by remember {
        mutableStateOf(prefs.getBoolean("notificationsEnabled", true))
    }
    var savedMessage by remember { mutableStateOf("") }

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
                .padding(start = 8.dp, end = 20.dp, top = 18.dp, bottom = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Settings",
                color = Color.White,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            // PROFILE
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Profile",
                        color = VarsivoNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Full name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LANGUAGE
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Language",
                        color = VarsivoNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box {
                        OutlinedButton(
                            onClick = { languageMenuExpanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = VarsivoNavy)
                        ) {
                            Text(selectedLanguage, modifier = Modifier.weight(1f))
                            Icon(Icons.Default.ExpandMore, contentDescription = null)
                        }

                        DropdownMenu(
                            expanded = languageMenuExpanded,
                            onDismissRequest = { languageMenuExpanded = false }
                        ) {
                            supportedLanguages.forEach { lang ->
                                DropdownMenuItem(
                                    text = { Text(lang) },
                                    onClick = {
                                        selectedLanguage = lang
                                        languageMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // NOTIFICATIONS
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Deadline & bursary notifications",
                            color = VarsivoNavy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Get alerts for closing dates and reminders.",
                            color = Color(0xFF667085),
                            fontSize = 12.sp
                        )
                    }
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = VarsivoNavy)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (savedMessage.isNotEmpty()) {
                Text(
                    text = savedMessage,
                    color = Color(0xFF1F6F54),
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    prefs.edit()
                        .putString("name", name)
                        .putString("email", email)
                        .putString("language", selectedLanguage)
                        .putBoolean("notificationsEnabled", notificationsEnabled)
                        .apply()

                    val authPrefs = context.getSharedPreferences("varsivo_auth", Context.MODE_PRIVATE)
                    val token = authPrefs.getString("token", null)
                    val uid = authPrefs.getString("uid", null)

                    if (token != null && uid != null) {
                        scope.launch {
                            try {
                                RetrofitClient.varsivoApi.updateSettings(
                                    uid,
                                    "Bearer $token",
                                    SettingsRequest(
                                        preferredLanguage = selectedLanguage,
                                        notificationsEnabled = notificationsEnabled
                                    )
                                )
                                savedMessage = "Settings saved."
                            } catch (e: Exception) {
                                savedMessage = "Saved locally (offline)."
                            }
                        }
                    } else {
                        savedMessage = "Settings saved."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VarsivoNavy,
                    contentColor = Color.White
                )
            ) {
                Text("SAVE CHANGES", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFBA1A1A)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBA1A1A))
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("LOG OUT", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        VarsivoBottomNav(
            selectedTab = NavTab.PROFILE,
            onHomeClick = onHomeClick,
            onSearchClick = onSearchClick,
            onAddClick = onAddClick,
            onApplicationsClick = onApplicationsClick,
            onProfileClick = { /* already here */ }
        )
    }
}
