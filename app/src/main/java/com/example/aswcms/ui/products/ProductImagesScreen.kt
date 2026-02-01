@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.aswcms.ui.products

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.aswcms.extensions.createCameraUri
import com.example.aswcms.ui.viewmodels.ImageState
import com.example.aswcms.ui.viewmodels.ProductImagesViewModel

@Composable
fun ProductImagesScreen(viewModel: ProductImagesViewModel = hiltViewModel()) {
    var tempCameraUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            { uri ->

            }
        )
    val cameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { success ->
            if (success) {

            }
        }

    var showImageSourceSheet by rememberSaveable { mutableStateOf(false) }

    val onImageButtonClicked = {
        showImageSourceSheet = true
    }
    val onDismissImageOptions = {
        showImageSourceSheet = false
    }

    val context = LocalContext.current
    val fromCameraSelected = {
        val cameraUri = context.createCameraUri()
        tempCameraUri = cameraUri
        cameraLauncher.launch(cameraUri)
        showImageSourceSheet = false
    }
    val fromDeviceSelected = {
        imagePickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
        showImageSourceSheet = false
    }

    if (showImageSourceSheet)
        ImageOptionsBottomSheet(
            fromCameraSelected = fromCameraSelected,
            fromDeviceSelected = fromDeviceSelected,
            onDismissImageOptions = onDismissImageOptions
        )
}

@Composable
fun ImageSection(
    mainImage: ImageState?,
    images: List<ImageState>?,
    onImageButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card {
            ASWCMSImage(modifier = Modifier.size(200.dp), mainImage?.uri, "Main product image.")
        }

        Spacer(modifier = Modifier.height(8.dp))
        if (!images.isNullOrEmpty()) {
            LazyRow(modifier = Modifier.height(50.dp)) {
                items(images) {
                    ASWCMSImage(
                        modifier = Modifier.size(50.dp),
                        it.uri,
                        "Additional product image."
                    )
                }
            }

        }
        TextButton(onClick = onImageButtonClicked) {
            Text("Add an image")
        }
    }
}

@Composable
fun ImageOptionsBottomSheet(
    fromCameraSelected: () -> Unit,
    fromDeviceSelected: () -> Unit,
    onDismissImageOptions: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissImageOptions,
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            ListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = fromCameraSelected),
                leadingContent = { Text("Add from camera") },
                headlineContent = {})

            ListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = fromDeviceSelected),
                leadingContent = { Text("Add from device") },
                headlineContent = {})

        }
    }
}