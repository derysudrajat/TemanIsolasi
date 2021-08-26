package id.temanisolasi.ui.base.home.intro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.temanisolasi.databinding.FragmentIntroBinding
import id.temanisolasi.ui.base.home.HomeViewModel
import id.temanisolasi.ui.startisolation.StartIsolationActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!
    private val model: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.getUser()
        binding.btnStart.setOnClickListener {
            requireActivity().startActivity(
                Intent(requireContext(), StartIsolationActivity::class.java)
            )
        }

        model.user.observe(viewLifecycleOwner) {
            binding.tvName.text = buildString { append("Hi, ").append(it.name) }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = IntroFragment()
    }
}