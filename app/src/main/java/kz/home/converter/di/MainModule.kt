package kz.home.converter.di

import kz.home.converter.data.RepositoryImpl
import kz.home.converter.domain.Repository
import kz.home.converter.presentation.converter.ConverterViewModel
import kz.home.converter.presentation.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { SearchViewModel() }
    viewModel { ConverterViewModel(get()) }

    factory<Repository> { RepositoryImpl(get()) }
}

val modules = listOf(mainModule, networkModule)