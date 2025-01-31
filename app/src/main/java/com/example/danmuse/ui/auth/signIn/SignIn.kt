package com.example.danmuse.ui.auth.signIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danmuse.components.auth.signIn.SignInComponent
import com.example.mvi.auth.signIn.SignInIntent

@Composable
fun SignIn(
    component: SignInComponent
) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
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
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Войдите в приложение, используя свой аккаунт VK. Это необходимо для доступа к вашим аудиозаписям.",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                )
            }
            TextButton(onClick = {
                component.processIntent(SignInIntent.Login)
            }) {
                Text(
                    text = "Войти",
                    fontSize = 18.sp
                )
            }
        }
    }