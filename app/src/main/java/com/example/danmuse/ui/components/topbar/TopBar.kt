package com.example.danmuse.ui.components.topbar

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    isSearching: Boolean = false,
    canNavigateBack: Boolean = false,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    clearQuery: () -> Unit = {},
    navigateBack: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            if (isSearching)
                ExpandableSearchView(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearchDisplayClosed = clearQuery
                )
            else
                Text(text = "DanMuse")
        },
        navigationIcon = {
            if(canNavigateBack)
                IconButton(onClick = navigateBack) {
                    Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "button back")
                }
        },
        colors = if(isSystemInDarkTheme())
            TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
        else
            TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.shadow(6.dp)
    )
}