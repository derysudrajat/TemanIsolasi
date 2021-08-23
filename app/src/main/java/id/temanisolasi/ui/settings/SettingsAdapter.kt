package id.temanisolasi.ui.settings

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.temanisolasi.data.model.Settings
import id.temanisolasi.databinding.ItemSettingsBinding
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
            tvItems.text = setting.name
            contentSettings.setOnClickListener {
                if (setting.target != null) context.apply {
                    startActivity(Intent(this, setting.target).apply {
                        when (position) {
                            1 -> putExtra(
                                DetailSettingsActivity.EXTRA_SETTINGS,
                                DetailSettingsActivity.FRAGMENT_CREDITS
                            )
                        }
                    })
                }
            }
        }
    }

    override fun getItemCount(): Int = listSettings.size
}