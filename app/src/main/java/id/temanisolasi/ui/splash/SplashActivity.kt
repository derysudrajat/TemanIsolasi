package id.temanisolasi.ui.splash

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.temanisolasi.R
import id.temanisolasi.data.model.User
import id.temanisolasi.provider.AlarmReceiver
import id.temanisolasi.ui.base.BaseViewModel
import id.temanisolasi.ui.base.MainActivity
import id.temanisolasi.ui.base.home.HomeViewModel
import id.temanisolasi.ui.finishisolation.FinishIsolationActivity
import id.temanisolasi.ui.login.LoginActivity
import id.temanisolasi.ui.register.RegisterActivity
import id.temanisolasi.utils.Helpers.dayFrom
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashActivity : AppCompatActivity() {

    private lateinit var alarmManager: AlarmReceiver
    private val model: HomeViewModel by viewModel()
    private val baseModel: BaseViewModel by viewModel()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        alarmManager = AlarmReceiver()
        changeColor(R.color.white)
        Handler(mainLooper).postDelayed({
            Firebase.auth.currentUser.let {
                if (it != null) {
                    model.getUser()
                    model.user.observe(this) { user ->
                        if (user.name != null) goToMainActivity()
                        else goToRegisterActivity(it)
                    }
                } else goToLoginActivity()
            }
        }, 2000)
    }

    private fun goToRegisterActivity(user: FirebaseUser) {
        startActivity(Intent(this, RegisterActivity::class.java)
            .apply {
                putExtra(RegisterActivity.EXTRA_CREATE, User(user.displayName, user.email))
            })
    }

    private fun goToMainActivity() {
        model.user.removeObservers(this)
        val morningAlarm =
            alarmManager.isAlarmSet(this, AlarmReceiver.Companion.NOTIFICATION_ID.MORNING)
        val noonAlarm = alarmManager.isAlarmSet(this, AlarmReceiver.Companion.NOTIFICATION_ID.NOON)
        val nightAlarm =
            alarmManager.isAlarmSet(this, AlarmReceiver.Companion.NOTIFICATION_ID.NIGHT)
        Log.d(
            "TAG",
            "goToMainActivity: alarm set morning = $morningAlarm, noon = $noonAlarm, night = $nightAlarm"
        )
        baseModel.getActiveIsolationData()
        baseModel.activeIsolation.observe(this) {
            baseModel.activeIsolation.removeObservers(this)
            val dayDiff = it.startIsolation?.dayFrom(Timestamp.now())
            val isNewDay = dayDiff?.toInt() ?: 1 > it.passedDay ?: 1
            if (it.active == true && it.symptom != null) {
                val notificationId = AlarmReceiver.Companion.NOTIFICATION_ID
                if (it.symptom == 0 && !morningAlarm) alarmManager.setAlarm(
                    this, notificationId.MORNING
                ) else if (it.symptom == 1 && !morningAlarm && !noonAlarm && !nightAlarm) alarmManager.apply {
                    setAlarm(this@SplashActivity, notificationId.MORNING)
                    setAlarm(this@SplashActivity, notificationId.NOON)
                    setAlarm(this@SplashActivity, notificationId.NIGHT)
                }
            }

            val intent = if (isNewDay && it.passedDay == 14)
                Intent(this, FinishIsolationActivity::class.java)
                    .apply { putExtra(FinishIsolationActivity.EXTRA_ISOLATION, it.id) }
            else Intent(this, MainActivity::class.java)

            Log.d("TAG", "goToMainActivity: diff = $dayDiff, $isNewDay")

            if (isNewDay && it.passedDay != 14) postNewReport(it.id, dayDiff, intent)
            else {
                startActivity(intent)
                finish()
            }
        }
    }

    private fun postNewReport(id: String?, dayDiff: Long?, intent: Intent) {
        baseModel.addNewReport(id ?: "", (dayDiff ?: 1).toInt()) {
            startActivity(intent)
            finish()
        }
    }

    private fun goToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun changeColor(resourceColor: Int) {
        window.statusBarColor = ContextCompat.getColor(applicationContext, resourceColor)
        val bar: ActionBar? = supportActionBar
        bar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(applicationContext, resourceColor))
        )
    }
}