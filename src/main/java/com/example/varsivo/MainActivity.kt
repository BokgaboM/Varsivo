
package com.example.varsivo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.varsivo.screens.ApplicationFormScreen
import com.example.varsivo.screens.ApplicationsTrackerScreen
import com.example.varsivo.screens.BursariesScreen
import com.example.varsivo.screens.BursaryApplicationScreen
import com.example.varsivo.screens.BursaryDetailsScreen
import com.example.varsivo.screens.FirstYearSupportScreen
import com.example.varsivo.screens.HomeScreen
import com.example.varsivo.screens.LoginScreen
import com.example.varsivo.screens.RegisterScreen
import com.example.varsivo.screens.SettingsScreen
import com.example.varsivo.screens.SplashScreen
import com.example.varsivo.screens.UniversityApplicationsScreen
import com.example.varsivo.screens.UniversityMapScreen
import com.example.varsivo.screens.CareerCourseExplorerScreen
import com.example.varsivo.ui.theme.VarsivoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            VarsivoTheme {

                var currentScreen by remember {
                    mutableStateOf("splash")
                }

                var selectedUniversity by remember {
                    mutableStateOf("")
                }

                var selectedBursary by remember {
                    mutableStateOf("")
                }

                var mapUniversityName by remember {
                    mutableStateOf("")
                }

                when (currentScreen) {


                    // SPLASH


                    "splash" -> {

                        SplashScreen(
                            onSplashFinished = {
                                currentScreen = "login"
                            }
                        )
                    }


                    // LOGIN


                    "login" -> {

                        LoginScreen(
                            onRegisterClick = {
                                currentScreen = "register"
                            },

                            onLoginSuccess = {
                                currentScreen = "home"
                            }
                        )
                    }


                    // REGISTER


                    "register" -> {

                        RegisterScreen(
                            onBackToLogin = {
                                currentScreen = "login"
                            }
                        )
                    }


                    // HOME


                    "home" -> HomeScreen(
                        onUniversityApplicationsClick = { currentScreen = "applicationsTracker" },
                        onBursariesClick = { currentScreen = "bursaries" },
                        onFirstYearSupportClick = { currentScreen = "firstYearSupport" },
                        onExploreClick = { currentScreen = "careerCourseExplorer" },
                        onAddClick = { currentScreen = "universities" },
                        onSearchClick = { currentScreen = "bursaries" },
                        onProfileClick = { currentScreen = "settings" }
                    )

                    "careerCourseExplorer" -> CareerCourseExplorerScreen(
                        onBackClick = { currentScreen = "home" },
                        onHomeClick = { currentScreen = "home" },
                        onSearchClick = { currentScreen = "bursaries" },
                        onApplicationsClick = { currentScreen = "applicationsTracker" },
                        onAddClick = { currentScreen = "universities" },
                        onProfileClick = { currentScreen = "settings" }
                    )


                    // SETTINGS


                    "settings" -> SettingsScreen(
                        onBackClick = { currentScreen = "home" },
                        onLogoutClick = { currentScreen = "splash" },
                        onHomeClick = { currentScreen = "home" },
                        onSearchClick = { currentScreen = "bursaries" },
                        onApplicationsClick = { currentScreen = "applicationsTracker" },
                        onAddClick = { currentScreen = "universities" }
                    )


                    // APPLICATIONS TRACKER (bottom-nav "Applications" destination)


                    "applicationsTracker" -> ApplicationsTrackerScreen(
                        onBackClick = { currentScreen = "home" },
                        onBrowseUniversitiesClick = { currentScreen = "universities" },
                        onHomeClick = { currentScreen = "home" },
                        onSearchClick = { currentScreen = "bursaries" },
                        onProfileClick = { currentScreen = "settings" }
                    )


                    // UNIVERSITIES (browse/search, reached from Applications tracker)


                    "universities" -> {

                        UniversityApplicationsScreen(

                            onApplyClick = { universityName ->

                                selectedUniversity = universityName

                                currentScreen = "applicationForm"
                            },

                            onMapClick = { universityName ->

                                mapUniversityName = universityName
                                currentScreen = "universityMap"
                            },

                            onBackClick = { currentScreen = "applicationsTracker" },
                            onHomeClick = { currentScreen = "home" },
                            onSearchClick = { currentScreen = "bursaries" },
                            onApplicationsClick = { currentScreen = "applicationsTracker" },
                            onAddClick = { /* already on the add-application screen */ },
                            onProfileClick = { currentScreen = "settings" }
                        )
                    }


                    // UNIVERSITY APPLICATION FORM

                    "applicationForm" -> {

                        ApplicationFormScreen(

                            universityName = selectedUniversity,

                            onBackClick = {

                                currentScreen = "universities"
                            }
                        )
                    }


                    // UNIVERSITY MAP


                    "universityMap" -> {

                        UniversityMapScreen(
                            universityName = mapUniversityName,
                            onBackClick = {
                                currentScreen = "universities"
                            }
                        )
                    }


                    // BURSARIES


                    "bursaries" -> {

                        BursariesScreen(

                            onBursaryClick = { bursaryName ->

                                selectedBursary = bursaryName

                                currentScreen = "bursaryDetails"
                            },

                            onBackClick = {

                                currentScreen = "home"
                            },

                            onHomeClick = { currentScreen = "home" },
                            onSearchClick = { /* already here */ },
                            onApplicationsClick = { currentScreen = "applicationsTracker" },
                            onAddClick = { currentScreen = "universities" },
                            onProfileClick = { currentScreen = "settings" }
                        )
                    }


                    // BURSARY DETAILS


                    "bursaryDetails" -> {

                        BursaryDetailsScreen(

                            bursaryName = selectedBursary,

                            onBackClick = {

                                currentScreen = "bursaries"
                            },

                            onApplyClick = {

                                currentScreen = "bursaryApplication"
                            }
                        )
                    }


                    // BURSARY APPLICATION


                    "bursaryApplication" -> {

                        BursaryApplicationScreen(

                            bursaryName = selectedBursary,

                            onBackClick = {

                                currentScreen = "bursaryDetails"
                            }
                        )
                    }


                    // UNI-ASSIST / FIRST-YEAR SUPPORT


                    "firstYearSupport" -> {

                        FirstYearSupportScreen(
                            onBackClick = {
                                currentScreen = "home"
                            },
                            onHomeClick = { currentScreen = "home" },
                            onSearchClick = { currentScreen = "bursaries" },
                            onApplicationsClick = { currentScreen = "applicationsTracker" },
                            onAddClick = { currentScreen = "universities" },
                            onProfileClick = { currentScreen = "settings" }
                        )
                    }
                }
            }
        }
    }
}

