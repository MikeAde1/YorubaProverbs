package example.yorubaproverbs.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.Html
import android.text.Spanned
import android.widget.Toast
import androidx.core.text.HtmlCompat

fun copyToClipboard(context: Context, text: String) {
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("", text)
    clipboardManager.setPrimaryClip(clip)
}

fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun String.toolbarTitle(): Spanned {
    return HtmlCompat.fromHtml("<font color=\"#FFFFFF\">${this.toTitleCase()}", HtmlCompat.FROM_HTML_MODE_COMPACT)
}

fun String.toTitleCase() =
    this.split("\\s".toRegex()).joinToString(" ") {
        it.lowercase().replaceFirstChar(Char::titlecase)
    }