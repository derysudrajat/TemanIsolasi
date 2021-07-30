package id.temanisolasi.ui.base.home.condition.oxygen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.Timestamp
import id.temanisolasi.databinding.FragmentTemperatureBinding
import id.temanisolasi.ui.base.home.condition.LineChartViewHolder
import id.temanisolasi.utils.DateFormat
import id.temanisolasi.utils.Helpers
import id.temanisolasi.utils.Helpers.formatDate

class FragmentOxygen : Fragment() {

    private var _binding: FragmentTemperatureBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTemperatureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Helpers.dummySaturation.let {
            populateChart(it)
            binding.apply {
                tvTitle.text = "SATURASI OKSIGEN"
                tvDataContent.text = buildString { append(it.last().toInt()).append("%") }
                tvDate.text = Timestamp.now().formatDate(DateFormat.SHORT)
            }
        }
    }

    private fun populateChart(it: MutableList<Float>) {
        val lineChartViewHolder = LineChartViewHolder(requireContext(), binding)
        lineChartViewHolder.setDataChart(it, 80f)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentOxygen()
    }
}