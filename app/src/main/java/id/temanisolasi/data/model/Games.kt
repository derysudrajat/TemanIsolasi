package id.temanisolasi.data.model

data class Games(
    val games: String? = null,
    val fav: Int? = -1,
    val type: Int? = 0,
    val url: String? = null,
    val button: String? = null,
)

data class FavGames(
    val games: String? = null,
    val fav: Int? = 0,
    val img: String? = null
)
