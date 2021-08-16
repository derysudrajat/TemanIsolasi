package id.temanisolasi.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.github.dhaval2404.imagepicker.ImagePicker
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
import java.util.concurrent.TimeUnit

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

    fun String.toTimeStamp(format: String): Timestamp {
        return Timestamp(SimpleDateFormat(format, Locale.getDefault()).parse(this))
    }

    fun Activity.showDatePicker(onDateResult: (date: String) -> Unit) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                val newDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                }
                onDateResult(
                    Timestamp(newDate.time).formatDate(DateFormat.SIMPLE) ?: ""
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    fun getPlaceHolder(name: String): String = "https://icotar.com/initials/$name.png?s=200"

    fun String.encodeName(): String = this.replace(" ", "%20")

    fun ActivityResult.handleImagePicker(
        context: Context,
        onResult: (Uri) -> Unit
    ) {
        when (this.resultCode) {
            Activity.RESULT_OK -> onResult(this.data?.data ?: "".toUri())
            ImagePicker.RESULT_ERROR -> showToast(
                context as Activity,
                ImagePicker.getError(this.data)
            )
            else -> showToast(context as Activity, "Batal")
        }
    }

    fun View.showView() {
        this.visibility = View.VISIBLE
    }

    fun View.hideView() {
        this.visibility = View.GONE
    }

    fun Timestamp.dayFrom(day: Timestamp): Long {
        val diff = day.toDate().time - this.toDate().time
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1
    }

    fun Timestamp.getHour(): Int {
        val cal = Calendar.getInstance().apply { time = this@getHour.toDate() }
        return cal.get(Calendar.HOUR_OF_DAY)
    }

    fun getStatus(time: String, stateTime: TIME): Int = when {
        time != "-" -> 0
        time == "-" && isInTime(stateTime) -> 1
        else -> 2
    }

    fun isInTime(stateTime: TIME): Boolean = when (stateTime) {
        TIME.DAY -> Timestamp.now().getHour() < 13
        TIME.NOON -> Timestamp.now().getHour() < 19
        TIME.NIGHT -> Timestamp.now().getHour() <= 24
    }

    fun getTimeStateNow(): TIME = when (Timestamp.now().getHour()) {
        in 7..12 -> TIME.DAY
        in 13..18 -> TIME.NOON
        in 19..24 -> TIME.NIGHT
        else -> TIME.DAY
    }

    fun Activity.isDarkMode(): Boolean =
        this.resources?.configuration?.uiMode
            ?.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
}