package id.temanisolasi.ui.base.home.condition.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import id.temanisolasi.databinding.FragmentTaskBinding
import id.temanisolasi.ui.base.BaseSharedViewModel

class FragmentTask : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val sharedModel: BaseSharedViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedModel.activeIsolation.observe(viewLifecycleOwner) {
            it.listReport?.map { report -> report.task }?.let { task ->
                val complete = task[(it.passedDay ?: 1) - 1]?.filter { t -> t }?.count() ?: 0
                with(binding) {
                    progressIndicator.progress = complete
                    tvTask.text = complete.toString()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {

        @JvmStatic
        fun newInstance() = FragmentTask()
    }
}