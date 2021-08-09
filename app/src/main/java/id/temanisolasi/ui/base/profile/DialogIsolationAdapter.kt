package id.temanisolasi.ui.base.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.temanisolasi.databinding.ItemDataIsolationBinding
import id.temanisolasi.utils.DataHelpers

class DialogIsolationAdapter(
    private val listData: List<String>
) : RecyclerView.Adapter<DialogIsolationAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemDataIsolationBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemDataIsolationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = DataHelpers.listOfDataIsolation[position]
        val value = listData[position]
        with(binding) {
            tvItems.text = items
            tvValue.text = value
        }
    }

    override fun getItemCount(): Int = listData.size
}