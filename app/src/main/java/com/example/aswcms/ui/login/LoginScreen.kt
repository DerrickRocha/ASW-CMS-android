package com.example.aswcms.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aswcms.R
import com.example.aswcms.ui.theme.ASWCMSTheme
import com.example.aswcms.ui.theme.Typography

@Composable
fun LoginScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.asw_logo),
                    contentDescription = ""
                )
                Spacer(Modifier.width(8.dp))
                Text(modifier = Modifier, text = "Agile Southwest CMS", style = Typography.titleLarge)
            }

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