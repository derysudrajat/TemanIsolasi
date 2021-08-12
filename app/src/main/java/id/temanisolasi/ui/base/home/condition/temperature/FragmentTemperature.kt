package id.temanisolasi.ui.base.home.condition.temperature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.Timestamp
import id.temanisolasi.databinding.FragmentTemperatureBinding
import id.temanisolasi.ui.base.BaseSharedViewModel
import id.temanisolasi.ui.base.home.condition.LineChartViewHolder
import id.temanisolasi.utils.DateFormat
import id.temanisolasi.utils.Helpers.formatDate

class FragmentTemperature : Fragment() {

    private var _binding: FragmentTemperatureBinding? = null
    private val sharedModel: BaseSharedViewModel by activityViewModels()
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
        sharedModel.activeIsolation.observe(viewLifecycleOwner) {
            it.listReport?.map { report -> report.temperature }?.let { tempList ->
                populateChart(tempList.toMutableList())
                binding.apply {
                    tvDataContent.text =
                        buildString { append(tempList[(it.passedDay ?: 1) - 1]).append("°") }
                    tvDate.text = Timestamp.now().formatDate(DateFormat.SHORT)
                }
            }

        }
    }

    private fun populateChart(dummyTemp: MutableList<Float?>) {
        val lineChartViewHolder = LineChartViewHolder(requireContext(), binding)
        lineChartViewHolder.setDataChart(dummyTemp)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentTemperature()
    }
}