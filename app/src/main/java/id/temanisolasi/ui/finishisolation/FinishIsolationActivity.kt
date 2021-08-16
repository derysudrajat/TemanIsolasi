package id.temanisolasi.ui.finishisolation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.temanisolasi.databinding.ActivityFinishIsolationBinding
import id.temanisolasi.provider.AlarmReceiver
import id.temanisolasi.ui.base.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FinishIsolationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinishIsolationBinding
    private val model: FinishIsolationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishIsolationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        intent.extras?.getString(EXTRA_ISOLATION)?.let { docId ->
            model.makeIsolationFinish(docId)
            val activity = this
            AlarmReceiver().apply {
                cancelAlarm(activity, AlarmReceiver.Companion.NOTIFICATION_ID.MORNING)
                cancelAlarm(activity, AlarmReceiver.Companion.NOTIFICATION_ID.NOON)
                cancelAlarm(activity, AlarmReceiver.Companion.NOTIFICATION_ID.NIGHT)
            }

            binding.btnHome.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_ISOLATION = "extra_isolation"
    }
}