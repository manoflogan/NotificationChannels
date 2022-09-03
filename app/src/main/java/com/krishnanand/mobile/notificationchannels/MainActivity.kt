package com.krishnanand.mobile.notificationchannels

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.krishnanand.mobile.notificationchannels.databinding.ActivityMainBinding
import com.krishnanand.mobile.notificationchannels.notifications.NotificationsHelper
import com.krishnanand.mobile.notificationchannels.notifications.PushNotificationPermissionRationaleDialogFragment
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var activityMainBinding: ActivityMainBinding
    lateinit var layout : View

    @Inject
    lateinit var notificationsHelper: NotificationsHelper

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        Snackbar.make(
            layout,
            if (isGranted) {
                R.string.notification_permission_permission_approval_access
            } else {
                R.string.notification_permission_permission_denied_access
             }, Snackbar.LENGTH_LONG).apply {
            setAction(android.R.string.ok) {
                this@apply.dismiss()
            }.show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        layout = activityMainBinding.root
        setContentView(layout)
        activityMainBinding.buttonSendNotification.setOnClickListener {
            notificationsHelper.createAndDispatchNotification()
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(layout, R.string.notification_permission_permission_already_granted, Snackbar.LENGTH_LONG).show()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            // Show the dialog if present
            var fragment = supportFragmentManager.findFragmentByTag(
                PushNotificationPermissionRationaleDialogFragment.PERMISSION_TAG
            ) as PushNotificationPermissionRationaleDialogFragment?
            if (fragment == null) {
                fragment = PushNotificationPermissionRationaleDialogFragment()
                fragment.show(
                    supportFragmentManager,
                    PushNotificationPermissionRationaleDialogFragment.PERMISSION_TAG
                )
            }
        } else {
            Snackbar.make(layout, R.string.notification_permission_permission_not_granted, Snackbar.LENGTH_LONG).show()
        }
    }
}