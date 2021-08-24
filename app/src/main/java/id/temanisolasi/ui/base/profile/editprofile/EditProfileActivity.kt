package id.temanisolasi.ui.base.profile.editprofile

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.temanisolasi.R
import id.temanisolasi.data.model.User
import id.temanisolasi.databinding.ActivityEditProfileBinding
import id.temanisolasi.utils.COLLECTION
import id.temanisolasi.utils.Helpers
import id.temanisolasi.utils.Helpers.afterTextChanged
import id.temanisolasi.utils.Helpers.encodeName
import id.temanisolasi.utils.Helpers.getPlainText
import id.temanisolasi.utils.Helpers.handleImagePicker
import id.temanisolasi.utils.Helpers.showError
import id.temanisolasi.utils.Helpers.validateError
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val model: EditProfileViewModel by viewModel()
    private var updateState: UPDATE? = null
    private var avaUrl = ""
    private var previousName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        val uid = Firebase.auth.currentUser?.uid ?: ""
        Firebase.firestore.collection(COLLECTION.USER)
            .document(uid)
            .get()
            .addOnSuccessListener { doc ->
                doc.toObject(User::class.java)?.let { user ->
                    previousName = user.name ?: ""

                    with(binding) {

                        lifecycleScope.launch { edtName.afterTextChanged { validateName(it) } }

                        user.img.let { img ->
                            if (img != null) model.getImgUrl(user.id ?: "", img) {
                                loadAva(it)
                            } else loadAva(
                                Helpers.getPlaceHolder(user.name?.encodeName() ?: "").toUri()
                            )
                        }

                        edtName.setText(user.name)
                        btnChangePhoto.setOnClickListener {
                            ImagePicker.with(this@EditProfileActivity)
                                .cropSquare()
                                .createIntent {
                                    activityForResult.launch(it)
                                }
                        }

                        btnSave.setOnClickListener {
                            when (updateState) {
                                UPDATE.PHOTO_NAME -> model.updateData(
                                    this@EditProfileActivity, user.apply {
                                        name = edtName.getPlainText()
                                        img = avaUrl
                                    }, true
                                )
                                UPDATE.NAME -> model.updateData(
                                    this@EditProfileActivity, user.apply {
                                        name = edtName.getPlainText()
                                    }, false
                                )
                                UPDATE.PHOTO -> model.updateData(
                                    this@EditProfileActivity, user.apply {
                                        img = avaUrl
                                    }, true
                                )
                            }
                        }
                    }
                }
            }
    }

    private fun loadAva(it: Uri) = with(binding) {
        ivAva.load(it) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    private val activityForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.handleImagePicker(this) {
            avaUrl = it.toString()
            activeUpdate(2)
            binding.ivAva.load(it) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun validateName(it: String) = with(binding) {
        val valid = it.isNotEmpty()
        btnSave.isEnabled = valid
        if (valid) {
            validateError(tilName)
            when {
                it != previousName && avaUrl.isNotBlank() -> activeUpdate(0)
                it != previousName -> activeUpdate(1)
                avaUrl.isNotBlank() -> activeUpdate(2)
                else -> btnSave.isEnabled = false
            }
        } else tilName.showError()
    }

    private fun activeUpdate(condition: Int) {
        binding.btnSave.isEnabled = true
        updateState = when (condition) {
            0 -> UPDATE.PHOTO_NAME
            1 -> UPDATE.NAME
            2 -> UPDATE.PHOTO
            else -> null
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private enum class UPDATE {
            PHOTO_NAME, PHOTO, NAME
        }

    }
}