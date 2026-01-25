package com.example.aswcms.extensions

import com.example.aswcms.R
import com.example.aswcms.ui.viewmodels.MainMenuItem

fun MainMenuItem.resolveMainMenuItemString(): Int {
    return when (this) {
        MainMenuItem.Account -> R.string.account
        MainMenuItem.Logout -> R.string.logout
        MainMenuItem.Stores -> R.string.stores
    }
}