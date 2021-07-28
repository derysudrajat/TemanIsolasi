package id.temanisolasi.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.temanisolasi.R
import id.temanisolasi.utils.Helpers.hideKeyboard

class DialogHelpers(private val activity: Activity) {
    private val builder = MaterialAlertDialogBuilder(activity, R.style.MaterialAlertDialog_rounded)
    private var dialog: AlertDialog? = null

    fun init(layout: Int) {
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(layout, null))
            .setCancelable(false)
    }

    fun showDialog() {
        dialog = builder.create()
        dialog!!.show()
        activity.hideKeyboard()
    }

    fun hideDialog() = dialog!!.cancel()
}