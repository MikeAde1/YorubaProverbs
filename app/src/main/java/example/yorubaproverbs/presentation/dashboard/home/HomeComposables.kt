package example.yorubaproverbs.presentation.dashboard.home

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.webkit.MimeTypeMap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import example.yorubaproverbs.R
import example.yorubaproverbs.presentation.categorizedlist.CategorizedListActivity
import example.yorubaproverbs.util.AppConstants
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProverbCategories() {
    CategoryItems()
}

fun createFile(context: Context, mimeType: String): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val fileName = "TMP_" + timeStamp + "_"
    val suffix = "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) //.pdf
    return context.getExternalFilesDir("Documents")?.let {
        if (!it.exists()) {
            it.mkdir()
        }
        File.createTempFile(fileName, suffix, it)
    } ?: File.createTempFile(fileName, suffix, Environment.getExternalStorageDirectory())
}

@Composable
private fun CategoryItems(homeViewModel: HomeViewModel = viewModel()) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp)
    ) {
        items(homeViewModel.categoriesList) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .clickable {
                        context.startActivity(
                            Intent(context, CategorizedListActivity::class.java).putExtra(
                                AppConstants.categoryTitle,
                                context.getString(it.name)
                            )
                        )
                        /*val file = createFile(context, "application/pdf")
                        val str = ""
                        val base = Base64.getDecoder().decode(str)
                        val fos = FileOutputStream(file)
                        fos.write(base)
                        fos.close()

                        try {
                            val uri = FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.provider", file)

                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.setDataAndType(uri, "application/pdf")
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Log.d("::msg", e.message?:e.localizedMessage)
                        }*/
                    }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.button)
                        .placeholder(R.drawable.button)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                )
                Icon(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(7.dp),
                    painter = painterResource(id = R.drawable.ic_short_text_black_24dp),
                    contentDescription = "",
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 28.dp, horizontal = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = it.name),
                        style = TextStyle(color = colorResource(id = R.color.colorPrimary))
                    )
                    Text(
                        text = stringResource(id = it.translation).uppercase(),
                        style = TextStyle(
                            color = colorResource(id = R.color.colorPrimary),
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ProverbCategoriesPreview() {
    Surface {
        CategoryItems(viewModel())
    }
}