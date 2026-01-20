package com.example.aswcms.ui

import LoginState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
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
            .onNodeWithTag("google_sign_in_button")
            .assertExists()
    }

    @Test
    fun showsLoadingIndicator_whenLoading() {
        composeRule.setContent {
            LoginScreenMainSection(
                state = LoginState(isLoading = true),
                onSignInClicked = {}
            )
        }

        composeRule
            .onNodeWithTag("loading_indicator")
            .assertExists()
    }

    @Test
    fun clickingSignIn_callsCallback() {
        var clicked = false

        composeRule.setContent {
            LoginScreenMainSection(
                state = LoginState(isLoading = false),
                onSignInClicked = { clicked = true }
            )
        }

        composeRule
            .onNodeWithTag("google_sign_in_button")
            .performClick()

        assert(clicked)
    }

}