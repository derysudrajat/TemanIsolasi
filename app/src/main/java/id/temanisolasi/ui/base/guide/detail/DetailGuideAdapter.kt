package id.temanisolasi.ui.base.guide.detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.OriginalSize
import coil.transform.RoundedCornersTransformation
import id.temanisolasi.data.model.ContentGuide
import id.temanisolasi.databinding.ItemDetailGuideBinding
import id.temanisolasi.utils.Helpers.showView

class DetailGuideAdapter(
    private val context: Context,
    private val listGuide: List<ContentGuide>
) : RecyclerView.Adapter<DetailGuideAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemDetailGuideBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemDetailGuideBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val guide = listGuide[position]
        with(binding) {
            when (guide.type) {
                0 -> tvHeading.apply {
                    text = guide.content
                    showView()
                }
                1 -> tvSubHeading.apply {
                    text = guide.content
                    showView()
                }
                2 -> tvContent.apply {
                    text = guide.content
                    showView()
                }
                3 -> layoutListNumber.apply {
                    tvOrderedNumber.text = buildString { append(guide.ordered).append(". ") }
                    tvTextNumber.text = guide.content
                    showView()
                }
                4 -> layoutListPoint.apply {
                    tvTextPoint.text = guide.content
                    showView()
                }
                5 -> layoutQuotes.apply {
                    tvTextQuotes.text = guide.content?.replace("\\n", "\n")
                    showView()
                }

                6 -> ivContent.apply {
                    load(guide.content) {
                        crossfade(true)
                        transformations(RoundedCornersTransformation(16f))
                        size(OriginalSize)
                    }
                    showView()
                }
                else -> Log.d("TAG", "onBindViewHolder: error")
            }
        }
    }

    override fun getItemCount(): Int = listGuide.size
}