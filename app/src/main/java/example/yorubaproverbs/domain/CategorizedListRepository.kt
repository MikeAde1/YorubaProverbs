package example.yorubaproverbs.domain

import example.yorubaproverbs.models.ProverbData
import kotlinx.coroutines.flow.Flow

interface CategorizedListRepository {
    fun getProverbsByCategory(category: String): Flow<List<ProverbData>>
}