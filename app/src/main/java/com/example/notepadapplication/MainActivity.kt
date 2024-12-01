package com.example.notepadapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.notepadapplication.navigation.NavigationItem
import com.example.notepadapplication.screens.HomeScreenLayout
import com.example.notepadapplication.screens.SavedScreenLayout
import com.example.notepadapplication.ui.theme.NotePadApplicationTheme
import com.example.notepadapplication.viewmodel.NotepadViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val notepadViewModel:NotepadViewModel by viewModels ()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotePadApplicationTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopAppBar(title = { Text("NotePad App") }) },
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavApp(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    @Composable
    private fun NavApp(navController: NavHostController,modifier: Modifier){
        NavHost(
            navController = navController,
            startDestination = NavigationItem.HomeScreen.route,
            modifier = modifier
        ){
            composable(NavigationItem.HomeScreen.route) {
                HomeScreenLayout(notepadViewModel =notepadViewModel )
            }
            composable(NavigationItem.SavedScreen.route) {
                SavedScreenLayout(notepadViewModel)
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        val items = listOf(
            NavigationItem.HomeScreen,
            NavigationItem.SavedScreen,

            )

        NavigationBar {
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry?.destination?.route

            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = when (item) {
                                NavigationItem.HomeScreen -> Icons.Filled.Home
                                NavigationItem.SavedScreen -> Icons.Filled.Favorite
                            },
                            contentDescription = item.route.toString()
                        )
                    },
                    label = { Text(text = item.route) },
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                restoreState = true
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }
    }
}
