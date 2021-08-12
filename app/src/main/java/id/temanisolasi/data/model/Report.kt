package id.temanisolasi.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Report(
    var temperature: Float? = 0.0f,
    var saturationOxygen: Float? = 0.0f,
    var medicine: MutableList<String>? = mutableListOf("-", "-", "-"),
    var task: MutableList<Boolean>? = mutableListOf(false, false, false, false),
    var day: Int? = 1,
    var date: Timestamp? = Timestamp.now()
) : Parcelable