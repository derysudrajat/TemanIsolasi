package id.temanisolasi.ui.base.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.temanisolasi.data.model.Isolation
import id.temanisolasi.databinding.ItemIsolationHistoryBinding
import id.temanisolasi.utils.DateFormat
import id.temanisolasi.utils.Helpers.formatDate

class IsolationAdapter(
    private val listener: ItemIsolationListener,
    private val dataIsolation: List<Isolation>
) : RecyclerView.Adapter<IsolationAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemIsolationHistoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemIsolationHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isolation = dataIsolation[position]
        with(binding) {
            tvName.text = isolation.name
            tvDate.text = isolation.startIsolation?.formatDate(DateFormat.SHORT)
            contentItem.setOnClickListener { listener.onItemClick(isolation) }
        }
    }

    override fun getItemCount(): Int = dataIsolation.size
}

interface ItemIsolationListener {
    fun onItemClick(isolation: Isolation)
}