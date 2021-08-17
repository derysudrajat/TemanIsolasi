package id.temanisolasi.data.repo.remote.firebase.firestore.guide

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.temanisolasi.data.model.ContentGuide
import id.temanisolasi.data.repo.State
import id.temanisolasi.utils.COLLECTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
class FirestoreGuideRepository : FirestoreGuideDataStore {

    private val instanceStart = Firebase.firestore.collection(COLLECTION.GUIDE_START)
    private val instanceIn = Firebase.firestore.collection(COLLECTION.GUIDE_ISOLATION)
    private val instanceFinish = Firebase.firestore.collection(COLLECTION.GUIDE_FINISH)

    override fun getFirstIsolation() = callbackFlow<State<List<ContentGuide>>> {
        this.trySend(State.loading()).isSuccess
        instanceStart
            .orderBy("no", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(State.failed(error.message ?: "")).isSuccess
                    close(error)
                    return@addSnapshotListener
                }

                if (value != null && !value.isEmpty) value.toObjects(ContentGuide::class.java).let {
                    this.trySend(State.success(it)).isSuccess
                } else this.trySend(State.success(listOf())).isSuccess
            }
        awaitClose()
    }.catch {
        emit(State.failed(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    override fun getInIsolation() = callbackFlow<State<List<ContentGuide>>> {
        this.trySend(State.loading()).isSuccess
        instanceIn
            .orderBy("no", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(State.failed(error.message ?: "")).isSuccess
                    close(error)
                    return@addSnapshotListener
                }

                if (value != null && !value.isEmpty) value.toObjects(ContentGuide::class.java).let {
                    this.trySend(State.success(it)).isSuccess
                } else this.trySend(State.success(listOf())).isSuccess
            }
        awaitClose()
    }.catch {
        emit(State.failed(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    override fun getLastIsolation() = callbackFlow<State<List<ContentGuide>>> {
        this.trySend(State.loading()).isSuccess
        instanceFinish
            .orderBy("no", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(State.failed(error.message ?: "")).isSuccess
                    close(error)
                    return@addSnapshotListener
                }

                if (value != null && !value.isEmpty) value.toObjects(ContentGuide::class.java).let {
                    this.trySend(State.success(it)).isSuccess
                } else this.trySend(State.success(listOf())).isSuccess
            }
        awaitClose()
    }.catch {
        emit(State.failed(it.message ?: ""))
    }.flowOn(Dispatchers.IO)
}