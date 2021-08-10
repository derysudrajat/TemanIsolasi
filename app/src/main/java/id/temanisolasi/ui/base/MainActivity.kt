package id.temanisolasi.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.temanisolasi.R
import id.temanisolasi.databinding.ActivityMainBinding
import id.temanisolasi.ui.base.home.HomeFragment
import id.temanisolasi.ui.base.home.HomeViewModel
import id.temanisolasi.ui.base.home.intro.IntroFragment
import id.temanisolasi.ui.base.profile.ProfileFragment
import id.temanisolasi.utils.Helpers.setRounded
import id.temanisolasi.utils.SectionPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<View>
    private val model: HomeViewModel by viewModel()
    private var bottomSheetState = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomSheet()
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
                            Fragment(),
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
    }

    private fun setPagePosition(position: Int) {
        binding.viewPager.currentItem = position
    }

    fun showBottomView() {
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    fun hideBottomView() {
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun setupBottomSheet() = with(binding) {
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainer.id, Fragment())
            .commit()

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
}