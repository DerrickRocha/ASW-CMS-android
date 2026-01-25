@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.aswcms.ui.stores

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AddStoreDialog(onDismissAddStoreDialog: () -> Unit, onSaveClicked: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissAddStoreDialog, properties = DialogProperties(
            usePlatformDefaultWidth = false, // Crucial for allowing the dialog to be full width
            decorFitsSystemWindows = false // Helps manage system bars (status/nav bar)
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AddStoreDialogSection(onSaveClicked, onDismissAddStoreDialog)
        }
    }
}

@Composable
fun AddStoreDialogSection(onSaveClicked: () -> Unit, onCancelClicked: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            AddStoreTopAppBar(onCancelClicked = onCancelClicked, onSaveClicked = onSaveClicked)
            AddStoreDialogContent()
        }
    }
}

@Composable
fun AddStoreDialogContent() {
    TODO("Not yet implemented")
}

@Composable
fun AddStoreTopAppBar(onSaveClicked: () -> Unit, onCancelClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Add a new store",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onCancelClicked) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close dialog"
                )
            }
        },
        actions = {
            TextButton(
                onClick = onSaveClicked,
                enabled = true
            ) {
                Text("Save")
            }
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Preview
@Composable
fun AddStoreDialogPreview() {
    AddStoreDialog(onDismissAddStoreDialog = {}, onSaveClicked = {})
}