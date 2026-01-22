package com.example.aswcms.ui.store

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aswcms.R
import com.example.aswcms.domain.models.Store
import com.example.aswcms.ui.theme.ASWCMSTheme
import com.example.aswcms.ui.viewmodels.StoresScreenState
import com.example.aswcms.ui.viewmodels.StoresScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoresScreen(viewModel: StoresScreenViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    val onRetry = {

    }
    StoresScreenContent(state, onRetry)
}

@Composable
fun StoresScreenContent(state: StoresScreenState, onRetry:() -> Unit) {
    Box(Modifier.fillMaxSize()) {
        StoresList(state.stores)
        if(state.isLoading) {
            LoadingSection()
        }
        if(state.errorMessage != null) {
            ErrorScreen(state.errorMessage, onRetry)
        }
    }
}

@Composable
fun StoresList(stores: List<Store>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(stores) { store ->
            Text(text=store.name)
        }
    }
}

@Composable
fun LoadingSection() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(errorMessage: String, onRetryClicked: () -> Unit){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text(errorMessage, Modifier.testTag("retry_button"))
            Button(
                onClick = onRetryClicked
            ) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}

@Preview("Stores Screen", showSystemUi = true)
@Composable
fun PreviewStoresScreen() {
    ASWCMSTheme{
        StoresScreen()
    }

}