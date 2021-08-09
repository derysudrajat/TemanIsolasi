package id.temanisolasi.data.repo.remote.firebase.firestore.isolation

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.temanisolasi.data.model.Isolation
import id.temanisolasi.data.repo.State
import id.temanisolasi.utils.COLLECTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

class FirestoreIsolationRepository : FirestoreIsolationDataStore {

    private val instance = Firebase.firestore.collection(COLLECTION.ISOLATION)

    override fun addDataIsolation(isolation: Isolation) = flow {
        emit(State.loading())
        val snapshot = instance.document().set(isolation)
        snapshot.await()
        if (snapshot.isSuccessful) emit(State.success(true))
    }.catch {
        emit(State.failed(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    @ExperimentalCoroutinesApi
    override fun getDataIsolation(uid: String) = callbackFlow<State<List<Isolation>>> {
        this.trySend(State.loading()).isSuccess
        instance.whereEqualTo("userId", uid).addSnapshotListener { value, error ->
            if (error != null) {
                trySend(State.failed(error.message ?: "")).isSuccess
                close(error)
                return@addSnapshotListener
            }

            if (value != null && !value.isEmpty) value.toObjects(Isolation::class.java).let {
                this.trySend(State.success(it)).isSuccess
            } else this.trySend(State.success(listOf())).isSuccess
        }
        awaitClose()
    }.catch {
        emit(State.failed(it.message ?: ""))
    }.flowOn(Dispatchers.IO)
}