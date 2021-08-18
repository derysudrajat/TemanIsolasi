package id.temanisolasi.data.repo.remote.firebase.firestore.game

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.temanisolasi.data.model.FavGames
import id.temanisolasi.data.model.Games
import id.temanisolasi.data.repo.State
import id.temanisolasi.utils.COLLECTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

class FirestoreGamesRepository : FirestoreGamesDataStore {

    private val instance = Firebase.firestore.collection(COLLECTION.GAME)
    private val instanceFav = Firebase.firestore.collection(COLLECTION.FAV_GAME)

    @ExperimentalCoroutinesApi
    override fun getFavoriteGames() = callbackFlow<State<List<FavGames>>> {
        this.trySend(State.loading()).isSuccess
        instanceFav
            .orderBy("no", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(State.failed(error.message ?: "")).isSuccess
                    close(error)
                    return@addSnapshotListener
                }

                if (value != null && !value.isEmpty) value.toObjects(FavGames::class.java).let {
                    this.trySend(State.success(it)).isSuccess
                } else this.trySend(State.success(listOf())).isSuccess
            }
        awaitClose()
    }.catch {
        emit(State.failed(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    @ExperimentalCoroutinesApi
    override fun getAllGames(fav: Int?) = callbackFlow<State<List<Games>>> {
        this.trySend(State.loading()).isSuccess
        if (fav != -1) instance.whereEqualTo("fav", fav)
        instance.addSnapshotListener { value, error ->
            if (error != null) {
                trySend(State.failed(error.message ?: "")).isSuccess
                close(error)
                return@addSnapshotListener
            }

            if (value != null && !value.isEmpty) value.toObjects(Games::class.java).let {
                this.trySend(State.success(it)).isSuccess
            } else this.trySend(State.success(listOf())).isSuccess
        }
        awaitClose()
    }.catch {
        emit(State.failed(it.message ?: ""))
    }.flowOn(Dispatchers.IO)
}