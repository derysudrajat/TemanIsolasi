package id.temanisolasi.data.model

data class Guide(
    val img: String,
    val text: String
)

data class Question(
    val question: String,
    val answer: String,
    var isExpanded: Boolean? = false
)
