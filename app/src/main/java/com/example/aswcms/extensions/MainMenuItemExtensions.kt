package com.example.aswcms.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.aswcms.R
import com.example.aswcms.ui.main.MainMenuItem

@Composable
fun MainMenuItem.resolveMainMenuItemString(): String {
    return when (this) {
        MainMenuItem.Account -> stringResource(R.string.account)
        MainMenuItem.Logout -> stringResource(R.string.logout)
        MainMenuItem.Stores -> stringResource(R.string.stores)
    }
}