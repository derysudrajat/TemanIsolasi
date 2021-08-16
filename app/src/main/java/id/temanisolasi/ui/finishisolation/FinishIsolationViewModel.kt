package id.temanisolasi.ui.finishisolation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.temanisolasi.data.repo.remote.firebase.firestore.isolation.FirestoreIsolationRepository
import id.temanisolasi.data.repo.remote.firebase.firestore.user.FirestoreUserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FinishIsolationViewModel(
    private val userRepository: FirestoreUserRepository,
    private val isolationRepository: FirestoreIsolationRepository
) : ViewModel() {

    fun makeIsolationFinish(docId: String) = viewModelScope.launch {
        val uid = Firebase.auth.currentUser?.uid ?: ""
        userRepository.updateUserIsInIsolation(uid, false).collect()
        isolationRepository.updateStatusIsolation(docId, false).collect()
    }
}