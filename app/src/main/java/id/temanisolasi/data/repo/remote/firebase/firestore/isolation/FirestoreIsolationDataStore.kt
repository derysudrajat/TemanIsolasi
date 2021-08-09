package id.temanisolasi.data.repo.remote.firebase.firestore.isolation

import id.temanisolasi.data.model.Isolation
import id.temanisolasi.data.repo.State
import kotlinx.coroutines.flow.Flow

sealed interface FirestoreIsolationDataStore {

    fun addDataIsolation(isolation: Isolation): Flow<State<Boolean>>
    fun getDataIsolation(uid: String): Flow<State<List<Isolation>>>

}