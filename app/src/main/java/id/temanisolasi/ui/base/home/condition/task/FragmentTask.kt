package id.temanisolasi.ui.base.home.condition.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.temanisolasi.databinding.FragmentTaskBinding

class FragmentTask : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
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