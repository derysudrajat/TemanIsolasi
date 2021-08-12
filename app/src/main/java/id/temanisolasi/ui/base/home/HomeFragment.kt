package id.temanisolasi.ui.base.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import id.temanisolasi.databinding.FragmentHomeBinding
import id.temanisolasi.ui.base.BaseSharedViewModel
import id.temanisolasi.ui.base.home.condition.medicine.FragmentMedicine
import id.temanisolasi.ui.base.home.condition.oxygen.FragmentOxygen
import id.temanisolasi.ui.base.home.condition.task.FragmentTask
import id.temanisolasi.ui.base.home.condition.temperature.FragmentTemperature
import id.temanisolasi.utils.DataHelpers
import id.temanisolasi.utils.Helpers
import id.temanisolasi.utils.SectionPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val model: HomeViewModel by viewModel()
    private val sharedModel: BaseSharedViewModel by activityViewModels()
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
        sharedModel.activeIsolation.observe(viewLifecycleOwner) { isolation ->

            val dayLeft = (14 - (isolation?.passedDay ?: 1))
            model.getUser()

            binding.apply {

                tvCheer.text = DataHelpers.dataHomeTitle[(isolation?.passedDay ?: 1) - 1]

                model.user.observe(requireActivity()) {
                    tvName.text = buildString { append("Hi, ").append(it.name) }
                }
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
                            FragmentOxygen.newInstance(),
                            FragmentMedicine.newInstance(),
                            FragmentTask.newInstance(),
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