package example.yorubaproverbs.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import example.yorubaproverbs.data.AllProverbsRepositoryImpl
import example.yorubaproverbs.data.CategorizedListRepositoryImpl
import example.yorubaproverbs.data.HomeRepositoryImpl
import example.yorubaproverbs.domain.CategorizedListRepository
import example.yorubaproverbs.domain.HomeRepository
import example.yorubaproverbs.presentation.dashboard.all.AllProverbsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsHomeRepo(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun bindsAllProverbsRepo(allProverbsRepositoryImpl: AllProverbsRepositoryImpl): AllProverbsRepository

    @Binds
    abstract fun bindCategorizedProverbs(categorizedListRepositoryImpl: CategorizedListRepositoryImpl): CategorizedListRepository
}