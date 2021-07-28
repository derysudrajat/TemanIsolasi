package id.temanisolasi.ui.register

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.temanisolasi.R
import id.temanisolasi.data.model.User
import id.temanisolasi.data.repo.State
import id.temanisolasi.data.repo.remote.firebase.auth.AuthRepository
import id.temanisolasi.data.repo.remote.firebase.firestore.FirestoreUserRepository
import id.temanisolasi.utils.DialogHelpers
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
        val dialog = DialogHelpers(activity)
        dialog.init(R.layout.dialog_base)
        user.email?.let { mail ->
            authRepository.signUpWithEmail(mail, password).collect {
                when (it) {
                    is State.Loading -> dialog.showDialog()
                    is State.Success -> it.data.let { map ->
                        Log.d(
                            "TAG",
                            "isSuccess ${map.keys}: ${map.values}, id = ${Firebase.auth.currentUser?.uid}"
                        )
                        recordDataUser(user, activity, dialog) { isSuccess() }
                    }
                    is State.Failed -> {
                        dialog.hideDialog()
                        Helpers.showToast(activity, it.message)
                    }
                }
            }
        }

    }

    fun recordDataUser(
        user: User,
        activity: Activity,
        dialog: DialogHelpers? = null,
        isSuccess: () -> Unit
    ) = viewModelScope.launch {
        val mDialog: DialogHelpers?
        if (dialog == null) {
            mDialog = DialogHelpers(activity)
            mDialog.init(R.layout.dialog_base)
        } else mDialog = dialog
        userRepository.createNewUser(user).collect {
            when (it) {
                is State.Loading -> {
                    if (dialog == null) mDialog.showDialog()
                    Log.d("TAG", "loading: true")
                }
                is State.Success -> {
                    mDialog.hideDialog()
                    isSuccess()
                }
                is State.Failed -> {
                    mDialog.hideDialog()
                    Helpers.showToast(activity, it.message)
                }
            }
        }
    }

}