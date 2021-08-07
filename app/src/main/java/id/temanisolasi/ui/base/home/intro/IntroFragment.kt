package id.temanisolasi.ui.base.home.intro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.temanisolasi.databinding.FragmentIntroBinding
import id.temanisolasi.ui.startisolation.StartIsolationActivity

class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnStart.setOnClickListener {
            requireActivity().startActivity(
                Intent(requireContext(), StartIsolationActivity::class.java)
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = IntroFragment()
    }
}