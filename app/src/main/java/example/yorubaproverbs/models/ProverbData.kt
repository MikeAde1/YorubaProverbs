package example.yorubaproverbs.models

import com.google.firebase.database.PropertyName

data class ProverbData (
    @PropertyName("Content")
    var content: String? = null,
    @PropertyName("Context")
    var context: String?= null,
    @PropertyName("Explanation")
    var explanation: String?= null,
    @PropertyName("Translation")
    var translation: String?= null
)