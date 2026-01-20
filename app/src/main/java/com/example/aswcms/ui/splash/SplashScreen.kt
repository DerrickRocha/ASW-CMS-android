package com.example.aswcms.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aswcms.R
import com.example.aswcms.ui.theme.ASWCMSTheme
import com.example.aswcms.ui.theme.Typography
import com.example.aswcms.ui.viewmodels.SplashEffect
import com.example.aswcms.ui.viewmodels.SplashViewModel

@Composable
fun SplashScreen(viewModel: SplashViewModel = viewModel(), onShowNextScreen: () -> Unit = {}) {
    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when(effect) {
                SplashEffect.ShowNextScreen -> onShowNextScreen()
            }
        }
    }
    SplashContent()
}

@Composable
fun SplashContent() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier,
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
        }
    }
}

@Preview(name = "Splash Screen")
@Composable
fun showSplash() {
    ASWCMSTheme {
        SplashScreen()
    }
}