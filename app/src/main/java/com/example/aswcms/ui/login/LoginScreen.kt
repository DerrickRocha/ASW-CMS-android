package com.example.aswcms.ui.login

import LoginState
import LoginViewModel
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aswcms.R
import com.example.aswcms.ui.theme.ASWCMSTheme
import com.example.aswcms.ui.theme.Typography

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current

    val onSignInClicked = {
        viewModel.onLoginIntent(
            context,
        )
    }

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                LoginEffect.ShowSignInSuccess ->
                    Toast.makeText(context, "Sign in successful!", Toast.LENGTH_SHORT).show()

                is LoginEffect.ShowError ->
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LoginScreenMainSection(state, onSignInClicked)
}

@Composable
fun LoginScreenMainSection(state: LoginState, onSignInClicked: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.testTag("loading_indicator")
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.asw_logo),
                    contentDescription = ""
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier,
                    text = "Agile Southwest CMS",
                    style = Typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "You must login to continue",
                style = Typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                modifier = Modifier
                    .testTag("google_sign_in_button")
                    .clickable(enabled = true, onClick = onSignInClicked),
                painter = painterResource(R.drawable.android_light_sq_si),
                contentDescription = "",
            )
        }
    }
}

@Preview(name = "LoginScreen preview")
@Composable
fun ShowLoginScreen() {
    ASWCMSTheme {
        LoginScreen()
    }
}