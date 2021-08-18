package id.temanisolasi.data.repo.remote.firebase.firestore.game

import id.temanisolasi.data.model.FavGames
import id.temanisolasi.data.model.Games
import id.temanisolasi.data.repo.State
import kotlinx.coroutines.flow.Flow

interface FirestoreGamesDataStore {

    fun getFavoriteGames(): Flow<State<List<FavGames>>>
    fun getAllGames(fav: Int? = -1): Flow<State<List<Games>>>
}