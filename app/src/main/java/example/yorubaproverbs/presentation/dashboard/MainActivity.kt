package example.yorubaproverbs.presentation.dashboard

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import example.yorubaproverbs.presentation.searchdetails.ProverbDetails
import example.yorubaproverbs.R
import example.yorubaproverbs.presentation.dashboard.about.AboutCover
import example.yorubaproverbs.presentation.dashboard.all.AllProverbs
import example.yorubaproverbs.presentation.dashboard.home.ProverbCategories
import example.yorubaproverbs.presentation.dashboard.home.ShowSearchView
import example.yorubaproverbs.presentation.dashboard.home.TabRowItem
import example.yorubaproverbs.presentation.theme.YorubaProverbsTheme
import kotlinx.coroutines.launch

/**
 * @author Michael Adeneye
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YorubaProverbsTheme {
                Surface {
                    BaseActivityComposable()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BaseActivityComposable(viewModel: MainViewModel = viewModel()) {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        var shouldSearch by remember { mutableStateOf(false) }
        var searchText by remember { mutableStateOf("") }
        var searchLanguage by remember { mutableStateOf("English") }

        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (shouldSearch.not()) {
                        ShowMainContent(scrollBehavior) { shouldSearch = true }
                    } else {
                        ShowSearchView(
                            searchText,
                            searchLanguage,
                            { shouldSearch = false },
                            { shouldSearch = true },
                            { searchText = it },
                            {
                                searchLanguage = if (it == 1) {
                                    "English"
                                } else {
                                    "Yoruba"
                                }
                            }
                        )
                    }
                }
            }
        ) {
            it.calculateTopPadding()
            if (shouldSearch.not()) {
                ShowTabs()
            } else {
                SearchResult(viewModel, searchText, searchLanguage)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShowMainContent(
        scrollBehavior: TopAppBarScrollBehavior,
        shouldSearch: () -> Unit
    ) {
        TopAppBar(
            title = {
                Text(
                    stringResource(id = R.string.title_name),
                    style = TextStyle(
                        color = colorResource(id = R.color.colorViewpager)
                    )
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.colorPrimary)
            ),
            scrollBehavior = scrollBehavior,
            actions = {
                androidx.compose.material3.IconButton(
                    onClick = { shouldSearch() },
                    content = {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint = Color.LightGray
                        )
                    }
                )
            }
        )
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun ShowTabs() {
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()
        val tabRowItems = listOf(
            TabRowItem(
                title = stringResource(id = R.string.home),
            ),
            TabRowItem(
                title = stringResource(id = R.string.all)
            ),
            TabRowItem(
                title = stringResource(id = R.string.about)
            )
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TabRow(
                backgroundColor = colorResource(id = R.color.colorPrimary),
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                        color = colorResource(id = R.color.colorAccent)
                    )
                },
            ) {
                tabRowItems.forEachIndexed { index, item ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    index
                                )
                            }
                        },
                        text = {
                            Text(
                                text = item.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = TextStyle(
                                    color = colorResource(id = R.color.colorViewpager)
                                )
                            )
                        }
                    )
                }
            }
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize(),
                count = tabRowItems.size,
                state = pagerState
            ) { index ->
                when (index) {
                    0 -> ProverbCategories()
                    1 -> AllProverbs()
                    2 -> AboutCover()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun SearchResult(viewModel: MainViewModel, searchText: String, searchLanguage: String) {
        var isSearching by remember {
            mutableStateOf(false)
        }
        isSearching = searchText.isNotEmpty()
        val context = LocalContext.current
        val result = viewModel.homeScreenState.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = searchText, key2 = searchLanguage) {
            if (searchText.isNotEmpty()) {
                viewModel.performSearch(searchText, searchLanguage)
            }
        }

        if (isSearching) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(result.value.searchResultList) { _, item ->
                    Card(
                        shape = RoundedCornerShape(1.dp),
                        elevation = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        onClick = {
                            val intent = Intent(context, ProverbDetails::class.java)
                            intent.putExtra("value", item.content)
                            intent.putExtra("translation", item.translation)
                            intent.putExtra("explanation", item.explanation)
                            context.startActivity(intent)
                        }
                    ) {
                        Text(
                            text = item.content.orEmpty(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            textAlign = TextAlign.Start,
                            color = Color.Gray,
                            style = TextStyle(fontSize = 18.sp)
                        )
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun BaseActivityComposablePrev() {
        Surface {
            BaseActivityComposable(viewModel())
        }
    }
}