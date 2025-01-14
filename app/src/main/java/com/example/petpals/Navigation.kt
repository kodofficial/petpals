package com.example.petpals


import com.example.petpals.pages.LoginPage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petpals.pages.HomePage
import com.example.petpals.pages.SingUpPage
import com.example.petpals.pages.AdoptPage
import com.example.petpals.pages.ProfilePage




enum class PetPalsScreens {
      Login,
      SignUp,
      Home,
      Adopt,
      Profile,
      Search,
      Post
   }

@Composable
fun Navigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, petViewModel: PetViewModel) {
   val navController: NavHostController = rememberNavController()
   NavHost(
      navController = navController,
      startDestination = PetPalsScreens.Login.name,
   ){
      composable(PetPalsScreens.Login.name) {
         LoginPage(modifier, navController, authViewModel)
      }
      composable(PetPalsScreens.SignUp.name) {
         SingUpPage(modifier, navController,authViewModel)
      }
      composable(PetPalsScreens.Home.name) {
          HomePage(modifier,petViewModel,navController,authViewModel)
      }
//       composable(PetPalsScreens.Profile.name) {
//           ProfilePage(modifier, navController, authViewModel)
//       }
//       composable(PetPalsScreens.Adopt.name) {
//           AdoptPage(modifier,navController,authViewModel)
//       }
   }
}