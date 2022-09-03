package com.krishnanand.mobile.notificationchannels.notifications

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.krishnanand.mobile.notificationchannels.MainActivity
import com.krishnanand.mobile.notificationchannels.R
import dagger.android.support.AndroidSupportInjection

/**
 * Shows the rationale for which the user is to display push notifications.
 */
class PushNotificationPermissionRationaleDialogFragment: DialogFragment() {

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val fragmentActivity = requireActivity() as MainActivity
        return AlertDialog.Builder(fragmentActivity).setTitle(
            R.string.notification_permission_dialog_title
        ).setMessage(
            R.string.notification_permission_dialog_message
        ).setPositiveButton(android.R.string.ok) { dialog, which: Int ->
            dialog.dismiss()
            handleClickAction(fragmentActivity, which)
        }.setNeutralButton(R.string.notification_permission_negative_maybe_later) { dialog, which: Int ->
            dialog.dismiss()
            handleClickAction(fragmentActivity, which)
        }.create()
    }

    private fun handleClickAction(activity: MainActivity, which: Int) {
        when(which) {
            DialogInterface.BUTTON_POSITIVE -> {
                // The user has expressed interest in enabling push notifications. request permission
                activity.requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            DialogInterface.BUTTON_NEUTRAL -> {
                // Try againi later then.
               Snackbar.make(activity.layout, R.string.notification_permission_permission_maybe_later, Snackbar.LENGTH_LONG).show()
            }
            else -> {
                // NOOP
            }
        }
    }

    companion object {
        const val PERMISSION_TAG = "permissionsTag"
    }
}