package id.temanisolasi.data.repo.remote.firebase.firestore.user

import id.temanisolasi.data.model.User
import id.temanisolasi.data.repo.State
import kotlinx.coroutines.flow.Flow

interface FirestoreUserDataSource {
    fun createNewUser(user: User): Flow<State<Boolean>>
    fun getUserById(userId: String): Flow<State<User>>
    fun updateUserIsInIsolation(uid: String, inIsolation: Boolean): Flow<State<Boolean>>
    fun updateUser(user: User): Flow<State<Boolean>>
}