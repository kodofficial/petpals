package com.example.petpals.pages


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petpals.AuthState
import com.example.petpals.AuthViewModel
import com.example.petpals.PetPalsScreens
import com.example.petpals.R
import com.example.petpals.ui.theme.PetPalsTheme


@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()

    Column(
        modifier = modifier
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(75.dp))
        Image(
            painter = painterResource(R.drawable._fulllogo),
            contentDescription = null,
            modifier = modifier.size(200.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = stringResource(R.string.email),
                        color = Color.Gray, // Gray color for the placeholder text
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = stringResource(R.string.password),
                        color = Color.Gray, // Gray color for the placeholder text
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {authViewModel.login(email, password)},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.login))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.titleSmall
            )
            TextButton(
                onClick = { navController.navigate(PetPalsScreens.SignUp.name) }
            ) {
                Text(
                    text = "Sign up",
                    style = MaterialTheme.typography.titleSmall,
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        LaunchedEffect(authState.value) {
            when(authState.value){
                is AuthState.Authenticated -> navController.navigate(PetPalsScreens.Home.name)
                else -> Unit
            }
        }
    }
}

@Preview
@Composable
fun LoginPagePreview() {
    PetPalsTheme {
        val navController = rememberNavController()
        val authViewModel: AuthViewModel = viewModel()

        LoginPage(
            navController = navController,
            authViewModel = authViewModel
        )
    }
}