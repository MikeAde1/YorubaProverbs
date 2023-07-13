package example.yorubaproverbs.presentation.dashboard.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import example.yorubaproverbs.models.ProverbData
import example.yorubaproverbs.presentation.dashboard.BaseState
import example.yorubaproverbs.presentation.dashboard.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllProverbsViewModel @Inject constructor(
    private val allProverbsRepository: AllProverbsRepository
) : ViewModel() {
    private val _allProverbsScreenState = MutableStateFlow(AllProverbsScreenState())
    val allProverbsScreenState get() = _allProverbsScreenState.asStateFlow()

    fun getAllProverbs() {
        viewModelScope.launch {
            allProverbsRepository.getAllProverbs()
                .catch { error ->
                    _allProverbsScreenState.update {
                        it.apply { errorMessage = error.message.orEmpty() }
                    }
                }.collect { result ->
                    _allProverbsScreenState.update {
                        it.loadingState = LoadingState.Success
                        it.copy(searchResultList = result)
                    }
                }
        }
    }
}


data class AllProverbsScreenState(
    val searchResultList: List<ProverbData> = listOf()
) : BaseState()