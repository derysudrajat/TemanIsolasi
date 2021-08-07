package id.temanisolasi.ui.startisolation.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import id.temanisolasi.data.model.Isolation
import id.temanisolasi.databinding.FragmentIsolationDataBinding
import id.temanisolasi.ui.startisolation.StartIsolationViewModel
import id.temanisolasi.utils.DateFormat
import id.temanisolasi.utils.Helpers
import id.temanisolasi.utils.Helpers.afterTextChanged
import id.temanisolasi.utils.Helpers.getPlainText
import id.temanisolasi.utils.Helpers.showDatePicker
import id.temanisolasi.utils.Helpers.showError
import id.temanisolasi.utils.Helpers.toTimeStamp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch

class IsolationDataFragment : Fragment() {

    private var _binding: FragmentIsolationDataBinding? = null
    private val binding get() = _binding!!
    private val model: StartIsolationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIsolationDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val bloodTypes = listOf("A", "B", "O", "AB")
            val adapterBloodType =
                ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, bloodTypes)

            val vaccinatedStatus = listOf("Sudah", "Belum")
            val adapterVaccinated = ArrayAdapter(
                requireContext(), R.layout.simple_dropdown_item_1line, vaccinatedStatus
            )

            (edtBloodType as? AutoCompleteTextView)?.setAdapter(adapterBloodType)
            (edtVaccinated as? AutoCompleteTextView)?.setAdapter(adapterVaccinated)

            edtDate.setOnClickListener { requireActivity().showDatePicker { edtDate.setText(it) } }
            tilDate.setEndIconOnClickListener {
                requireActivity().showDatePicker { edtDate.setText(it) }
            }

            lifecycleScope
                .launch { edtDate.afterTextChanged { validateNotEmpty(it, 0, tilDate) } }
            lifecycleScope
                .launch { edtBloodType.afterTextChanged { validateNotEmpty(it, 1, tilBloodType) } }
            lifecycleScope
                .launch { edtWeight.afterTextChanged { validateNotEmpty(it, 2, tilWeight) } }
            lifecycleScope
                .launch {
                    edtVaccinated.afterTextChanged {
                        validateNotEmpty(
                            it,
                            3,
                            tilVaccinated
                        )
                    }
                }
        }

        model.progressIsolation.observe(viewLifecycleOwner) { progress ->
            val isComplete = progress.count { it } == progress.size
            model.setComplete(1, isComplete)
            if (isComplete) with(binding) {
                model.setDataFromIsolation(
                    Isolation(
                        startIsolation = edtDate.getPlainText().toTimeStamp(DateFormat.SIMPLE),
                        bloodType = edtBloodType.getPlainText(),
                        weight = edtWeight.getPlainText().toInt(),
                        vaccinated = if (edtVaccinated.getPlainText() == "Sudah") 1 else 0
                    )
                )
            }
        }
    }

    private fun validateNotEmpty(it: String, index: Int, til: TextInputLayout) = with(binding) {
        val valid = it.isNotEmpty()
        model.setProgressIsolation(index, valid)
        if (valid) Helpers.validateError(til)
        else til.showError()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = IsolationDataFragment()
    }
}