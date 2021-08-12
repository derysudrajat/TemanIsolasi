package id.temanisolasi.ui.base.home.condition.medicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import id.temanisolasi.R
import id.temanisolasi.databinding.FragmentMedicineBinding
import id.temanisolasi.ui.base.BaseSharedViewModel
import id.temanisolasi.utils.Helpers.getStatus
import id.temanisolasi.utils.TIME

class FragmentMedicine : Fragment() {

    private var _binding: FragmentMedicineBinding? = null
    private val sharedModel: BaseSharedViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicineBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedModel.activeIsolation.observe(viewLifecycleOwner) {
            it.listReport?.map { report -> report.medicine }?.let { med ->
                val todayMedicine = med[(it.passedDay ?: 1) - 1]

                with(binding) {
                    setIcon(ivStatDay, todayMedicine?.get(0), TIME.DAY)
                    setIcon(ivStatNoon, todayMedicine?.get(1), TIME.NOON)
                    setIcon(ivStatNight, todayMedicine?.get(2), TIME.NIGHT)
                }
            }
        }
    }

    private fun setIcon(iv: ImageView, day: String?, mTime: TIME) {
        iv.apply {
            val stat = getStatus(day ?: "-", mTime)
            setImageResource(iconStatus[stat])
            if (stat == 2) rotation = 45f
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private val iconStatus = listOf(
            R.drawable.ic_circle_check,
            R.drawable.ic_circle_remove,
            R.drawable.ic_circle_add
        )

        @JvmStatic
        fun newInstance() = FragmentMedicine()
    }
}