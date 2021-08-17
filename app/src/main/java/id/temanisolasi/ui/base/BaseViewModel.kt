package id.temanisolasi.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.temanisolasi.data.model.Isolation
import id.temanisolasi.data.repo.State
import id.temanisolasi.data.repo.remote.firebase.firestore.isolation.FirestoreIsolationRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BaseViewModel(
    private val isolationRepository: FirestoreIsolationRepository
) : ViewModel() {

    private val _activeIsolation = MutableLiveData<Isolation>()
    val activeIsolation: LiveData<Isolation> get() = _activeIsolation

    fun getActiveIsolationData() = viewModelScope.launch {
        val id = Firebase.auth.currentUser?.uid ?: ""
        isolationRepository.getActiveIsolation(id).collect {
            if (it is State.Success) it.data.let { data -> _activeIsolation.value = data }
        }
    }

    fun addNewReport(id: String, newPassedDay: Int, isFinish: () -> Unit) = viewModelScope.launch {
        isolationRepository.postNewReport(id, newPassedDay).collect {
            if (it is State.Success) isFinish()
        }
    }
}