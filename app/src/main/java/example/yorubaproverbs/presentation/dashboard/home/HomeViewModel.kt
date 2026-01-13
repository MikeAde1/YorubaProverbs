package example.yorubaproverbs.presentation.dashboard.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import example.yorubaproverbs.R
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    val categoriesList = listOf(
        ProverbCategoriesUiItem(
            name = R.string.the_good_person,
            translation = R.string.the_good_person_translation
        ),
        ProverbCategoriesUiItem(
            name = R.string.the_good_life,
            translation = R.string.the_good_life_translation
        ),
        ProverbCategoriesUiItem(
            name = R.string.relationship,
            translation = R.string.relationship_translation
        ),
        ProverbCategoriesUiItem(
            name = R.string.human_nature,
            translation = R.string.human_nature_translation
        ),
        ProverbCategoriesUiItem(
            name = R.string.rights_and_responsibilities,
            translation = R.string.rights_translation
        ),
        ProverbCategoriesUiItem(
            name = R.string.truism,
            translation = R.string.truism_translation
        )
    )
}