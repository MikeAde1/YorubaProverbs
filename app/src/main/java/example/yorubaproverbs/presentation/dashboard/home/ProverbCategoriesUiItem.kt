package example.yorubaproverbs.presentation.dashboard.home

import androidx.annotation.StringRes

data class ProverbCategoriesUiItem(
    @StringRes
    val name: Int,

    @StringRes
    val translation: Int
)