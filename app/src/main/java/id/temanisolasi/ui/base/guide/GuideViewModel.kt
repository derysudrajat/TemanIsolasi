package id.temanisolasi.ui.base.guide

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.temanisolasi.data.model.Question
import kotlinx.coroutines.launch

class GuideViewModel : ViewModel() {

    private var _question = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> get() = _question

    fun getAllQuestion() = viewModelScope.launch {
        Firebase.firestore.collection("question")
            .orderBy("no", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.d("TAG", "getAllQuestion: error = ${error.message}")
                    return@addSnapshotListener
                }

                if (value != null && !value.isEmpty) _question.value =
                    value.toObjects(Question::class.java)
                else _question.value = listOf()
            }
    }

}