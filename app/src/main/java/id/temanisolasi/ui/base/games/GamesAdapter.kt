package id.temanisolasi.ui.base.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.temanisolasi.data.model.FavGames
import id.temanisolasi.databinding.ItemGamesBinding

class GamesAdapter(
    private val listener: GamesListener,
    private val listOfFavGames: List<FavGames>
) : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemGamesBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemGamesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val games = listOfFavGames[position]
        with(binding) {
            tvText.text = games.games
            ivImg.load(games.img) { crossfade(true) }
            cardView.setOnClickListener { games.fav?.let { fav -> listener.onSelected(fav) } }
        }
    }

    override fun getItemCount(): Int = listOfFavGames.size
}

interface GamesListener {
    fun onSelected(fav: Int)
}