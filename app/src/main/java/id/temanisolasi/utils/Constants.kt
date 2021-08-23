package id.temanisolasi.utils

object COLLECTION {
    const val CREDITS = "credits"
    const val GUIDE_START = "guide-start"
    const val GUIDE_ISOLATION = "guide-isolation"
    const val GUIDE_FINISH = "guide-finish"
    const val GAME = "games"
    const val FAV_GAME = "fav_games"
    const val USER = "user"
    const val ISOLATION = "isolation"
}

enum class LoginState {
    SUCCESS, USER_NOT_FOUND, WRONG_PASSWORD
}

object DateFormat {
    const val SHORT = "dd MMMM yyyy"
    const val SIMPLE = "dd/MM/yyyy"
}

object STORAGE {
    private const val PATH_AVA = "AVA_"

    private const val ROOT_AVA = "photos/ava/"

    fun String.getAvaPath(): String = "$PATH_AVA$this"

    fun String.getAvaLocation(pathAva: String): String = buildString {
        append(ROOT_AVA).append(this@getAvaLocation).append("/").append(pathAva)
    }
}

enum class TIME { DAY, NOON, NIGHT }