package id.temanisolasi.ui.base.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.temanisolasi.data.model.User
import id.temanisolasi.data.repo.State
import id.temanisolasi.data.repo.remote.firebase.firestore.user.FirestoreUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: FirestoreUserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    @ExperimentalCoroutinesApi
    fun getUser() = viewModelScope.launch {
        val id = Firebase.auth.currentUser?.uid ?: ""
        userRepository.getUserById(id).collect {
            if (it is State.Success) it.data.let { data -> _user.value = data }
        }
    }
}