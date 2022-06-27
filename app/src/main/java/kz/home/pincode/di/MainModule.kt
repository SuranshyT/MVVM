package kz.home.pincode.di

import androidx.lifecycle.SavedStateHandle
import kz.home.pincode.data.PinCodeMapper
import kz.home.pincode.data.RemoteRepository
import kz.home.pincode.domain.GetPinCodeUseCase
import kz.home.pincode.domain.Repository
import kz.home.pincode.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.math.sin

val mainModule = module {
    single { PinCodeMapper() }
    single<Repository> { RemoteRepository(get()) }
    single { GetPinCodeUseCase(repository = get()) }
    viewModel { MainViewModel(useCase =  get() , stateHandle = get()) }
    factory { SavedStateHandle() }
    //viewModel { (handle: SavedStateHandle) -> MainViewModel(get(), handle) }
}

val modules = listOf(mainModule)