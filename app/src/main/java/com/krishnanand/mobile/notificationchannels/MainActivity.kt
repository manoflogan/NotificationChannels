package com.krishnanand.mobile.notificationchannels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.krishnanand.mobile.notificationchannels.databinding.ActivityMainBinding
import com.krishnanand.mobile.notificationchannels.notifications.NotificationsHelper
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    @Inject
    lateinit var notificationsHelper: NotificationsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.buttonSendNotification.setOnClickListener {
            notificationsHelper.createAndDispatchNotification()
        }
    }
}