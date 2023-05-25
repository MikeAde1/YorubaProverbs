package example.yorubaproverbs.presentation.dashboard.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class TabRowItem(
    val title: String,
    val icon: ImageVector? = null,
    val screen: (@Composable () -> Unit)? = null
)