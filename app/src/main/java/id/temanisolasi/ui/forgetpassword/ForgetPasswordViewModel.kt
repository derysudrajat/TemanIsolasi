package id.temanisolasi.ui.forgetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.textfield.TextInputEditText
import id.temanisolasi.data.model.User
import id.temanisolasi.data.repo.State
import id.temanisolasi.data.repo.remote.firebase.auth.AuthRepository
import id.temanisolasi.data.repo.remote.firebase.firestore.user.FirestoreUserRepository
import id.temanisolasi.utils.Helpers.afterTextChanged
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ForgetPasswordViewModel(
    private val userRepository: FirestoreUserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    fun onEmailAfterTextChange(textInputEditText: TextInputEditText) = viewModelScope.launch {
        textInputEditText.afterTextChanged { _email.value = it }
    }

    private var _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> get() = _progress
    private var currentProgress = 0

    fun setNewProgress(state: Boolean) = viewModelScope.launch {
        if (state) currentProgress += 1 else currentProgress -= 1
        _progress.value = currentProgress
    }

    private var _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun getUser(email: String) = viewModelScope.launch {
        userRepository.getUserByEmail(email).collect {
            if (it is State.Success) it.data.let { data -> _user.value = data }
        }
    }

    private var _isEmailSent = MutableLiveData<Boolean>()
    val isEmailSent: LiveData<Boolean> get() = _isEmailSent

    fun sentEmailVerification(email: String) = viewModelScope.launch {
        authRepository.forgotPassword(email).collect {
            _isEmailSent.value = it is State.Success
        }
    }
}