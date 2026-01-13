package example.yorubaproverbs.domain

import example.yorubaproverbs.models.ProverbData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun performSearch(searchText: String, searchLanguage: String): Flow<List<ProverbData>>
}