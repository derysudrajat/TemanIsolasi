package id.temanisolasi.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import id.temanisolasi.data.model.User
import id.temanisolasi.databinding.ActivityRegisterBinding
import id.temanisolasi.ui.base.MainActivity
import id.temanisolasi.ui.login.LoginActivity
import id.temanisolasi.utils.Helpers
import id.temanisolasi.utils.Helpers.afterTextChanged
import id.temanisolasi.utils.Helpers.getPlainText
import id.temanisolasi.utils.Helpers.showError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val model: RegisterViewModel by viewModel()
    private var isGoogleLogin = false

    @FlowPreview
    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.getParcelable<User>(EXTRA_CREATE)?.let { user ->
            isGoogleLogin = true
            with(binding) {
                edtEmail.setText(user.email)
                tilEmail.isEnabled = false

                edtName.setText(user.name)
                model.setProgress(0, true)
                model.setProgress(1, true)
            }
        }

        with(binding) {
            lifecycleScope.launch { edtEmail.afterTextChanged { validateEmail(it) } }
            lifecycleScope.launch { edtName.afterTextChanged { validateName(it) } }
            lifecycleScope.launch { edtPassword.afterTextChanged { validatePassword(it) } }
            lifecycleScope.launch { edtConfirmPassword.afterTextChanged { validateConfirmPassword(it) } }

            model.isComplete.observe(this@RegisterActivity) { btnRegister.isEnabled = it }

            btnRegister.setOnClickListener {
                val user = User(edtName.getPlainText(), edtEmail.getPlainText())
                if (isGoogleLogin) model.recordDataUser(user, this@RegisterActivity) {
                    toMainActivity()
                } else model.createAccount(
                    user, edtPassword.getPlainText(), this@RegisterActivity
                ) { toMainActivity() }
            }

            btnLogin.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun toMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
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

    private fun validateName(it: String) = with(binding) {
        val valid = it.isEmpty()
        model.setProgress(1, !valid)
        if (valid) tilName.showError()
        else Helpers.validateError(tilName)
    }

    private fun validatePassword(it: String) = with(binding) {
        val valid = it.isEmpty()
        model.setProgress(2, !valid)
        if (valid) tilPassword.showError()
        else Helpers.validateError(tilPassword)
    }

    private fun validateConfirmPassword(it: String) = with(binding) {
        val isEmpty = it.isEmpty()
        if (!isEmpty) {
            val valid = it == edtPassword.getPlainText()
            model.setProgress(3, valid)
            if (valid) Helpers.validateError(tilConfirmPassword)
            else tilConfirmPassword.showError("Password tidak sama")
        } else tilConfirmPassword.showError()
    }

    companion object {
        const val EXTRA_CREATE = "extra_create"
    }
}