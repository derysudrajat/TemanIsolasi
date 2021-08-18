package id.temanisolasi.di

import id.temanisolasi.data.repo.remote.firebase.auth.AuthRepository
import id.temanisolasi.data.repo.remote.firebase.firestore.game.FirestoreGamesRepository
import id.temanisolasi.data.repo.remote.firebase.firestore.guide.FirestoreGuideRepository
import id.temanisolasi.data.repo.remote.firebase.firestore.isolation.FirestoreIsolationRepository
import id.temanisolasi.data.repo.remote.firebase.firestore.user.FirestoreUserRepository
import id.temanisolasi.data.repo.remote.firebase.storage.StorageUserRepository
import id.temanisolasi.ui.base.BaseViewModel
import id.temanisolasi.ui.base.games.GamesViewModel
import id.temanisolasi.ui.base.guide.detail.DetailGuideActivityViewModel
import id.temanisolasi.ui.base.home.HomeViewModel
import id.temanisolasi.ui.base.inputdata.InputDataViewModel
import id.temanisolasi.ui.base.profile.editprofile.EditProfileViewModel
import id.temanisolasi.ui.finishisolation.FinishIsolationViewModel
import id.temanisolasi.ui.login.LoginViewModel
import id.temanisolasi.ui.register.RegisterViewModel
import id.temanisolasi.ui.startisolation.IsolationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    single { AuthRepository() }
    single { StorageUserRepository() }
    single { FirestoreUserRepository() }
    single { FirestoreIsolationRepository() }
    single { FirestoreGuideRepository() }
    single { FirestoreGamesRepository() }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { IsolationViewModel(get(), get()) }
    viewModel { EditProfileViewModel(get(), get()) }
    viewModel { BaseViewModel(get()) }
    viewModel { InputDataViewModel(get()) }
    viewModel { FinishIsolationViewModel(get(), get()) }
    viewModel { DetailGuideActivityViewModel(get()) }
    viewModel { GamesViewModel(get()) }
}