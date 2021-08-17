package id.temanisolasi.ui.base.guide

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.temanisolasi.data.model.Guide
import id.temanisolasi.databinding.ItemGuideBinding
import id.temanisolasi.ui.base.guide.detail.DetailGuideActivity

class GuideAdapter(
    private val context: Context,
    private val listGuides: List<Guide>
) : RecyclerView.Adapter<GuideAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemGuideBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemGuideBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val guide = listGuides[position]
        with(binding) {
            tvText.text = guide.text
            ivImg.load(guide.img) { crossfade(true) }
            cardView.setOnClickListener {
                context.startActivity(
                    Intent(context, DetailGuideActivity::class.java).apply {
                        putExtra(DetailGuideActivity.EXTRA_GUIDE, position)
                    }
                )
            }
        }
    }

    override fun getItemCount(): Int = listGuides.size
}