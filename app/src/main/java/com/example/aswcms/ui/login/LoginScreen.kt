package com.example.aswcms.ui.login

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCustomException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.example.aswcms.R
import com.example.aswcms.ui.theme.ASWCMSTheme
import com.example.aswcms.ui.theme.Typography
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.util.Base64

private fun generateSecureRandomNonce(byteLength: Int = 32): String {
    val randomBytes = ByteArray(byteLength)
    SecureRandom.getInstanceStrong().nextBytes(randomBytes)
    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
}

//This code will not work on Android versions < UPSIDE_DOWN_CAKE when GetCredentialException is
//is thrown.
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
suspend fun signIn(request: GetCredentialRequest, context: Context): Exception? {
    val credentialManager = CredentialManager.create(context)
    val failureMessage = "Sign in failed!"
    var e: Exception? = null
    //using delay() here helps prevent NoCredentialException when the BottomSheet Flow is triggered
    //on the initial running of our app
    delay(250)
    try {
        // The getCredential is called to request a credential from Credential Manager.
        val result = credentialManager.getCredential(
            request = request,
            context = context,
        )
        Log.i(TAG, result.toString())

        Toast.makeText(context, "Sign in successful!", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "(☞ﾟヮﾟ)☞  Sign in Successful!  ☜(ﾟヮﾟ☜)")

    } catch (e: GetCredentialException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e(TAG, failureMessage + ": Failure getting credentials", e)

    } catch (e: GoogleIdTokenParsingException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e(TAG, failureMessage + ": Issue with parsing received GoogleIdToken", e)

    } catch (e: NoCredentialException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e(TAG, failureMessage + ": No credentials found", e)
        return e

    } catch (e: GetCredentialCustomException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e(TAG, failureMessage + ": Issue with custom credential request", e)

    } catch (e: GetCredentialCancellationException) {
        Toast.makeText(context, ": Sign-in cancelled", Toast.LENGTH_SHORT).show()
        Log.e(TAG, failureMessage + ": Sign-in was cancelled", e)
    }
    return e
}


@Composable
fun LoginScreen(webClientId: String) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val onClick: () -> Unit = {
        val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption
            .Builder(serverClientId = webClientId)
            .setNonce(generateSecureRandomNonce())
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        coroutineScope.launch {
            signIn(request, context)
        }
    }



    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                modifier = Modifier.clickable(enabled = true, onClick = onClick),
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
        LoginScreen("")
    }
}