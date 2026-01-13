package example.yorubaproverbs.presentation.categorizedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import example.yorubaproverbs.R
import example.yorubaproverbs.data.common.ResourceProvider
import example.yorubaproverbs.domain.CategorizedListRepository
import example.yorubaproverbs.models.ProverbData
import example.yorubaproverbs.presentation.dashboard.BaseState
import example.yorubaproverbs.presentation.dashboard.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorizedListViewModel@Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val categorizedListRepository: CategorizedListRepository
): ViewModel() {
    private val _categorizedListScreenState = MutableStateFlow(CategorizedListScreenState())
    val categorizedListScreenState get() = _categorizedListScreenState.asStateFlow()

    fun getProverbsByCategory(category: String) {
        viewModelScope.launch {
            categorizedListRepository.getProverbsByCategory(category.toContextFromBd())
                .catch { error ->
                    _categorizedListScreenState.update {
                        it.apply { errorMessage = error.message.orEmpty() }
                    }
                }.collect { result ->
                    _categorizedListScreenState.update {
                        it.loadingState = LoadingState.Success
                        it.copy(proverbs = result)
                    }
                }
        }
    }

    private fun String.toContextFromBd() = when(this) {
        resourceProvider.getString(R.string.the_good_person) -> resourceProvider.getString(R.string.iwa_rere)
        resourceProvider.getString(R.string.the_good_life) -> resourceProvider.getString(R.string.aye_rere)
        resourceProvider.getString(R.string.relationship) -> resourceProvider.getString(R.string.ibasepo)
        resourceProvider.getString(R.string.human_nature) -> resourceProvider.getString(R.string.iwa_eda)
        resourceProvider.getString(R.string.rights_and_responsibilities) -> resourceProvider.getString(R.string.eto)
        resourceProvider.getString(R.string.truism) -> resourceProvider.getString(R.string.otito)
        else -> ""
    }
}

data class CategorizedListScreenState(
    val proverbs: List<ProverbData> = listOf()
): BaseState()

