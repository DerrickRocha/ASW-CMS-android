package com.example.aswcms.ui

import LoginState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.aswcms.ui.login.LoginScreenMainSection
import org.junit.Rule
import org.junit.Test

class LoginScreenTests {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun showsGoogleSignInButton() {
        composeRule.setContent {
            LoginScreenMainSection(
                state = LoginState(isLoading = false),
                onSignInClicked = {}
            )
        }

        composeRule
            .onNodeWithTag("google_sign_in_b2utton")
            .assertExists()
    }
}