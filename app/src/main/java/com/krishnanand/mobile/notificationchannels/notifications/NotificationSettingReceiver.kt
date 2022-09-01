package com.krishnanand.mobile.notificationchannels.notifications

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log

import dagger.android.DaggerBroadcastReceiver

class NotificationSettingReceiver: DaggerBroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if ( intent?.action?.equals(NotificationManager.ACTION_APP_BLOCK_STATE_CHANGED) == true) {
            Log.i(TAG, "The blocked state = ${intent.getBooleanExtra(NotificationManager.EXTRA_BLOCKED_STATE, false)}")
        }
    }

    companion object {
        private const val TAG = "NotificationChannels"
    }
}