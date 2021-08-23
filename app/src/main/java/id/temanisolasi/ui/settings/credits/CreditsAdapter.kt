package id.temanisolasi.ui.settings.credits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.temanisolasi.data.model.Credits
import id.temanisolasi.databinding.ItemCreditsBinding
import id.temanisolasi.utils.Helpers.formatHtml

class CreditsAdapter(
    private val listCredits: List<Credits>
) : RecyclerView.Adapter<CreditsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemCreditsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCreditsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val credits = listCredits[position]
        with(binding) {
            "${(position + 1)}.".also { tvNo.text = it }
            tvTitle.text = credits.name
            tvContent.text = credits.source?.formatHtml()
        }
    }

    override fun getItemCount(): Int = listCredits.size
}