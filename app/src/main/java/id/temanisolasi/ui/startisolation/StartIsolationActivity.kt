package id.temanisolasi.ui.startisolation

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import id.temanisolasi.R
import id.temanisolasi.databinding.ActivityStartIsolationBinding
import id.temanisolasi.provider.AlarmReceiver
import id.temanisolasi.ui.startisolation.fragment.BeginIsolationFragment
import id.temanisolasi.ui.startisolation.fragment.IsolationDataFragment
import id.temanisolasi.ui.startisolation.fragment.PersonalDataFragment
import id.temanisolasi.utils.SectionPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartIsolationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartIsolationBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private val model: StartIsolationViewModel by viewModels()
    private val isolationModel: IsolationViewModel by viewModel()
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartIsolationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        alarmReceiver = AlarmReceiver()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
            setDisplayShowTitleEnabled(false)
        }

        with(binding) {

            listOf(ivPersonalData, ivDataIsolation, ivFinish).forEachIndexed { index, img ->
                img.setOnClickListener {
                    model.getProgress().let { progress ->
                        if (progress[index]) {
                            currentPage = index
                            populatePosition(index)
                        }
                    }
                }
            }

            viewPager.apply {
                adapter = SectionPagerAdapter(
                    this@StartIsolationActivity,
                    listOf(
                        PersonalDataFragment.newInstance(),
                        IsolationDataFragment.newInstance(),
                        BeginIsolationFragment.newInstance(),
                    )
                )
                isUserInputEnabled = false
            }

            btnNext.setOnClickListener {
                currentPage++
                if (currentPage < 3) populatePosition(currentPage)
                else model.getDataIsolation().let { isolation ->
                    isolationModel.addNewDataIsolation(isolation)
                    alarmReceiver.apply {
                        setAlarm(
                            this@StartIsolationActivity,
                            AlarmReceiver.Companion.NOTIFICATION_ID.MORNING
                        )
                        if (isolation.symptom == 1) {
                            setAlarm(
                                this@StartIsolationActivity,
                                AlarmReceiver.Companion.NOTIFICATION_ID.NOON
                            )
                            setAlarm(
                                this@StartIsolationActivity,
                                AlarmReceiver.Companion.NOTIFICATION_ID.NIGHT
                            )
                        }
                    }
                    finish()
                }
            }
        }

        model.progress.observe(this) {
            binding.btnNext.isEnabled = it[currentPage]
        }

    }

    private fun populatePosition(index: Int) = with(binding) {
        viewPager.currentItem = index
        if (index == 2) model.setComplete(index, true)
        model.getProgress().let { btnNext.isEnabled = it[index] }

        btnNext.text = if (index == 2) "Mulai Isolasi" else "Selanjutnya"

        linePersonalToIsolation.setCardBackgroundColor(
            ContextCompat.getColor(
                applicationContext, if (index >= 1) R.color.white else R.color.white_50
            )
        )

        ivDataIsolation.imageTintList = ContextCompat.getColorStateList(
            applicationContext, if (index >= 1) R.color.white else R.color.white_50
        )

        tvIsolationData.setTextColor(
            ContextCompat.getColor(
                applicationContext, if (index >= 1) R.color.white else R.color.white_50
            )
        )

        lineIsolationToFinish.setCardBackgroundColor(
            ContextCompat.getColor(
                applicationContext, if (index == 2) R.color.white else R.color.white_50
            )
        )

        ivFinish.imageTintList = ContextCompat.getColorStateList(
            applicationContext, if (index == 2) R.color.white else R.color.white_50
        )

        tvFinish.setTextColor(
            ContextCompat.getColor(
                applicationContext, if (index == 2) R.color.white else R.color.white_50
            )
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}