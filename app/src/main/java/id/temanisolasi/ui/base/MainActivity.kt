package id.temanisolasi.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.temanisolasi.R
import id.temanisolasi.data.model.InputData
import id.temanisolasi.data.model.Isolation
import id.temanisolasi.databinding.ActivityMainBinding
import id.temanisolasi.ui.base.guide.GuideFragment
import id.temanisolasi.ui.base.home.HomeFragment
import id.temanisolasi.ui.base.home.HomeViewModel
import id.temanisolasi.ui.base.home.intro.IntroFragment
import id.temanisolasi.ui.base.inputdata.InputDataActivity
import id.temanisolasi.ui.base.profile.ProfileFragment
import id.temanisolasi.utils.DataHelpers
import id.temanisolasi.utils.Helpers.setRounded
import id.temanisolasi.utils.SectionPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), InputDataListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<View>
    private val model: HomeViewModel by viewModel()
    private val baseModel: BaseViewModel by viewModel()
    private val sharedModel: BaseSharedViewModel by viewModels()
    private var bottomSheetState = 0
    private var currentIsolation = Isolation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomSheet()
        baseModel.getActiveIsolationData()
        model.getUser()

        binding.apply {
            bottomAppBar.setRounded()
            btnAddReport.setOnClickListener {
                showBottomView()
            }
            viewPager.apply {
                model.user.observe(this@MainActivity) { user ->
                    adapter = SectionPagerAdapter(
                        this@MainActivity,
                        listOf(
                            if (user?.inIsolation == true)
                                HomeFragment.newInstance()
                            else IntroFragment.newInstance(),
                            GuideFragment.newInstance(),
                            Fragment(),
                            ProfileFragment.newInstance(),
                        )
                    )
                }
                isUserInputEnabled = false
            }

            bottomNavigationView.setOnItemSelectedListener {
                val position = when (it.itemId) {
                    R.id.itemHome -> 0
                    R.id.itemGuide -> 1
                    R.id.itemActivity -> 2
                    R.id.itemProfile -> 3
                    else -> -1
                }
                if (position != -1) {
                    setPagePosition(position)
                    true
                } else false
            }
        }
        baseModel.activeIsolation.observe(this) {
            sharedModel.setDataActiveIsolation(it)
            currentIsolation = it

            it.listReport?.get((it.passedDay ?: 1) - 1)?.let { report ->
                binding.rvInputData.apply {
                    itemAnimator = DefaultItemAnimator()
                    adapter = InputDataAdapter(
                        this@MainActivity, DataHelpers.itemInputData, report, it.symptom ?: 0
                    )
                }
            }
        }
    }

    private fun setPagePosition(position: Int) {
        binding.viewPager.currentItem = position
    }

    private fun showBottomView() {
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun hideBottomView() {
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun setupBottomSheet() = with(binding) {
        standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)
        standardBottomSheetBehavior.apply {
            isHideable = true
            state = BottomSheetBehavior.STATE_HIDDEN
            saveFlags = BottomSheetBehavior.SAVE_ALL
        }
        standardBottomSheetBehavior.addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    bottomSheetState = newState
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
    }

    override fun onBackPressed() {
        if (bottomSheetState == BottomSheetBehavior.STATE_HALF_EXPANDED) hideBottomView()
        else finish()
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) hideBottomView()
    }

    override fun onClick(data: InputData) {
        startForResult.launch(Intent(this, InputDataActivity::class.java)
            .apply {
                putExtra(InputDataActivity.EXTRA_DATA, data)
                putExtra(InputDataActivity.EXTRA_ISOLATION, currentIsolation)
            })
    }
}