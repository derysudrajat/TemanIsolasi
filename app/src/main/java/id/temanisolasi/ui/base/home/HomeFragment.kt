package id.temanisolasi.ui.base.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import id.temanisolasi.databinding.FragmentHomeBinding
import id.temanisolasi.ui.base.home.condition.FragmentTemperature
import id.temanisolasi.utils.Helpers
import id.temanisolasi.utils.SectionPagerAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var conditionPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dummyTemp = Helpers.dummyTemp
        val dayLeft = (14 - dummyTemp.size)
        binding.apply {
            circularSeekBar.apply {
                isEnabled = false
                progress = dayLeft.toFloat()
            }
            tvDayLeft.text = dayLeft.toString()
            circularSeekBar2.isEnabled = false

            viewPager.apply {
                adapter = SectionPagerAdapter(
                    requireActivity(),
                    listOf(
                        FragmentTemperature.newInstance(),
                        Fragment(),
                        Fragment(),
                        Fragment(),
                    )
                )
            }

            TabLayoutMediator(tabLayout, viewPager) { tabs, position ->
                tabs.icon = resources.getDrawable(Helpers.dummyIconHome[position], null)
            }.attach()

            btnNext.setOnClickListener {
                if (conditionPage < 3) {
                    conditionPage++
                    viewPager.currentItem = conditionPage
                }
            }
            btnPrev.setOnClickListener {
                if (conditionPage > 0) {
                    conditionPage--
                    viewPager.currentItem = conditionPage
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}