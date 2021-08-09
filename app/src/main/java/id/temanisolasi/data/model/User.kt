package id.temanisolasi.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String? = null,
    val email: String? = null,
    val img: String? = null,
    val inIsolation: Boolean? = false,
    @DocumentId
    val id: String? = null,
) : Parcelable
