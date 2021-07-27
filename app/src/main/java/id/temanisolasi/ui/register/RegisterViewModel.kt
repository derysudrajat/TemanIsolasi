package id.temanisolasi.ui.register

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.temanisolasi.data.model.User
import id.temanisolasi.data.repo.State
import id.temanisolasi.data.repo.remote.firebase.auth.AuthRepository
import id.temanisolasi.data.repo.remote.firebase.firestore.FirestoreUserRepository
import id.temanisolasi.utils.Helpers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: FirestoreUserRepository
) : ViewModel() {

    private var _isComplete = MutableLiveData<Boolean>()
    private val currentProgress = mutableListOf(false, false, false, false)
    val isComplete: LiveData<Boolean> get() = _isComplete

    fun setProgress(index: Int, state: Boolean) = viewModelScope.launch {
        currentProgress[index] = state
        _isComplete.value = currentProgress.count { it } == currentProgress.size
    }

    fun createAccount(
        user: User, password: String, activity: Activity, isSuccess: () -> Unit
    ) = viewModelScope.launch {
        user.email?.let { mail ->
            authRepository.signUpWithEmail(mail, password).collect {
                when (it) {
                    is State.Loading -> {
                    }
                    is State.Success -> it.data.let { map ->
                        Log.d(
                            "TAG",
                            "isSuccess ${map.keys}: ${map.values}, id = ${Firebase.auth.currentUser?.uid}"
                        )
                        recordDataUser(user, activity) { isSuccess() }
                    }
                    is State.Failed -> {
//                        DialogHelpers.hideLoadingDialog()
                        Helpers.showToast(activity, it.message)
                    }
                }
            }
        }

    }

    fun recordDataUser(user: User, activity: Activity, isSuccess: () -> Unit) =
        viewModelScope.launch {
            userRepository.createNewUser(user).collect {
                when (it) {
                    is State.Loading -> Log.d("TAG", "loading: true")
                    is State.Success -> {
//                        DialogHelpers.hideLoadingDialog()
                        isSuccess()
                    }
                    is State.Failed -> {
//                        DialogHelpers.hideLoadingDialog()
                        Helpers.showToast(activity, it.message)
                    }
                }
            }
        }

}