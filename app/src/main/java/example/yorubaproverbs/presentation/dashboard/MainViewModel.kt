package example.yorubaproverbs.presentation.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import example.yorubaproverbs.domain.HomeRepository
import example.yorubaproverbs.models.ProverbData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    // actions to perform
    // search, click events, categories rendering
    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState get() = _homeScreenState.asStateFlow()

    fun performSearch(searchText: String, searchLanguage: String) {
        viewModelScope.launch {
            homeRepository.performSearch(searchText, searchLanguage)
                .catch { error ->
                    _homeScreenState.update {
                        it.apply { errorMessage = error.message.orEmpty() }
                    }
                }.collect { list ->
                    Log.d("::result", "$list")
                    _homeScreenState.update {
                        it.loadingState = LoadingState.Success
                        it.copy(
                            searchResultList = list
                        )
                    }
                }
        }
    }
}


data class HomeScreenState(
    val searchResultList: List<ProverbData> = listOf()
) : BaseState()

open class BaseState {
    open var errorMessage = ""
    open var loadingState = LoadingState.Loading
}

enum class LoadingState {
    // manage loading states
    // Not really going to be used since firebase data is retrieved in real time
    Loading, Success, Failure
}