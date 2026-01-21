package com.example.aswcms.ui.store

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aswcms.ui.theme.ASWCMSTheme
import com.example.aswcms.ui.viewmodels.StoresScreenState
import com.example.aswcms.ui.viewmodels.StoresScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoresScreen(viewModel: StoresScreenViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    StoresScreenContent(state)
}

@Composable
fun StoresScreenContent(state: StoresScreenState) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

        }
    }
}

@Preview("Stores Screen")
@Composable
fun PreviewStoresScreen() {
    ASWCMSTheme{
        StoresScreen()
    }

}