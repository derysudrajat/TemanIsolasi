package id.temanisolasi.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String? = null,
    val email: String? = null,
    var img: String? = null,
    val inIsolation: Boolean? = false,
    val updatedAt: Timestamp? = null,
    @DocumentId
    val id: String? = null,
) : Parcelable


fun User.mapped() = mapOf(
    "name" to this.name,
    "img" to this.img,
    "updatedAt" to Timestamp.now(),
)
