package example.yorubaproverbs.presentation.dashboard.all


import example.yorubaproverbs.models.ProverbData
import kotlinx.coroutines.flow.Flow

interface AllProverbsRepository {
    fun getAllProverbs(): Flow<List<ProverbData>>
}