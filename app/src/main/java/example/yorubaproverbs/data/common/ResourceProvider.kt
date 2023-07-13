package example.yorubaproverbs.data.common

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourceProvider@Inject constructor(
    private val context: Context
) {
    fun getString(@StringRes res: Int) = context.getString(res)
}