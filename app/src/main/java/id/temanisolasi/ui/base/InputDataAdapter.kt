package id.temanisolasi.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.temanisolasi.R
import id.temanisolasi.data.model.InputData
import id.temanisolasi.data.model.Report
import id.temanisolasi.databinding.ItemInputDataBinding
import id.temanisolasi.utils.Helpers
import id.temanisolasi.utils.TIME

class InputDataAdapter(
    private val listener: InputDataListener,
    private val listData: List<InputData>,
    private val report: Report,
    private val symptom: Int
) : RecyclerView.Adapter<InputDataAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemInputDataBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemInputDataBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listData[position].let { data ->
            with(binding) {
                tvItem.text = data.text
                ivIcon.setImageResource(data.img)
                contentInput.setOnClickListener { listener.onClick(data) }

                if (position == 2) contentInput.apply {
                    val time = Helpers.getTimeStateNow()
                    val status = report.medicine?.get(
                        when (time) {
                            TIME.DAY -> 0
                            TIME.NOON -> 1
                            TIME.NIGHT -> 2
                        }
                    )?.let { Helpers.getStatus(it, time) }

                    if (status == 0 || symptom == 0) {
                        isClickable = false
                        isFocusable = false
                        setCardBackgroundColor(ContextCompat.getColor(context, R.color.primary_50))
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = listData.size
}

interface InputDataListener {
    fun onClick(data: InputData)
}