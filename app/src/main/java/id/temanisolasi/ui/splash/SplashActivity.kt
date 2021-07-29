package id.temanisolasi.ui.splash

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.temanisolasi.R
import id.temanisolasi.data.model.User
import id.temanisolasi.ui.base.MainActivity
import id.temanisolasi.ui.base.home.HomeViewModel
import id.temanisolasi.ui.login.LoginActivity
import id.temanisolasi.ui.register.RegisterActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashActivity : AppCompatActivity() {

    private val model: HomeViewModel by viewModel()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
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
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun goToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun changeColor(resourceColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, resourceColor)
        }
        val bar: ActionBar? = supportActionBar
        bar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(applicationContext, resourceColor))
        )
    }
}