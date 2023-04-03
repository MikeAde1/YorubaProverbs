package example.yorubaproverbs

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Surface
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import example.yorubaproverbs.ui.theme.YorubaProverbsTheme
import java.util.*

class About : Fragment() {
    private var tv: TextView? = null
    private var tv_feedback: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview = inflater.inflate(R.layout.fragment_about, container, false)

        (rootview?.findViewById(R.id.aboutComposeView) as ComposeView).setContent {
            YorubaProverbsTheme {
                AboutCover()
            }
        }

        /*tv_feedback = rootview.findViewById(R.id.tv_feedback)
        tv = rootview.findViewById(R.id.tv_title)
        val tp = Typeface.createFromAsset(
            Objects.requireNonNull(
                requireContext()
            ).assets, "fonts/NotoSans-Bold.ttf"
        )
        tv?.setTypeface(tp)
        val email = resources.getString(R.string.feedback)
        tv_feedback?.setText(email)
        val ss = SpannableString(email)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {}
        }
        ss.setSpan(clickableSpan, 49, email.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        Linkify.addLinks(tv_feedback!!, Linkify.EMAIL_ADDRESSES)*/
        return rootview
    }

    @Preview()
    @Composable
    fun AboutCover() {
        Surface {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "YORUBA PROVERBS 1.0",
                    color = Color.Gray,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                )

                Divider(modifier = Modifier.width(135.dp), color = Color.DarkGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(30.dp))

                val globalText = stringResource(id = R.string.disclaimer)
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
                        color = colorResource(id = R.color.colorGrey)
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                val mAnnotatedLinkString = buildAnnotatedString {
                    val mStr = stringResource(id = R.string.feedback)

                    val mStartIndex = 49
                    val mEndIndex = mStr.length

                    append(mStr)
                    addStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.colorPrimary),
                            textDecoration = TextDecoration.Underline
                        ),
                        start = mStartIndex, end = mEndIndex
                    )
                }
                ClickableText(
                    modifier = Modifier.fillMaxWidth(),
                    text = mAnnotatedLinkString,
                    style = TextStyle(
                        color = colorResource(id = R.color.colorGrey)
                    ),
                    onClick = { navToEmailCompose(mAnnotatedLinkString.substring(49)) }
                )
            }
        }
    }

    // val body = Html.fromHtml(fragment.resources.getString(R.string.email_body))
    private fun navToEmailCompose(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO,
            Uri.parse("mailto:${email}"))
        startActivity(Intent.createChooser(intent, "Choose Email app"))
    }
}