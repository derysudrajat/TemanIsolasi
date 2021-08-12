package id.temanisolasi.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.temanisolasi.data.model.Isolation

class BaseSharedViewModel : ViewModel() {

    private val _activeIsolation = MutableLiveData<Isolation>()
    val activeIsolation: LiveData<Isolation> get() = _activeIsolation

    fun setDataActiveIsolation(isolation: Isolation) {
        _activeIsolation.value = isolation
    }
}