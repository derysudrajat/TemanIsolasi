package id.temanisolasi.ui.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import id.temanisolasi.R
import id.temanisolasi.databinding.ActivitySettingsBinding
import id.temanisolasi.utils.DataHelpers

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
            setDisplayShowTitleEnabled(false)
        }

        binding.rvSettings.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = SettingsAdapter(this@SettingsActivity, DataHelpers.itemSettings)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_SETTINGS = "extra_setting"
    }
}