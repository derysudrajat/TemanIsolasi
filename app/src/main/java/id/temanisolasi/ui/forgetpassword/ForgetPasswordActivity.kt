package id.temanisolasi.ui.forgetpassword

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import coil.load
import coil.transform.CircleCropTransformation
import id.temanisolasi.data.model.User
import id.temanisolasi.data.repo.remote.firebase.storage.StorageUserHelpers
import id.temanisolasi.databinding.ActivityForgetPasswordBinding
import id.temanisolasi.utils.Helpers
import id.temanisolasi.utils.Helpers.encodeName
import id.temanisolasi.utils.Helpers.getPlainText
import id.temanisolasi.utils.Helpers.hideView
import id.temanisolasi.utils.Helpers.showError
import id.temanisolasi.utils.Helpers.showView
import id.temanisolasi.utils.STORAGE.getAvaLocation
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgetPasswordActivity : AppCompatActivity() {

    private val model: ForgetPasswordViewModel by viewModel()

    private lateinit var binding: ActivityForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.onEmailAfterTextChange(binding.edtEmail)
        model.email.observe(this, Observer(this::validateEmail))

        binding.btnAction.setOnClickListener {
            model.setNewProgress(true)
        }
        binding.tvNotMyAccount.setOnClickListener {
            model.setNewProgress(false)
        }

        model.progress.observe(this, Observer(this::setUpProgress))
        model.user.observe(this, Observer(this::setDataUser))
        model.isEmailSent.observe(this, Observer(this::onEmailSent))
    }

    @SuppressLint("SetTextI18n")
    private fun onEmailSent(isSent: Boolean) = with(binding) {
        if (isSent) {
            tvMessage.text = """
                Klik link pada email untuk mengatur ulang password akunmu, setelah itu kamu bisa login menggunakan password barumu

                jangan sampai lupa lagi ya
            """.trimIndent()
            tvMessage.showView()
            listOf(layoutUser, line, tvNotMyAccount).forEach { it.hideView(true) }
            layoutEmail.hideView(true)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDataUser(user: User) = with(binding) {
        tvName.text = user.name
        user.img.let { img ->
            if (img != null) StorageUserHelpers.getImgUrl(
                user.id?.getAvaLocation(img) ?: ""
            ) { loadAva(it) }
            else loadAva(Helpers.getPlaceHolder(user.name?.encodeName() ?: "").toUri())
        }
        tvMessage.text =
            "Kami akan mengirim sebuah email yang berisi link untuk mengatur ulang passwordmu"
        listOf(layoutUser, tvMessage, tvNotMyAccount).forEach { it.showView() }
        tvTitleCard.hideView()
        layoutEmail.hideView(true)
    }

    private fun loadAva(it: Uri) {
        binding.ivAva.load(it) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    private fun setUpProgress(progress: Int) = with(binding) {
        if (progress < 3) {
            tvTitle.text = listOfTitle[progress]
            tvDesc.text = listOfDesc[progress]
            btnAction.text = listTextButton[progress]
        }
        when (progress) {
            0 -> {
                btnAction.isEnabled = false
                layoutEmail.showView()
                listOf(layoutUser, tvMessage).forEach { it.hideView() }
                tvNotMyAccount.hideView(true)
            }
            1 -> model.getUser(edtEmail.getPlainText())
            2 -> model.sentEmailVerification(edtEmail.getPlainText())
            else -> finish()
        }
    }

    private val listOfTitle = listOf(
        "Lupa  kata sandi?",
        "Ini akunmu?",
        "Email"
    )

    private val listOfDesc = listOf(
        "Jangan khawatir, silahkan masukan email untuk mencari akunmu",
        "Pastikan ini adalah akunmu ya",
        "Cek email mu, coba cek bagian spam dan promosi juga ya",
    )

    private val listTextButton = listOf(
        "Cari", "Kirim Verifikasi", "Login"
    )

    private fun validateEmail(it: String) = with(binding) {
        val isEmpty = it.isEmpty()
        if (!isEmpty) {
            val valid = Patterns.EMAIL_ADDRESS.matcher(it).matches()
            btnAction.isEnabled = valid
            if (valid) Helpers.validateError(tilEmail)
            else tilEmail.showError("Email tidak valid")
        } else tilEmail.showError()
    }
}