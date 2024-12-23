package com.example.danmuse.ui.app.components.topbar

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
    isSearching: Boolean,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    clearQuery: () -> Unit = {}
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
        colors = if(isSystemInDarkTheme())
            TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
        else
            TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.shadow(6.dp)
    )
}