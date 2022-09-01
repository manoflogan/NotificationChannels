package com.krishnanand.mobile.notificationchannels

import android.app.Application
import com.krishnanand.mobile.notificationchannels.dagger.DaggerNotificationChannelsComponent
import com.krishnanand.mobile.notificationchannels.notifications.NotificationsHelper
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class NotificationChannelsApplication: Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var notificationsHelper: NotificationsHelper

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerNotificationChannelsComponent.factory().create(this).inject(this)
        notificationsHelper.createNotificationChannel()
    }
}