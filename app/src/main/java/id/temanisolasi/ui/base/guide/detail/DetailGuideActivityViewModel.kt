package id.temanisolasi.ui.base.guide.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.temanisolasi.data.model.ContentGuide
import id.temanisolasi.data.repo.State
import id.temanisolasi.data.repo.remote.firebase.firestore.guide.FirestoreGuideRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailGuideActivityViewModel(
    private val guideRepository: FirestoreGuideRepository
) : ViewModel() {

    private var _guides = MutableLiveData<List<ContentGuide>>()
    val guides: LiveData<List<ContentGuide>> get() = _guides

    fun getDataStart() = viewModelScope.launch {
        guideRepository.getFirstIsolation().collect {
            if (it is State.Success) it.data.let { data -> _guides.value = data }
        }
    }

    fun getDataIn() = viewModelScope.launch {
        guideRepository.getInIsolation().collect {
            if (it is State.Success) it.data.let { data -> _guides.value = data }
        }
    }

    fun getDataFinish() = viewModelScope.launch {
        guideRepository.getLastIsolation().collect {
            if (it is State.Success) it.data.let { data -> _guides.value = data }
        }
    }
}