package id.temanisolasi.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import id.temanisolasi.R
import id.temanisolasi.databinding.ActivityMainBinding
import id.temanisolasi.ui.base.home.HomeFragment
import id.temanisolasi.utils.Helpers.setRounded
import id.temanisolasi.utils.SectionPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            bottomAppBar.setRounded()
            btnAddReport.setOnClickListener {
                Toast.makeText(this@MainActivity, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            viewPager.apply {
                adapter = SectionPagerAdapter(
                    this@MainActivity,
                    listOf(
                        HomeFragment.newInstance(),
                        Fragment(),
                        Fragment(),
                        Fragment(),
                    )
                )
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
}