package com.example.petpals.pages


import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 40.dp, top = 170.dp, bottom = 100.dp, end = 40.dp ),
        shape = MaterialTheme.shapes.extraLarge,
        shadowElevation = 100.dp
    ) {
        Column(
            modifier = modifier
                .wrapContentSize(Alignment.Center)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable._fulllogo),
                contentDescription = null,
                modifier = modifier.size(200.dp)

            )
            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "Email icon"
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(text = stringResource(R.string.email))
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )

            //Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = "Password icon"
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(text = stringResource(R.string.password))
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {authViewModel.login(email, password) }) {
                Text(text = stringResource(R.string.login))
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = {navController.navigate(PetPalsScreens.SignUp.name) }) {
                Text(
                    text = "Don't have an account, Sign up",
                    style = MaterialTheme.typography.titleSmall,
                    textDecoration = TextDecoration.Underline
                )
            }
            LaunchedEffect(authState.value) {
                when(authState.value){
                    is AuthState.Authenticated -> navController.navigate(PetPalsScreens.Home.name)
                    else -> Unit
                }
            }
        }
    }
}
@Preview
@Composable
fun LoginPagePreview() {


}