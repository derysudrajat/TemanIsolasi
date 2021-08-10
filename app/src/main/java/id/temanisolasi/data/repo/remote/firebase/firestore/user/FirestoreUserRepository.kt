package id.temanisolasi.data.repo.remote.firebase.firestore.user

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.temanisolasi.data.model.User
import id.temanisolasi.data.model.mapped
import id.temanisolasi.data.repo.State
import id.temanisolasi.utils.COLLECTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

class FirestoreUserRepository : FirestoreUserDataSource {

    private val instance = Firebase.firestore.collection(COLLECTION.USER)

    override fun createNewUser(user: User) = flow {
        emit(State.loading())
        val id = Firebase.auth.currentUser?.uid ?: ""
        val snapshot = instance.document(id).set(user)
        snapshot.await()
        if (snapshot.isSuccessful) emit(State.success(true))
    }.catch {
        emit(State.failed(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    @ExperimentalCoroutinesApi
    override fun getUserById(userId: String) = callbackFlow<State<User>> {
        this.trySend(State.loading()).isSuccess
        instance.document(userId).addSnapshotListener { value, error ->
            if (error != null) {
                trySend(State.failed(error.message ?: "")).isSuccess
                close(error)
                return@addSnapshotListener
            }

            if (value != null && value.exists()) value.toObject(User::class.java).let {
                this.trySend(State.success(it ?: User())).isSuccess
            } else this.trySend(State.success(User())).isSuccess
        }
        awaitClose()
    }.catch {
        emit(State.failed(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    override fun updateUserIsInIsolation(uid: String, inIsolation: Boolean) = flow {
        emit(State.loading())
        val snapshot = instance.document(uid).update("inIsolation", inIsolation)
        snapshot.await()
        if (snapshot.isSuccessful) emit(State.success(true))
    }.catch {
        emit(State.failed(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    override fun updateUser(user: User) = flow {
        emit(State.loading())
        val snapshot = instance.document(user.id ?: "")
            .update(user.mapped())
        snapshot.await()
        if (snapshot.isSuccessful) emit(State.success(true))
    }.catch {
        emit(State.failed(it.message ?: ""))
    }.flowOn(Dispatchers.IO)
}