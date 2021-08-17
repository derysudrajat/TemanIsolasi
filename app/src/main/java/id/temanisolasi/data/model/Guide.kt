package id.temanisolasi.data.model

data class Guide(
    val img: String,
    val text: String
)

data class Question(
    val question: String? = null,
    val answer: String? = null,
    val type: Int? = null,
    var isExpanded: Boolean? = false
)
