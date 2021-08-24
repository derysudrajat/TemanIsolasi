package id.temanisolasi.ui.settings.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import id.temanisolasi.R
import id.temanisolasi.databinding.ActivityDetailSettingsBinding
import id.temanisolasi.ui.settings.about.AboutUsFragment
import id.temanisolasi.ui.settings.credits.CreditsFragment
import id.temanisolasi.ui.settings.privacy.PrivacyPolicyFragment

class DetailSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            intent.extras?.getString(EXTRA_SETTINGS)?.let {

                val fragment = when (it) {
                    FRAGMENT_CREDITS -> CreditsFragment.newInstance()
                    FRAGMENT_ABOUT -> AboutUsFragment.newInstance()
                    FRAGMENT_POLICY_PRIVACY -> PrivacyPolicyFragment.newInstance()
                    else -> Fragment()
                }

                val pageTitle = when (it) {
                    FRAGMENT_CREDITS -> "Credits"
                    FRAGMENT_ABOUT -> "Tentang Kami"
                    FRAGMENT_POLICY_PRIVACY -> "Kebijakan Privasi"
                    else -> ""
                }

                supportFragmentManager
                    .beginTransaction()
                    .replace(binding.fragmentContent.id, fragment)
                    .commit()

                supportActionBar?.apply {
                    title = pageTitle
                    setDisplayHomeAsUpEnabled(true)
                    setHomeAsUpIndicator(R.drawable.ic_back)
                }
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_SETTINGS = "extra_settings"

        const val FRAGMENT_CREDITS = "fragment_credits"
        const val FRAGMENT_ABOUT = "fragment_about"
        const val FRAGMENT_POLICY_PRIVACY = "fragment_policy_privacy"
    }
}