package id.temanisolasi.utils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Timestamp
import id.temanisolasi.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*

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
        R.drawable.ic_temp,
        R.drawable.ic_oxy,
        R.drawable.ic_medince,
        R.drawable.ic_task,
    )

    val dummyTemp = mutableListOf(
        38.9f, 38.2f, 37.6f, 37.9f, 36.7f, 37.2f, 36.3f,
        36.1f, 36.2f, 36.4f, 36.2f, 36.1f, 36.3f
    )

    val dummySaturation = mutableListOf(
        85.0f, 86.0f, 85.0f, 87.0f, 89.0f, 90.0f, 91.0f, 92.0f, 93.0f, 92.0f, 94.0f, 95.0f, 96.0f
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
        for (layout in inputLayouts) layout.apply {
            isErrorEnabled = false
            boxStrokeColor = ContextCompat.getColor(context, R.color.white)
            boxBackgroundColor = ContextCompat.getColor(context, R.color.white)
            setErrorTextColor(ContextCompat.getColorStateList(context, R.color.white))
        }
    }

    fun TextInputLayout.showError(message: String? = "Tidak boleh kosong") {
        this.apply {
            error = message
            isErrorEnabled = true
            boxStrokeColor = ContextCompat.getColor(context, R.color.error)
            boxBackgroundColor = ContextCompat.getColor(context, R.color.error)
            setErrorTextColor(ContextCompat.getColorStateList(context, R.color.error))
        }
    }

    fun showToast(activity: Activity, message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun Activity.hideKeyboard() = this.currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun Timestamp.formatDate(format: String): String? {
        return SimpleDateFormat(format, Locale.getDefault()).format(this.toDate().time)
    }
}