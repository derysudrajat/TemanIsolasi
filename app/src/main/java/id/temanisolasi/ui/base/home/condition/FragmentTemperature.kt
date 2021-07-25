package id.temanisolasi.ui.base.home.condition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.temanisolasi.databinding.FragmentTemperatureBinding
import id.temanisolasi.utils.Helpers

class FragmentTemperature : Fragment() {

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
        populateChart(Helpers.dummyTemp)
    }

    private fun populateChart(dummyTemp: MutableList<Float>) {
        val lineChartViewHolder = LineChartViewHolder(requireContext(), binding)
        lineChartViewHolder.setDataChart(dummyTemp)
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentTemperature()
    }
}