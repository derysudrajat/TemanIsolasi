package id.temanisolasi.utils

object COLLECTION {
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