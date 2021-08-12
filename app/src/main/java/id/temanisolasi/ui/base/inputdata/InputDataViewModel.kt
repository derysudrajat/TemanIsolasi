package id.temanisolasi.ui.base.inputdata

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.temanisolasi.R
import id.temanisolasi.data.model.Report
import id.temanisolasi.data.repo.State
import id.temanisolasi.data.repo.remote.firebase.firestore.isolation.FirestoreIsolationRepository
import id.temanisolasi.utils.DialogHelpers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class InputDataViewModel(
    private val isolationRepository: FirestoreIsolationRepository
) : ViewModel() {

    fun updateDataIsolation(
        id: String, reports: MutableList<Report>, activity: Activity,
        isSuccess: (Boolean) -> Unit
    ) = viewModelScope.launch {
        isolationRepository.updateIsolationData(id, reports).collect {
            val dialog = DialogHelpers(activity).apply {
                init(R.layout.dialog_base)
            }
            when (it) {
                is State.Loading -> dialog.showDialog()
                is State.Success -> {
                    dialog.hideDialog()
                    isSuccess(it.data)
                }
                is State.Failed -> {
                    dialog.hideDialog()
                    Log.d("TAG", "updateDataIsolation: failed = ${it.message}")
                }
            }
        }
    }
}