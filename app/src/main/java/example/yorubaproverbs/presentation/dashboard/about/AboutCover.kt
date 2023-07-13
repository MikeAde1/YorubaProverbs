package example.yorubaproverbs.presentation.dashboard.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Surface
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutCover() {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 20.dp)
        ) {
            val context = LocalContext.current
            Header()
            Spacer(modifier = Modifier.height(30.dp))
            AboutAppMessage()
            Spacer(modifier = Modifier.height(10.dp))
            EmailClickableText(context)
        }
    }
}

@Composable
fun Header() {
    Text(
        text = "YORUBA PROVERBS 1.1",
        color = Color.Gray,
        style = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp
        )
    )
    TabRowDefaults.Divider(
        modifier = Modifier.width(135.dp),
        color = Color.DarkGray,
        thickness = 1.dp
    )
}

@Composable
fun AboutAppMessage() {
    val globalText = stringResource(id = example.yorubaproverbs.R.string.disclaimer)
    val start = globalText.indexOf("Oyékàn")
    val spanStyles = listOf(
        AnnotatedString.Range(
            SpanStyle(fontWeight = FontWeight.Bold),
            start = start,
            end = globalText.length
        )
    )
    Text(
        text = AnnotatedString(
            text = globalText,
            spanStyles = spanStyles
        ),
        style = TextStyle(
            fontSize = 14.sp,
            color = colorResource(id = example.yorubaproverbs.R.color.colorGrey)
        )
    )
}

@Composable
fun EmailClickableText(context: Context) {
    val mAnnotatedLinkString = getStringAsLink()
    ClickableText(
        modifier = Modifier.fillMaxWidth(),
        text = mAnnotatedLinkString,
        style = TextStyle(
            color = colorResource(id = example.yorubaproverbs.R.color.colorGrey)
        ),
        onClick = { navToEmailCompose(mAnnotatedLinkString.substring(49), context) }
    )
}

@Composable
fun getStringAsLink(): AnnotatedString =
    buildAnnotatedString {
        val mStr = stringResource(id = example.yorubaproverbs.R.string.feedback)

        val mStartIndex = 49
        val mEndIndex = mStr.length

        append(mStr)
        addStyle(
            style = SpanStyle(
                color = colorResource(id = example.yorubaproverbs.R.color.colorPrimary),
                textDecoration = TextDecoration.Underline
            ),
            start = mStartIndex, end = mEndIndex
        )
    }


private fun navToEmailCompose(email: String, context: Context) {
    val intent = Intent(
        Intent.ACTION_SENDTO,
        Uri.parse("mailto:${email}")
    )
    context.startActivity(Intent.createChooser(intent, "Choose Email app"))
}