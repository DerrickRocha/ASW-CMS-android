package com.example.aswcms.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aswcms.ui.theme.ASWCMSTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CMSSearchBar() {
    val state = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()

    SearchBar(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        state = state,
        inputField = {
            SearchBarDefaults.InputField(
                query = "",
                onQueryChange = { },
                onSearch = { s ->
                    scope.launch {
                        state.animateToCollapsed()

                    }
                },
                expanded = true,
                onExpandedChange = {  },
                placeholder = { Text("Search") }
            )
        }
    )
}

@Preview(name = "CMS SearchBar")
@Composable
fun showSearchbar() {
    ASWCMSTheme {
        CMSSearchBar()
    }
}