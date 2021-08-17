package id.temanisolasi.ui.startisolation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.temanisolasi.data.model.Isolation
import kotlinx.coroutines.launch

class StartIsolationViewModel : ViewModel() {

    private val isComplete = mutableListOf(false, false, false)
    private val _progress = MutableLiveData<List<Boolean>>()
    val progress: LiveData<List<Boolean>> get() = _progress

    fun setComplete(index: Int, state: Boolean) {
        isComplete[index] = state
        _progress.value = isComplete
    }

    fun getProgress(): List<Boolean> = isComplete


    private val personalData = mutableListOf(false, false, false, false)
    private val _progressPersonal = MutableLiveData<List<Boolean>>()
    val progressPersonal: LiveData<List<Boolean>> get() = _progressPersonal

    fun setProgressPersonal(index: Int, state: Boolean) = viewModelScope.launch {
        personalData[index] = state
        _progressPersonal.value = personalData
    }

    private val isolationData = mutableListOf(false, false, false, false, false)
    private val _progressIsolation = MutableLiveData<List<Boolean>>()
    val progressIsolation: LiveData<List<Boolean>> get() = _progressIsolation

    fun setProgressIsolation(index: Int, state: Boolean) = viewModelScope.launch {
        isolationData[index] = state
        _progressIsolation.value = isolationData
    }

    private val currentIsolation = Isolation()
    fun setDataFromPersonal(isolation: Isolation) {
        currentIsolation.apply {
            name = isolation.name
            gender = isolation.gender
            dateBirt = isolation.dateBirt
            address = isolation.address
        }
    }

    fun setDataFromIsolation(isolation: Isolation) {
        val id = Firebase.auth.currentUser?.uid ?: ""
        currentIsolation.apply {
            startIsolation = isolation.startIsolation
            bloodType = isolation.bloodType
            weight = isolation.weight
            vaccinated = isolation.vaccinated
            symptom = isolation.symptom
            passedDay = isolation.passedDay
            listReport = isolation.listReport
            userId = id
        }
    }

    fun getDataIsolation(): Isolation = currentIsolation
}