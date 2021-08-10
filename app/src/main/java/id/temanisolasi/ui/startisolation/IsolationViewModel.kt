package id.temanisolasi.ui.startisolation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.temanisolasi.data.model.Isolation
import id.temanisolasi.data.repo.State
import id.temanisolasi.data.repo.remote.firebase.firestore.isolation.FirestoreIsolationRepository
import id.temanisolasi.data.repo.remote.firebase.firestore.user.FirestoreUserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class IsolationViewModel(
    private val isolationRepository: FirestoreIsolationRepository,
    private val userRepository: FirestoreUserRepository
) : ViewModel() {

    fun addNewDataIsolation(isolation: Isolation) = viewModelScope.launch {
        val id = Firebase.auth.currentUser?.uid ?: ""
        isolationRepository.addDataIsolation(isolation).collect()
        userRepository.updateUserIsInIsolation(id, true).collect()
    }

    private val _isolation = MutableLiveData<List<Isolation>>()
    val isolation: LiveData<List<Isolation>> get() = _isolation

    fun getDataIsolation() = viewModelScope.launch {
        val id = Firebase.auth.currentUser?.uid ?: ""
        isolationRepository.getDataIsolation(id).collect {
            if (it is State.Success) it.data.let { data -> _isolation.value = data }
        }
    }
}