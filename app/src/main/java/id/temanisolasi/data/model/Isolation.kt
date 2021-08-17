package id.temanisolasi.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import id.temanisolasi.utils.DateFormat
import id.temanisolasi.utils.Helpers.formatDate
import kotlinx.parcelize.Parcelize

@Parcelize
data class Isolation(
    var name: String? = null,
    var gender: Int? = null,
    var dateBirt: Timestamp? = null,
    var address: String? = null,
    var startIsolation: Timestamp? = null,
    var bloodType: String? = null,
    var weight: Int? = null,
    var vaccinated: Int? = null,
    var symptom: Int? = null,
    var passedDay: Int? = 1,
    var active: Boolean? = true,
    var listReport: MutableList<Report>? = mutableListOf(Report()),
    var userId: String? = null,
    @DocumentId
    val id: String? = null,
) : Parcelable

fun getEmptyReport(day: Int): MutableList<Report> {
    val listReport = mutableListOf<Report>()
    repeat(day) { listReport.add(Report()) }
    return listReport
}

fun Isolation.toList(): List<String> {
    return listOf(
        this.name ?: "-",
        if (this.gender == 0) "Pria" else "Wanita",
        this.dateBirt?.formatDate(DateFormat.SIMPLE) ?: "-",
        this.address ?: "-",
        this.startIsolation?.formatDate(DateFormat.SIMPLE) ?: "-",
        this.bloodType ?: "-",
        "${this.weight} Kg",
        if (this.vaccinated == 1) "Sudah" else "Belum",
        if (this.symptom == 0) "Tidak Bergejala" else "Bergejala Ringan"
    )
}

