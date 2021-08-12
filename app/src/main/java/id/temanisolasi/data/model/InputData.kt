package id.temanisolasi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputData(
    val img: Int,
    val text: String,
    val target: Int,
) : Parcelable
