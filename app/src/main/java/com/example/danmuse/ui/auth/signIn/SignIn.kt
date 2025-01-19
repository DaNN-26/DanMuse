package com.example.danmuse.ui.auth.signIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danmuse.components.auth.signIn.SignInComponent
import com.example.mvi.auth.signIn.SignInIntent

@Composable
fun SignIn(
    component: SignInComponent
) {
        Column(
            verticalArrangement = Arrangement
                .spacedBy(space = 22.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "DanMuse",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Авторизация",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Light
                )
            }
            Button(onClick = {
                component.processIntent(SignInIntent.Login)
            }) {
                Text(text = "Войти")
            }
        }
    }