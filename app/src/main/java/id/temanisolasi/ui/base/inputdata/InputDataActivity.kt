package id.temanisolasi.ui.base.inputdata

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Timestamp
import id.temanisolasi.data.model.InputData
import id.temanisolasi.data.model.Isolation
import id.temanisolasi.data.model.Report
import id.temanisolasi.databinding.ActivityInputDataBinding
import id.temanisolasi.utils.DataHelpers
import id.temanisolasi.utils.Helpers
import id.temanisolasi.utils.Helpers.afterTextChanged
import id.temanisolasi.utils.Helpers.getHour
import id.temanisolasi.utils.Helpers.getPlainText
import id.temanisolasi.utils.Helpers.showError
import id.temanisolasi.utils.Helpers.showView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class InputDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputDataBinding
    private val model: InputDataViewModel by viewModel()
    private val checkedItem = mutableListOf(false, false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.getParcelable<InputData>(EXTRA_DATA)?.let { data ->
            intent.extras?.getParcelable<Isolation>(EXTRA_ISOLATION)?.let { isolation ->

                val index = (isolation.passedDay ?: 1) - 1
                val report = isolation.listReport?.get(index)
                Log.d("TAG", "reportData-before: ${isolation.listReport}")

                with(binding) {
                    tvTitle.text = data.text
                    ivIllustration.setImageResource(DataHelpers.imageInputData[data.target])
                    tvContentTitle.text = DataHelpers.tdInputData[data.target]

                    if (data.target != 3) {
                        tvContentDesc.apply {
                            text = DataHelpers.cdInputData[data.target]
                            this.showView()
                        }
                        layoutEditText.showView()
                        listOf(tilTemp, tilOxy, tilMed)[data.target].showView()
                    } else {
                        val checkBoxes = listOf(checkOne, checkTwo, checkThree, checkFour)
                        report?.task?.forEachIndexed { index, isFinished ->
                            checkBoxes[index].apply {
                                isChecked = isFinished
                                checkedItem[index] = isFinished
                                if (isFinished) isEnabled = false
                            }
                        }
                        layoutCheckbox.showView()
                    }
                    btnSave.isEnabled = data.target == 3

                    checkOne.setOnCheckedChangeListener { _, it -> setCheckItem(0, it) }
                    checkTwo.setOnCheckedChangeListener { _, it -> setCheckItem(1, it) }
                    checkThree.setOnCheckedChangeListener { _, it -> setCheckItem(2, it) }
                    checkFour.setOnCheckedChangeListener { _, it -> setCheckItem(3, it) }

                    lifecycleScope.launch {
                        edtTemp.afterTextChanged { validateInput(it, tilTemp) }
                    }
                    lifecycleScope.launch { edtOxy.afterTextChanged { validateInput(it, tilOxy) } }
                    lifecycleScope.launch { edtMed.afterTextChanged { validateInput(it, tilMed) } }

                    btnSave.setOnClickListener {
                        when (data.target) {
                            0 -> report?.temperature = edtTemp.getPlainText().toFloat()
                            1 -> report?.saturationOxygen = edtOxy.getPlainText().toFloat()
                            2 -> report?.medicine?.set(getTimeIndex(), edtMed.getPlainText())
                            3 -> report?.task = checkedItem
                        }
                        report.also { isolation.listReport?.set(index, it ?: Report()) }
                        model.updateDataIsolation(
                            isolation.id ?: "",
                            isolation.listReport ?: mutableListOf(),
                            this@InputDataActivity
                        ) {
                            if (it) {
                                setResult(RESULT_OK)
                                finish()
                            }
                        }
                    }

                }
            }
        }
    }

    private fun setCheckItem(i: Int, checked: Boolean) {
        checkedItem[i] = checked
    }

    private fun validateInput(it: String, til: TextInputLayout) {
        val isValid = it.isNotEmpty()
        binding.btnSave.isEnabled = isValid

        if (isValid) Helpers.validateError(til)
        else til.showError()
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_ISOLATION = "extra_isolation"

        private fun getTimeIndex(): Int {
            Timestamp.now().getHour().let {
                return when (it) {
                    in 7..12 -> 0
                    in 13..18 -> 1
                    in 19..24 -> 2
                    else -> -1
                }
            }
        }
    }
}