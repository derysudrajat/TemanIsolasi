package id.temanisolasi.data.repo.remote.firebase.storage

import android.net.Uri
import id.temanisolasi.data.repo.State
import kotlinx.coroutines.flow.Flow

interface StorageUserDataStore {
    fun uploadPhoto(uri: Uri, path: String): Flow<State<String>>
    fun getImageUrl(path: String): Flow<State<Uri>>
}