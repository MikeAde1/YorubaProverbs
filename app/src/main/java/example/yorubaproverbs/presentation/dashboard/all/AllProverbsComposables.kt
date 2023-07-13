package example.yorubaproverbs.presentation.dashboard.all

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import example.yorubaproverbs.models.ProverbData
import example.yorubaproverbs.presentation.common_ui.ListLayout

@Composable
fun AllProverbs(allProverbsViewModel: AllProverbsViewModel = viewModel()) {
    val allProverbsScreenState =
        allProverbsViewModel.allProverbsScreenState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = allProverbsViewModel) {
        allProverbsViewModel.getAllProverbs()
    }
    ListLayout(allProverbsScreenState.value.searchResultList)
}

@Preview
@Composable
fun AllProverbsPreview() {
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
    Surface { ListLayout(proverbDataList) }
}