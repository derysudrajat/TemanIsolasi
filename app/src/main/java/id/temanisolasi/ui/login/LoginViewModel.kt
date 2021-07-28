package id.temanisolasi.ui.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.temanisolasi.R
import id.temanisolasi.data.model.User
import id.temanisolasi.data.repo.State
import id.temanisolasi.data.repo.remote.firebase.auth.AuthRepository
import id.temanisolasi.data.repo.remote.firebase.firestore.FirestoreUserRepository
import id.temanisolasi.ui.base.MainActivity
import id.temanisolasi.ui.register.RegisterActivity
import id.temanisolasi.utils.DataHelpers
import id.temanisolasi.utils.DialogHelpers
import id.temanisolasi.utils.LoginState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: FirestoreUserRepository
) : ViewModel() {

    private var _isComplete = MutableLiveData<Boolean>()
    private val currentProgress = mutableListOf(false, false)
    val isComplete: LiveData<Boolean> get() = _isComplete

    private val _isLoginSuccess = MutableLiveData<LoginState>()
    val isLoginSuccess: LiveData<LoginState> get() = _isLoginSuccess

    fun setProgress(index: Int, state: Boolean) = viewModelScope.launch {
        currentProgress[index] = state
        _isComplete.value = currentProgress.count { it } == currentProgress.size
    }

    @ExperimentalCoroutinesApi
    fun loginWithGoogle(account: GoogleSignInAccount, activity: Activity) = viewModelScope.launch {
        val dialog = DialogHelpers(activity)
        dialog.init(R.layout.dialog_base)
        account.idToken?.let { token ->
            authRepository.loginWithGoogle(token, activity).collect {
                when (it) {
                    is State.Loading -> dialog.showDialog()
                    is State.Success -> checkAccountWasExist(activity, account)
                    is State.Failed -> Log.d("TAG", "googleLoginWithGoogle: failed = ${it.message}")
                }
            }
        }

    }

    @ExperimentalCoroutinesApi
    private fun checkAccountWasExist(activity: Activity, account: GoogleSignInAccount) =
        viewModelScope.launch {
            val id = Firebase.auth.currentUser?.uid ?: ""
            userRepository.getUserById(id).collect {
                when (it) {
                    is State.Loading -> {
                        Log.d("TAG", "checkAccountWasExist: loading")
                    }
                    is State.Success -> {
//                        DialogHelpers.hideLoadingDialog()
                        it.data.let { user ->
                            if (user.name != null) with(activity) {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else with(activity) {
                                startActivity(Intent(
                                    this, RegisterActivity::class.java
                                ).apply {
                                    putExtra(
                                        RegisterActivity.EXTRA_CREATE,
                                        User(name = account.displayName, email = account.email)
                                    )
                                })
                                finish()
                            }
                        }
                    }
                    is State.Failed -> {
//                        DialogHelpers.hideLoadingDialog()
                        Log.d("TAG", "checkAccountWasExist: failed = ${it.message}")
                    }
                }
            }
        }

    fun loginWithEmailPassword(email: String, password: String, activity: Activity) =
        viewModelScope.launch {
            val dialog = DialogHelpers(activity)
            dialog.init(R.layout.dialog_base)
            authRepository.loginWithEmail(email, password).collect {
                when (it) {
                    is State.Loading -> dialog.showDialog()
                    is State.Success -> {
                        dialog.hideDialog()
                        _isLoginSuccess.value = LoginState.SUCCESS
                    }
                    is State.Failed -> {
                        dialog.hideDialog()
                        Log.d("TAG", "login: failed = ${it.message}")
                        DataHelpers.errorLoginMessage.let { msg ->
                            val isFirst = msg[msg.keys.first()] == it.message
                            _isLoginSuccess.value =
                                if (isFirst) LoginState.USER_NOT_FOUND else LoginState.WRONG_PASSWORD
                        }
                    }
                }
            }
        }
}