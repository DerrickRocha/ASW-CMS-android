package com.example.aswcms.extensions

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun Context.createCameraUri(): Uri {
    val imageFile = File(
        this.cacheDir,
        "camera_${System.currentTimeMillis()}.jpg"
    )

    return FileProvider.getUriForFile(
        this,
        "${this.packageName}.fileprovider",
        imageFile
    )
}
