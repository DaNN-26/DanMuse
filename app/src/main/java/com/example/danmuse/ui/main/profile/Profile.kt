package com.example.danmuse.ui.main.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.danmuse.components.main.profile.ProfileComponent
import com.example.mvi.main.profile.ProfileIntent

@Composable
fun Profile(
    component: ProfileComponent,
    modifier: Modifier
) {
    val state by component.state.subscribeAsState()
    val vkStoreState by component.vkStoreState.collectAsState()
    
    LaunchedEffect(vkStoreState.songsList.size) {
        component.processIntent(ProfileIntent.InitializeProfile)
    }

    Surface(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if(state.isLoading)
                CircularProgressIndicator()
            else {
                AsyncImage(
                    model = state.profileImage,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(120.dp)
                        .shadow(3.dp, CircleShape)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = state.profileName,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp
                )
                Text(
                    text = "Количество аудиозаписей: ${state.songsCount}",
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                SignOutButton(
                    signOut = { component.processIntent(ProfileIntent.SignOut) }
                )
            }
        }
    }
}

@Composable
fun SignOutButton(
    signOut: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Exit from account",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(32.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = (-70).dp, y = 0.dp)
        ) {
            Text(
                text = "Вы уверены что хотите\nвыйти из аккаунта?",
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(
                    horizontal = 14.dp,
                    vertical = 4.dp
                )
            )
            Divider()
            DropdownMenuItem(
                text = {
                    Text(text = "Выйти из аккаунта")
                },
                onClick = signOut
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Отмена")
                },
                onClick = { expanded = false }
            )
        }
    }
}