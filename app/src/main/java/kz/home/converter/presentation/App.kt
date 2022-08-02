package kz.home.converter.presentation

import android.app.Application
import kz.home.converter.di.modules
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(modules)
        }
    }
}