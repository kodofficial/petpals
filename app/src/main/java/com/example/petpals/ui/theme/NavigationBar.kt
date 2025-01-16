package com.example.petpals.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.petpals.PetPalsScreens

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val screens = listOf(
        NavItem(PetPalsScreens.Home, Icons.Filled.Home),
        NavItem(PetPalsScreens.Adopt, Icons.Filled.Search),
        NavItem(PetPalsScreens.Post, Icons.Filled.Add),
        NavItem(PetPalsScreens.Profile, Icons.Filled.Person)
    )

    NavigationBar(
        modifier = modifier,
        containerColor = Color.White
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = backStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.screen.name, tint = Color.Black) },
                label = { Text(screen.screen.name, color = Color.Black) },
                selected = currentRoute == screen.screen.name,
                onClick = {
                    navController.navigate(screen.screen.name) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

data class NavItem(val screen: PetPalsScreens, val icon: ImageVector)

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    val navController = rememberNavController()
    BottomNavBar(navController = navController)
}