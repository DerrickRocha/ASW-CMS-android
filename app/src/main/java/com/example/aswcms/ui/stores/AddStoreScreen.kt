@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.aswcms.ui.stores

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aswcms.R
import com.example.aswcms.ui.components.CMSSimpleDialog
import com.example.aswcms.ui.components.LoadingSection
import com.example.aswcms.ui.viewmodels.AddStoreEvent
import com.example.aswcms.ui.viewmodels.AddStoreScreenViewModel

@Composable
fun AddStoreScreen(
    viewModel: AddStoreScreenViewModel = viewModel(), onDismissAddStoreDialog: () -> Unit
) {

    val onSaveClicked = {
        viewModel.onRequestAddStore()
    }

    val onStoreNameChanged: (String) -> Unit = { value ->
        viewModel.onStoreNameChanged(value)
    }

    val onDomainChanged: (String) -> Unit = { value ->
        viewModel.onDomainNameChanged(value)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                AddStoreEvent.Success -> {
                    //todo show snack
                    onDismissAddStoreDialog()
                }
            }
        }
    }

    Dialog(
        onDismissRequest = onDismissAddStoreDialog, properties = DialogProperties(
            usePlatformDefaultWidth = false, // Crucial for allowing the dialog to be full width
            decorFitsSystemWindows = false // Helps manage system bars (status/nav bar)
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AddStoreDialogSection(
                storeName = state.storeName,
                domain = state.domain,
                onSaveClicked,
                onDismissAddStoreDialog,
                onStoreNameChanged,
                onDomainChanged
            )
            CMSSimpleDialog(
                stringResource(R.string.error_saving_store),
                show = state.error != null,
                {},
                {
                    viewModel.onErrorDismissed()
                })
            if (state.isSaving) {
                LoadingSection()
            }
        }

    }
}

@Composable
fun AddStoreDialogSection(
    storeName: String,
    domain: String,
    onSaveClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    onStoreNameChanged: (String) -> Unit,
    onDomainChanged: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column {
            AddStoreTopAppBar(onCancelClicked = onCancelClicked, onSaveClicked = onSaveClicked)
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                TextField(
                    value = storeName,
                    onValueChange = onStoreNameChanged,
                    label = { Text(stringResource(R.string.store_name)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = domain,
                    onValueChange = onDomainChanged,
                    label = { Text(stringResource(R.string.domain)) }
                )
            }
        }
    }
}

@Composable
fun AddStoreTopAppBar(onSaveClicked: () -> Unit, onCancelClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.add_a_new_store),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }, navigationIcon = {
            IconButton(onClick = onCancelClicked) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_dialog)
                )
            }
        }, actions = {
            TextButton(
                onClick = onSaveClicked, enabled = true
            ) {
                Text(stringResource(R.string.save))
            }
        }, colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Preview
@Composable
fun AddStoreScreenPreview() {
    AddStoreScreen {}
}