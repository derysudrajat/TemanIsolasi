package id.temanisolasi.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import id.temanisolasi.data.repo.remote.firebase.auth.AuthHelpers
import id.temanisolasi.databinding.ActivityLoginBinding
import id.temanisolasi.ui.base.MainActivity
import id.temanisolasi.ui.register.RegisterActivity
import id.temanisolasi.utils.Helpers
import id.temanisolasi.utils.Helpers.afterTextChanged
import id.temanisolasi.utils.Helpers.getPlainText
import id.temanisolasi.utils.Helpers.showError
import id.temanisolasi.utils.LoginState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val model: LoginViewModel by viewModel()

    @FlowPreview
    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInGoogle.setOnClickListener {
            gsoResult.launch(AuthHelpers.getGoogleClient(this).signInIntent)
        }

        with(binding) {
            lifecycleScope.launch {
                edtEmail.afterTextChanged { validateEmail(it) }
            }

            lifecycleScope.launch {
                edtPassword.afterTextChanged { validatePassword(it) }
            }

            btnLogin.setOnClickListener {
                model.loginWithEmailPassword(
                    edtEmail.getPlainText(),
                    edtPassword.getPlainText()
                )
            }

            btnRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }
        }

        model.isComplete.observe(this) {
            binding.btnLogin.isEnabled = it
        }

        model.isLoginSuccess.observe(this) { state ->
            when (state) {
                LoginState.SUCCESS -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                LoginState.USER_NOT_FOUND -> binding.tilEmail.showError("User tidak ditemukan")
                LoginState.WRONG_PASSWORD -> binding.tilPassword.showError("Password salah")
            }
        }

    }

    @ExperimentalCoroutinesApi
    private val gsoResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }
    }

    @ExperimentalCoroutinesApi
    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        task.getResult(ApiException::class.java)?.let {
            model.loginWithGoogle(it, this)
        }
    }

    private fun validatePassword(it: String) = with(binding) {
        val valid = it.isEmpty()
        model.setProgress(1, !valid)
        if (valid) tilPassword.showError()
        else Helpers.validateError(tilPassword)
    }

    private fun validateEmail(it: String) = with(binding) {
        val isEmpty = it.isEmpty()
        if (!isEmpty) {
            val valid = Patterns.EMAIL_ADDRESS.matcher(it).matches()
            model.setProgress(0, valid)
            if (valid) Helpers.validateError(tilEmail)
            else tilEmail.showError("Email tidak valid")
        } else tilEmail.showError()
    }

}