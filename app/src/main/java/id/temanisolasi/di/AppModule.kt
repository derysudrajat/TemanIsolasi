package id.temanisolasi.di

import id.temanisolasi.data.repo.remote.firebase.auth.AuthRepository
import id.temanisolasi.data.repo.remote.firebase.firestore.FirestoreUserRepository
import id.temanisolasi.data.repo.remote.firebase.firestore.isolation.FirestoreIsolationRepository
import id.temanisolasi.ui.base.home.HomeViewModel
import id.temanisolasi.ui.login.LoginViewModel
import id.temanisolasi.ui.register.RegisterViewModel
import id.temanisolasi.ui.startisolation.IsolationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    single { AuthRepository() }
    single { FirestoreUserRepository() }
    single { FirestoreIsolationRepository() }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { IsolationViewModel(get(), get()) }
}