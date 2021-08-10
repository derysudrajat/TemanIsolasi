package id.temanisolasi.ui.base.profile.editprofile

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.temanisolasi.R
import id.temanisolasi.data.model.User
import id.temanisolasi.data.repo.State
import id.temanisolasi.data.repo.remote.firebase.firestore.user.FirestoreUserRepository
import id.temanisolasi.data.repo.remote.firebase.storage.StorageUserRepository
import id.temanisolasi.utils.DialogHelpers
import id.temanisolasi.utils.STORAGE.getAvaLocation
import id.temanisolasi.utils.STORAGE.getAvaPath
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val userRepository: FirestoreUserRepository,
    private val storageUserRepository: StorageUserRepository
) : ViewModel() {

    fun updateData(activity: Activity, user: User, isUpdatePhoto: Boolean) = viewModelScope.launch {
        val uid = user.id ?: ""
        val userPath = uid.getAvaPath()
        if (isUpdatePhoto) {
            updatePhoto(user.img?.toUri() ?: "".toUri(), uid, userPath) {
                user.img = userPath
                updateUser(activity, user)
            }
        } else updateUser(activity, user)
    }

    private fun updateUser(activity: Activity, user: User) = viewModelScope.launch {

        val dialog = DialogHelpers(activity).apply {
            init(R.layout.dialog_base)
        }

        userRepository.updateUser(user).collect {
            when (it) {
                is State.Loading -> dialog.showDialog()
                is State.Success -> {
                    dialog.hideDialog()
                    Toast.makeText(activity, "Berhasil Diperbaharui", Toast.LENGTH_SHORT).show()
                    activity.finish()
                }
                is State.Failed -> {
                    dialog.hideDialog()
                    Log.d("TAG", "updateUser: failed = ${it.message}")
                }
            }
        }
    }

    fun updatePhoto(photoUri: Uri, uid: String, path: String, isSuccess: (Boolean) -> Unit) =
        viewModelScope.launch {
            storageUserRepository.uploadPhoto(
                photoUri, uid.getAvaLocation(path)
            ).collect { if (it !is State.Loading) isSuccess(it is State.Success) }
        }

    fun getImgUrl(uid: String, img: String, isSuccess: (Uri) -> Unit) = viewModelScope.launch {
        storageUserRepository.getImageUrl(uid.getAvaLocation(img)).collect {
            if (it is State.Success) isSuccess(it.data)
        }
    }
}