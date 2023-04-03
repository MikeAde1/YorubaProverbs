package example.yorubaproverbs

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import example.yorubaproverbs.ui.theme.YorubaProverbsTheme

class EmailActivity : AppCompatActivity() {
    private var name: EditText? = null
    private var proverb: EditText? = null
    private var explanation: EditText? = null
    private var translation: EditText? = null
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
        /*name = findViewById(R.id.fname)
        proverb = findViewById(R.id.edProverb)
        explanation = findViewById(R.id.edExplanation)
        translation = findViewById(R.id.edTranslation)*/
    }

    @Composable
    fun EmailCover() {
        val fullName = remember { mutableStateOf("") }
        val proverb = remember { mutableStateOf("") }
        val explanation = remember { mutableStateOf("") }
        val translation = remember { mutableStateOf("") }
        val error = remember { mutableStateOf(ErrorMessage.NONE) }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
        ) {
            TextField(
                value = fullName.value,
                onValueChange = {
                    fullName.value = it
                    if (it.isNotBlank() && error.value == ErrorMessage.FULL_NAME) {
                        error.value = ErrorMessage.NONE
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                placeholder = { Text(text = "Full Name") },
                singleLine = true,
                isError = error.value == ErrorMessage.FULL_NAME,
                trailingIcon = {
                    if (error.value == ErrorMessage.FULL_NAME) {
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = colorResource(id = R.color.colorPrimary)
                )
            )
            TextField(
                value = proverb.value,
                onValueChange = {
                    proverb.value = it
                    if (it.isNotBlank() && error.value == ErrorMessage.PROVERB) {
                        error.value = ErrorMessage.NONE
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                placeholder = { Text(text = "Proverb") },
                singleLine = true,
                isError = error.value == ErrorMessage.PROVERB,
                trailingIcon = {
                    if (error.value == ErrorMessage.PROVERB) {
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = colorResource(id = R.color.colorPrimary)
                )
            )
            TextField(
                value = explanation.value,
                onValueChange = {
                    explanation.value = it
                    if (it.isNotBlank() && error.value == ErrorMessage.EXPLANATION) {
                        error.value = ErrorMessage.NONE
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                placeholder = { Text(text = "Explanation") },
                singleLine = true,
                isError = error.value == ErrorMessage.EXPLANATION,
                trailingIcon = {
                    if (error.value == ErrorMessage.EXPLANATION) {
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = colorResource(id = R.color.colorPrimary)
                )

            )
            TextField(
                value = translation.value,
                onValueChange = {
                    translation.value = it
                    if (it.isNotBlank() && error.value == ErrorMessage.TRANSLATION) {
                        error.value = ErrorMessage.NONE
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                placeholder = { Text(text = "Translation") },
                singleLine = true,
                isError = error.value == ErrorMessage.TRANSLATION,
                trailingIcon = {
                    if (error.value == ErrorMessage.TRANSLATION) {
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
                          when {
                              fullName.value.isBlank() -> error.value = ErrorMessage.FULL_NAME
                              proverb.value.isBlank() -> error.value = ErrorMessage.PROVERB
                              explanation.value.isBlank() -> error.value = ErrorMessage.EXPLANATION
                              translation.value.isBlank() -> error.value = ErrorMessage.TRANSLATION
                              else -> {
                                  val it = Intent(Intent.ACTION_SEND)
                                  it.putExtra(
                                      Intent.EXTRA_EMAIL,
                                      arrayOf("michael.adeneye@yahoo.com")
                                  )
                                  it.putExtra(
                                      Intent.EXTRA_SUBJECT,
                                      "Addition to Proverbs catalogue"
                                  )
                                  it.putExtra(
                                      Intent.EXTRA_TEXT,
                                      "Proverb: ${proverb.value} \n " +
                                              "Translation: ${translation.value} \n " +
                                              "Explanation: ${explanation.value}"
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
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.colorPrimary)
                )
            ) {
                Text(text = "Send", color = Color.LightGray)
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

    fun send(view: View?) {
        val get_name = name?.text.toString()
        val get_proverb = proverb?.text.toString()
        val get_explanation = explanation?.text.toString()
        val get_translation = translation?.text.toString()
        if (get_name == "" || get_proverb == "" || get_explanation == "" || get_translation == "") {
            /*Sna.make(view!!, "Please fill in all of the entries", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
        } else {
            val it = Intent(Intent.ACTION_SEND)
            it.putExtra(Intent.EXTRA_EMAIL, arrayOf("michael.adeneye@yahoo.com"))
            it.putExtra(Intent.EXTRA_SUBJECT, "Addition to Proverbs catalogue")
            it.putExtra(
                Intent.EXTRA_TEXT, """
                 Proverb: $get_proverb
                 Translation: $get_translation
                 Explanation: $get_explanation
                 """.trimIndent()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}