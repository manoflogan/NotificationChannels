package com.krishnanand.mobile.notificationchannels.dagger

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.core.content.getSystemService
import com.krishnanand.mobile.notificationchannels.MainActivity
import com.krishnanand.mobile.notificationchannels.notifications.NotificationSettingReceiver
import com.krishnanand.mobile.notificationchannels.notifications.NotificationsHelper
import com.krishnanand.mobile.notificationchannels.notifications.NotificationsHelperImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
interface AppModule {

    @Binds
    fun bindApplication(application: Application): Context

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    @Binds
    fun bindNotificationChannelCreator(instance: NotificationsHelperImpl): NotificationsHelper

    @ContributesAndroidInjector
    fun contributeNotificationSettingsReceiver(): NotificationSettingReceiver

    companion object {

        @Provides
        fun provideNotificationManager(context: Context): NotificationManager? = context.getSystemService()

        @Provides
        fun provideContextResources(context: Context) = context.resources
    }
}