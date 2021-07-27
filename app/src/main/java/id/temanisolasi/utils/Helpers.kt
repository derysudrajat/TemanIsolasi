package id.temanisolasi.utils

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.textfield.TextInputLayout
import id.temanisolasi.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

object Helpers {

    fun BottomAppBar.setRounded() {
        val background = this.background as MaterialShapeDrawable
        background.shapeAppearanceModel = background.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, 50f)
            .setTopLeftCorner(CornerFamily.ROUNDED, 50f)
            .build()
    }

    val dummyIconHome = listOf(
        R.drawable.ic_home,
        R.drawable.ic_guide,
        R.drawable.ic_explore,
        R.drawable.ic_person,
    )

    val dummyTemp = mutableListOf(
        38.9f, 38.2f, 37.6f, 37.9f, 36.7f, 37.2f, 36.3f,
        36.1f, 36.2f, 36.4f, 36.2f, 36.1f, 36.3f
    )

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @FlowPreview
    suspend fun EditText.afterTextChanged(afterTextChanged: suspend (String) -> Unit) {
        val watcher = object : TextWatcher {

            private val channel = BroadcastChannel<String>(Channel.CONFLATED)

            override fun afterTextChanged(editable: Editable?) {
                channel.trySend(editable.toString()).isSuccess
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            fun asFlow(): Flow<String> = channel.asFlow()
        }

        this.addTextChangedListener(watcher)

        watcher.asFlow()
            .debounce(500)
            .distinctUntilChanged()
            .collect { afterTextChanged(it) }
    }

    fun EditText.getPlainText() = this.text.toString()

    fun validateError(vararg inputLayouts: TextInputLayout) {
        for (layout in inputLayouts) layout.isErrorEnabled = false
    }

    fun TextInputLayout.showError(message: String? = "Tidak boleh kosong") {
        this.apply {
            error = message
            isErrorEnabled = true
        }
    }

    fun showToast(activity: Activity, message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}