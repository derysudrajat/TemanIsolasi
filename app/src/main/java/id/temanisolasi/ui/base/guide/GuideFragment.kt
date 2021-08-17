package id.temanisolasi.ui.base.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.SimpleItemAnimator
import id.temanisolasi.databinding.FragmentGuideBinding
import id.temanisolasi.utils.DataHelpers

class GuideFragment : Fragment() {

    private var _binding: FragmentGuideBinding? = null
    private val model: GuideViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.getAllQuestion()

        with(binding) {

            rvGuide.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = GuideAdapter(requireContext(), DataHelpers.listOfGuide)
            }

            model.questions.observe(viewLifecycleOwner) {
                rvQuestion.apply {
                    itemAnimator = DefaultItemAnimator()
                    (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                    adapter = QuestionAdapter(requireContext(), it)
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
        fun newInstance() = GuideFragment()
    }
}