package id.temanisolasi.ui.base.guide.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import id.temanisolasi.R
import id.temanisolasi.databinding.ActivityDetailGuideBinding
import id.temanisolasi.utils.DataHelpers
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailGuideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGuideBinding
    private val model: DetailGuideActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_back)
            setDisplayHomeAsUpEnabled(true)
        }

        intent.extras?.getInt(EXTRA_GUIDE, 0)?.let {
            supportActionBar?.title = DataHelpers.listOfGuide[it].text
            when (it) {
                0 -> model.getDataStart()
                1 -> model.getDataIn()
                2 -> model.getDataFinish()
                else -> model.getDataStart()
            }
        }

        model.guides.observe(this) {
            binding.rvDetailGuide.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = DetailGuideAdapter(this@DetailGuideActivity, it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_GUIDE = "extra_guide"
    }
}