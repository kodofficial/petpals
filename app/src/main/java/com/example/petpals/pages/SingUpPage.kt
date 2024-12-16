package com.example.petpals.pages

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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petpals.AuthViewModel
import com.example.petpals.PetPalsScreens
import com.example.petpals.R

@Composable
fun SingUpPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 40.dp, top = 130.dp, bottom = 55.dp, end = 40.dp),
        shape = MaterialTheme.shapes.extraLarge,
        shadowElevation = 100.dp
    ) {
        Column(
            modifier = modifier
                .wrapContentSize(Alignment.Center)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
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
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "First Name icon"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = stringResource(R.string.first_name))
                        }
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                )


                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.AccountBox,
                                contentDescription = "Last Name icon"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = stringResource(R.string.last_name))
                        }
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                )

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Username icon"
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(text = stringResource(R.string.username))
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )


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

            Button(onClick = {authViewModel.signup(email,password, firstName, lastName, userName) }) {
                Text(text = stringResource(R.string.sign_up))
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = {navController.navigate(PetPalsScreens.Login.name)}) {
                Text(
                    text = "Already have an account, Login",
                    style = MaterialTheme.typography.titleSmall,
                    textDecoration = TextDecoration.Underline
                )
                }
            }
        }
    }







