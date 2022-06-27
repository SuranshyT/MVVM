package kz.home.pincode.presentation

import android.app.Application
import kz.home.pincode.di.mainModule
import kz.home.pincode.di.modules
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin { modules(mainModule) }
    }
}