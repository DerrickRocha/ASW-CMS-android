package com.example.aswcms.ui.stores

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aswcms.R
import com.example.aswcms.domain.models.Store
import com.example.aswcms.ui.components.LoadingSection
import com.example.aswcms.ui.theme.ASWCMSTheme
import com.example.aswcms.ui.viewmodels.StoresScreenState
import com.example.aswcms.ui.viewmodels.StoresScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoresScreen(
    viewModel: StoresScreenViewModel = viewModel(),
    onStoreClicked: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val onRetry = {

    }
    val isAddingStore = rememberSaveable { mutableStateOf(false) }
    val onCreateStoreClicked = {
        isAddingStore.value = true
    }
    val onDismissAddStoreDialog = {
        isAddingStore.value = false
    }
    StoresScreenContent(
        state,
        isAddingStore.value,
        onRetry,
        onStoreClicked,
        onCreateStoreClicked,
        onDismissAddStoreDialog
    )
}

@Composable
fun StoresScreenContent(
    state: StoresScreenState,
    isAddingStore: Boolean,
    onRetry: () -> Unit,
    onStoreClicked: (Int) -> Unit,
    onCreateStoreClicked: () -> Unit,
    onDismissAddStoreDialog: () -> Unit
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = onCreateStoreClicked
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add a new store"
            )
        }
    }) {
        Box(Modifier.fillMaxSize()) {
            StoresList(state.stores, onStoreClicked)
            if (state.isLoading) {
                LoadingSection()
            }
            if (state.errorMessage != null) {
                ErrorScreen(state.errorMessage, onRetry)
            }
        }
    }
    if (isAddingStore) {
        AddStoreDialog(onDismissAddStoreDialog)
    }
}

@Composable
fun AddStoreDialog(onDismissAddStoreDialog: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissAddStoreDialog, properties = DialogProperties(
            usePlatformDefaultWidth = false, // Crucial for allowing the dialog to be full width
            decorFitsSystemWindows = false // Helps manage system bars (status/nav bar)
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("This is a full-screen dialog!")
                    // ... add a close button or top app bar here
                }
            }
        }
    }
}

@Composable
fun StoresList(stores: List<Store>, onStoreClicked: (Int) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(stores) { store ->
            StoreCard(store, onStoreClicked)
        }
    }
}

@Composable
fun StoreCard(store: Store, onStoreClicked: (Int) -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = { onStoreClicked(store.storeId) })
    ) {
        Row(Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.asw_logo),
                contentDescription = ""
            )
            Spacer(Modifier.width(8.dp))
            Text(text = store.name, fontSize = 22.sp)
        }
    }
}

@Composable
fun ErrorScreen(errorMessage: String, onRetryClicked: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
    ASWCMSTheme {
        StoreCard(Store(name = "The Titty Twister", 1), {})
    }

}