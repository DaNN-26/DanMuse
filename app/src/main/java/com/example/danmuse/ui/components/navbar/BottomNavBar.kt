package com.example.danmuse.ui.components.navbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.danmuse.model.BottomNavigationItem

@Composable
fun BottomNavBar(
    isBottomNavBarVisible: Boolean,
    navigateToHome: () -> Unit,
    navigateToVkMusic: () -> Unit
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    AnimatedVisibility(
        visible = isBottomNavBarVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {

        NavigationBar(
            containerColor = if (!isSystemInDarkTheme())
                Color.White
            else
                MaterialTheme.colorScheme.inverseOnSurface,
            modifier = Modifier
                .height(78.dp)
                .shadow(8.dp)
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedIndex == index,
                    onClick = {
                        when (index) {
                            0 -> {
                                navigateToHome()
                                selectedIndex = index
                            }
                            1 -> {
                                navigateToVkMusic()
                                selectedIndex = index
                            }
                            2 -> selectedIndex = index
                        }
                    },
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = item.title)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = if (!isSystemInDarkTheme())
                            MaterialTheme.colorScheme.inverseOnSurface
                        else
                            MaterialTheme.colorScheme.surface
                    )
                )
            }
        }
    }
}

val items = listOf(
    BottomNavigationItem(
        title = "Дом",
        icon = Icons.Rounded.Home
    ),
    BottomNavigationItem(
        title = "ВК Музыка",
        icon = Icons.Rounded.List
    ),
    BottomNavigationItem(
        title = "Профиль",
        icon = Icons.Rounded.AccountCircle
    )
)