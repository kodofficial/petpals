package com.example.petpals


import com.example.petpals.pages.LoginPage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petpals.pages.AdoptPage
import com.example.petpals.pages.HomePage
import com.example.petpals.pages.PostScreen
import com.example.petpals.pages.ProfilePage
import com.example.petpals.pages.SingUpPage


enum class PetPalsScreens {
      Login,
      SignUp,
      Home,
      Adopt,
      Profile,
      Post
   }

@Composable
fun Navigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, petViewModel: PetViewModel) {
   val navController: NavHostController = rememberNavController()
    val currentUser by authViewModel.currentUser.observeAsState()
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
       composable(PetPalsScreens.Profile.name) {
           ProfilePage(currentUser, navController)
       }
       composable(PetPalsScreens.Adopt.name) {
           AdoptPage(modifier,petViewModel,navController,authViewModel)
       }

       composable(PetPalsScreens.Post.name) {
          PostScreen (petViewModel,navController)
       }

   }
}