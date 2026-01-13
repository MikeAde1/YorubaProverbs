package example.yorubaproverbs.ui.home

import android.content.Intent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import example.yorubaproverbs.CategorizedList

val list = listOf(
    CategoriesItem(
        name = "THE GOOD PERSON",
        translation = "ENIYAN RERE"
    ),
    CategoriesItem(
        name = "THE GOOD LIFE",
        translation = "AYE RERE"
    ),
    CategoriesItem(
        name = "RELATIONSHIP",
        translation = "IBASEPO"
    ),
    CategoriesItem(
        name = "HUMAN NATURE",
        translation = "IWA ENIYAN"
    ),
    CategoriesItem(
        name = "RIGHTS AND RESPONSIBILITIES",
        translation = "ETO ATI OJUSE"
    )
)

@Composable
fun ProverbCategories() {
    CategoryItems(list)
}

@Composable
private fun CategoryItems(categories: List<CategoriesItem>) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        items(categories) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .clickable {
                        context.startActivity(
                            Intent(context, CategorizedList::class.java).putExtra(
                                "name",
                                it.name.uppercase()
                            )
                        )
                    }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(example.yorubaproverbs.R.drawable.button)
                        .placeholder(example.yorubaproverbs.R.drawable.app_img)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(7.dp)
                ) {
                    Icon(
                        painter = painterResource(id = example.yorubaproverbs.R.drawable.ic_short_text_black_24dp),
                        contentDescription = "",
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 7.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = it.name,
                            style = TextStyle(color = colorResource(id = example.yorubaproverbs.R.color.colorPrimary))
                        )
                        Text(
                            text = "(${it.translation})",
                            style = TextStyle(
                                color = colorResource(id = example.yorubaproverbs.R.color.colorPrimary),
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProverbCategoriesPreview() {
    val list = listOf(
        CategoriesItem(
            name = "Good Person",
            translation = "ENIYAN RERE"
        ),
        CategoriesItem(
            name = "Good Person",
            translation = "ENIYAN RERE"
        ),
        CategoriesItem(
            name = "Good Person",
            translation = "ENIYAN RERE"
        ),
        CategoriesItem(
            name = "Good Person",
            translation = "ENIYAN RERE"
        ),
        CategoriesItem(
            name = "Good Person",
            translation = "ENIYAN RERE"
        )
    )
    Surface {
        CategoryItems(list)
    }
}