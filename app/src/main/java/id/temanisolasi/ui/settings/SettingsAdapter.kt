package id.temanisolasi.ui.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.temanisolasi.R
import id.temanisolasi.data.model.Settings
import id.temanisolasi.data.repo.remote.firebase.auth.AuthHelpers
import id.temanisolasi.databinding.ItemSettingsBinding
import id.temanisolasi.ui.login.LoginActivity
import id.temanisolasi.ui.settings.detail.DetailSettingsActivity

class SettingsAdapter(
    private val context: Context,
    private val listSettings: List<Settings>
) : RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemSettingsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemSettingsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val setting = listSettings[position]
        with(binding) {
            tvItems.apply {
                text = setting.name
                if (position == 5) setTextColor(ContextCompat.getColor(context, R.color.warning))
            }
            contentSettings.setOnClickListener {
                if (setting.target != null) context.apply {
                    startActivity(Intent(this, setting.target).apply {
                        when (position) {
                            1 -> putExtra(
                                DetailSettingsActivity.EXTRA_SETTINGS,
                                DetailSettingsActivity.FRAGMENT_CREDITS
                            )
                            2 -> putExtra(
                                DetailSettingsActivity.EXTRA_SETTINGS,
                                DetailSettingsActivity.FRAGMENT_ABOUT
                            )
                            3 -> putExtra(
                                DetailSettingsActivity.EXTRA_SETTINGS,
                                DetailSettingsActivity.FRAGMENT_POLICY_PRIVACY
                            )
                        }
                    })
                } else {
                    if (position == 4) context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://forms.gle/CLTLnWFJ7a2QtLyP8")
                        )
                    ) else logout()
                }
            }
        }
    }

    private fun logout() {
        AuthHelpers.signOut(context as Activity)
        context.startActivity(Intent(context, LoginActivity::class.java))
        context.finishAffinity()
    }

    override fun getItemCount(): Int = listSettings.size
}