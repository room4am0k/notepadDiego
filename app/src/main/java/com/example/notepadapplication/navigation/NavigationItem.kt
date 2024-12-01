package com.example.notepadapplication.navigation

sealed class NavigationItem (val route:String){
data object HomeScreen:NavigationItem(Screens.Home.name)
    data object SavedScreen:NavigationItem(Screens.SavedNotes.name)
}