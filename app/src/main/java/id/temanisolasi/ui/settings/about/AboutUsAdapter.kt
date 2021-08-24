package id.temanisolasi.ui.settings.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import id.temanisolasi.data.model.Team
import id.temanisolasi.databinding.ItemAboutUsBinding

class AboutUsAdapter(
    private val context: Context,
    private val listOfTeam: List<Team>
) : RecyclerView.Adapter<AboutUsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemAboutUsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemAboutUsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = listOfTeam[position]
        with(binding) {
            tvName.text = team.name
            tvRole.text = team.role
            ivAva.load(team.img) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            btnFollow.setOnClickListener {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(team.link))
                )
            }
        }
    }

    override fun getItemCount(): Int = listOfTeam.size


}