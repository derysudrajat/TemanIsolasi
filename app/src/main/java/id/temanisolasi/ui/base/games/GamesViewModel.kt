package id.temanisolasi.ui.base.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.temanisolasi.data.model.FavGames
import id.temanisolasi.data.model.Games
import id.temanisolasi.data.repo.State
import id.temanisolasi.data.repo.remote.firebase.firestore.game.FirestoreGamesRepository
import id.temanisolasi.utils.DataHelpers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GamesViewModel(
    private val repository: FirestoreGamesRepository
) : ViewModel() {

    private val _favGames = MutableLiveData<List<FavGames>>()
    val favGames: LiveData<List<FavGames>> get() = _favGames

    fun getFavoriteGames() = viewModelScope.launch {
        repository.getFavoriteGames().collect {
            if (it is State.Success) it.data.let { data ->
                _favGames.value = if (data.isNotEmpty()) data else DataHelpers.listOfFavGames
            }
        }
    }

    fun getRandomGames(fav: Int? = -1, result: (Games) -> Unit) = viewModelScope.launch {
        repository.getAllGames(fav).collect {
            if (it is State.Success) it.data.let { data ->
                val games = (if (data.isNotEmpty()) data else DataHelpers.listOfGames).filter {
                    if (fav != -1) it.fav == fav else it.fav != null
                }.shuffled().take(1)[0]
                result(games)
            }
        }
    }
}