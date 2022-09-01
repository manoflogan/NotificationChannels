package com.krishnanand.mobile.notificationchannels.dagger

import android.app.Application
import com.krishnanand.mobile.notificationchannels.NotificationChannelsApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class
    ]
)
interface NotificationChannelsComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): NotificationChannelsComponent
    }

    fun inject(application: NotificationChannelsApplication)
}