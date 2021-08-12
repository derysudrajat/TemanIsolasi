package id.temanisolasi.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.temanisolasi.R
import id.temanisolasi.utils.Helpers.hideKeyboard

class DialogHelpers(private val activity: Activity) {
    private lateinit var builder: MaterialAlertDialogBuilder
    private var dialog: AlertDialog? = null

    fun init(layout: Int, theme: Int? = 0) {
        val inflater = activity.layoutInflater
        builder = MaterialAlertDialogBuilder(
            activity,
            if (theme == 0) R.style.MaterialAlertDialog_rounded else R.style.MaterialAlertDialog_Transparent
        )
        builder.setView(inflater.inflate(layout, null))
            .setCancelable(false)
        dialog = builder.create()
    }

    fun showDialog() {
        dialog!!.show()
        activity.hideKeyboard()
    }

    fun hideDialog() = dialog!!.cancel()
}