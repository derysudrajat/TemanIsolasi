package id.temanisolasi.di

import id.temanisolasi.data.repo.remote.firebase.auth.AuthRepository
import id.temanisolasi.data.repo.remote.firebase.firestore.FirestoreUserRepository
import id.temanisolasi.ui.login.LoginViewModel
import id.temanisolasi.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    single { AuthRepository() }
    single { FirestoreUserRepository() }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
}