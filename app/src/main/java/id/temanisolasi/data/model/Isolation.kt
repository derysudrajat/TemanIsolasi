package id.temanisolasi.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import id.temanisolasi.utils.DateFormat
import id.temanisolasi.utils.Helpers.formatDate

data class Isolation(
    var name: String? = null,
    var gender: Int? = null,
    var dateBirt: Timestamp? = null,
    var address: String? = null,
    var startIsolation: Timestamp? = null,
    var bloodType: String? = null,
    var weight: Int? = null,
    var vaccinated: Int? = null,
    var userId: String? = null,
    @DocumentId
    val id: String? = null,
)

fun Isolation.toList(): List<String> {
    return listOf(
        this.name ?: "-",
        if (this.gender == 0) "Pria" else "Wanita",
        this.dateBirt?.formatDate(DateFormat.SIMPLE) ?: "-",
        this.address ?: "-",
        this.startIsolation?.formatDate(DateFormat.SIMPLE) ?: "-",
        this.bloodType ?: "-",
        "${this.weight} Kg",
        if (this.vaccinated == 1) "Sudah" else "Belum"
    )
}

