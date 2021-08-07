package id.temanisolasi.ui.startisolation.fragment

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
import id.temanisolasi.databinding.FragmentPersonalDataBinding
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

class PersonalDataFragment : Fragment() {

    private var _binding: FragmentPersonalDataBinding? = null
    private val binding get() = _binding!!
    private val model: StartIsolationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalDataBinding.inflate(inflater, container, false)
        return binding.root
    }


    @FlowPreview
    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val items = listOf("Pria", "Wanita")
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, items)
            (edtGender as? AutoCompleteTextView)?.setAdapter(adapter)

            edtDate.setOnClickListener {
                requireActivity().showDatePicker { date -> edtDate.setText(date) }
            }

            tilDate.setEndIconOnClickListener {
                requireActivity().showDatePicker { edtDate.setText(it) }
            }

            lifecycleScope
                .launch { edtName.afterTextChanged { validateNotEmpty(it, 0, tilName) } }
            lifecycleScope
                .launch { edtGender.afterTextChanged { validateNotEmpty(it, 1, tilGender) } }
            lifecycleScope
                .launch { edtDate.afterTextChanged { validateNotEmpty(it, 2, tilDate) } }
            lifecycleScope
                .launch { edtAddress.afterTextChanged { validateNotEmpty(it, 3, tilAddress) } }
        }

        model.progressPersonal.observe(viewLifecycleOwner) { progress ->
            val isComplete = progress.count { it } == progress.size
            model.setComplete(0, isComplete)
            if (isComplete) with(binding) {
                model.setDataFromPersonal(
                    Isolation(
                        edtName.getPlainText(),
                        if (edtGender.getPlainText() == "Pria") 0 else 1,
                        edtDate.getPlainText().toTimeStamp(DateFormat.SIMPLE),
                        edtAddress.getPlainText()
                    )
                )
            }
        }
    }

    private fun validateNotEmpty(it: String, index: Int, til: TextInputLayout) = with(binding) {
        val valid = it.isNotEmpty()
        model.setProgressPersonal(index, valid)
        if (valid) Helpers.validateError(til)
        else til.showError()
    }


    companion object {

        @JvmStatic
        fun newInstance() = PersonalDataFragment()
    }
}