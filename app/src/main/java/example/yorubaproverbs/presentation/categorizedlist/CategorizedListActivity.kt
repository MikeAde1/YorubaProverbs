package example.yorubaproverbs.presentation.categorizedlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import example.yorubaproverbs.R
import example.yorubaproverbs.models.ProverbData
import example.yorubaproverbs.presentation.common_ui.ListLayout
import example.yorubaproverbs.presentation.theme.YorubaProverbsTheme
import example.yorubaproverbs.util.AppConstants
import example.yorubaproverbs.util.toolbarTitle

@AndroidEntryPoint
class CategorizedListActivity : AppCompatActivity() {
    private var category: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorized_list)

        category = intent.getStringExtra(AppConstants.categoryTitle)

        supportActionBar?.apply {
            elevation = 0f
            title = category?.toolbarTitle()
        }

        findViewById<ComposeView>(R.id.categoryListComposeView).setContent {
            YorubaProverbsTheme {
                Surface {
                    //TODO: Get the list of categories from firebase
                    // save into shared preferences
                    // On every app launch, if the saved categories data is empty,
                    // fetch from firebase and save else read locally.
                    // Schema: [category]: [context] e.g The Good Person: ìwà rere
                    category?.let { ProverbsListCover(category = it) }
                }
            }
        }
    }

    @Composable
    fun ProverbsListCover(viewModel: CategorizedListViewModel = viewModel(), category: String) {
        val proverbListState =
            viewModel.categorizedListScreenState.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = viewModel) {
            viewModel.getProverbsByCategory(category)
        }

        ListLayout(list = proverbListState.value.proverbs, false)
    }

    @Preview
    @Composable
    fun ProverbsListCoverPrev() {
        val data = ProverbData().apply {
            content = "content"
            context = "context"
            explanation = "Explanation"
            translation = "Translation"
        }
        val proverbDataList = mutableListOf(
            data,
            data,
            data,
            data
        )
        Surface {
            ListLayout(proverbDataList)
        }
    }
}