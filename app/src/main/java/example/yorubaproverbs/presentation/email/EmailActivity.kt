package example.yorubaproverbs.presentation.email

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import example.yorubaproverbs.R
import example.yorubaproverbs.presentation.theme.YorubaProverbsTheme

@AndroidEntryPoint
class EmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        findViewById<ComposeView>(R.id.emailComposeView).setContent {
            YorubaProverbsTheme {
                Surface {
                    EmailCover()
                }
            }
        }
    }

    @Composable
    fun EmailCover() {
        //TODO : create a viewModel to manage the validation
        // and possibly the sending of the email
        var fullName by remember { mutableStateOf("") }
        var proverb by remember { mutableStateOf("") }
        var explanation by remember { mutableStateOf("") }
        var translation by remember { mutableStateOf("") }
        var error by remember { mutableStateOf(ErrorMessage.NONE) }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
        ) {
            Text(
                color = colorResource(id = R.color.colorGrey),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.email_description),
                modifier = Modifier.padding(vertical = 10.dp)
            )
            TextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                    if (it.isNotBlank() && error == ErrorMessage.FULL_NAME) {
                        error = ErrorMessage.NONE
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                placeholder = { Text(text = "Full Name") },
                singleLine = true,
                isError = error == ErrorMessage.FULL_NAME,
                trailingIcon = {
                    if (error == ErrorMessage.FULL_NAME) {
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = colorResource(id = R.color.colorPrimary)
                )
            )
            TextField(
                value = proverb,
                onValueChange = {
                    proverb = it
                    if (it.isNotBlank() && error == ErrorMessage.PROVERB) {
                        error = ErrorMessage.NONE
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                placeholder = { Text(text = "Proverb") },
                singleLine = true,
                isError = error == ErrorMessage.PROVERB,
                trailingIcon = {
                    if (error == ErrorMessage.PROVERB) {
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = colorResource(id = R.color.colorPrimary)
                )
            )
            TextField(
                value = explanation,
                onValueChange = {
                    explanation = it
                    if (it.isNotBlank() && error == ErrorMessage.EXPLANATION) {
                        error = ErrorMessage.NONE
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                placeholder = { Text(text = "Explanation") },
                singleLine = true,
                isError = error == ErrorMessage.EXPLANATION,
                trailingIcon = {
                    if (error == ErrorMessage.EXPLANATION) {
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = colorResource(id = R.color.colorPrimary)
                )
            )
            TextField(
                value = translation,
                onValueChange = {
                    translation = it
                    if (it.isNotBlank() && error == ErrorMessage.TRANSLATION) {
                        error = ErrorMessage.NONE
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                placeholder = { Text(text = "Translation") },
                singleLine = true,
                isError = error == ErrorMessage.TRANSLATION,
                trailingIcon = {
                    if (error == ErrorMessage.TRANSLATION) {
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = colorResource(id = R.color.colorPrimary)
                )
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                contentPadding = PaddingValues(vertical = 15.dp),
                onClick = {
                    validateEntries(
                        fullName, proverb,explanation, translation,
                        { error = ErrorMessage.FULL_NAME },
                        { error = ErrorMessage.PROVERB },
                        { error = ErrorMessage.EXPLANATION },
                        { error = ErrorMessage.TRANSLATION }
                    )
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorPrimary))
            ) {
                Text(text = "Send", color = Color.LightGray)
            }
        }
    }

    private fun validateEntries(
        fullName: String,
        proverb: String,
        explanation: String,
        translation: String,
        fullNameErrorAction: () -> Unit,
        proverbErrorAction: () -> Unit,
        explanationErrorAction: () -> Unit,
        translationErrorAction: () -> Unit
    ) {
        when {
            fullName.isBlank() -> fullNameErrorAction()
            proverb.isBlank() -> proverbErrorAction()
            explanation.isBlank() -> explanationErrorAction()
            translation.isBlank() -> translationErrorAction()
            else -> {
                val it = Intent(Intent.ACTION_SEND)
                it.putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf(getString(R.string.email_address))
                )
                it.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Addition to Proverbs catalogue"
                )
                it.putExtra(
                    Intent.EXTRA_TEXT,
                    "Proverb: $proverb \n " +
                            "Translation: $translation \n " +
                            "Explanation: $explanation"
                )
                it.type = "message/rfc822"
                try {
                    startActivity(Intent.createChooser(it, "Choose Mail App"))
                } catch (e: ActivityNotFoundException) {
                    //TODO: Handle case where no email app is available
                }
                startActivity(Intent.createChooser(it, "Choose Mail App"))
            }
        }
    }


    enum class ErrorMessage(val message: String) {
        FULL_NAME("Full name is empty"),
        PROVERB("Proverb is empty"),
        EXPLANATION("Explanation is empty"),
        TRANSLATION("Translation is empty"),
        NONE("")
    }

    @Preview
    @Composable
    fun EmailCoverPrev() {
        Surface {
            EmailCover()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}