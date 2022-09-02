## Motivation

This is a proof of concept to log tickets for the observed issues when the app targeting SDK older than 33 is installed on either Android T device or an emulator.

If the app target SDK < 33, the push notification permission dialog is shown as soon as the app creates notification channels.


## Issue # 1: Apps don't have the ability to find out what choice you have made when you either grant permission, or deny permission, or dismiss the dialog

### Steps to reproduce

* Install the app in Android T emulator or a device. You should see a permission dialog on the home screen as soon as the app is launched.
* Swipe the permission dialog to dismiss it or close the app
* Relaunch the app.
* Permission dialog is shown again and again until you choose to explicitly grant permission, or not grant permission.

If the app target SDK < 33, then permission dialog is shown repeatedly until you choose to either grant or deny or dismiss push notifications' permission dialog.

If there were a dialog fragment to be shown on the home screen, then there would no way for the app to know that the dialog will be obscured by the permission dialog.

The real world use case is follows:

We are legally obligated to show the GDPR dialog should certain conditions be satisfied. If you were to choose to grant or deny permission explicitly, then permission dialog would be shown repeatedly until a choice has been made. It is likely that GDPR dialog would be hidden by the native permission dialog

If apps had the ability to determine what choice has been made then app could choose to take or not take certain actions.

## Issue # 2: Android does not dispatch the broadcast event [`android.app.action.APP_BLOCK_STATE_CHANGED`](https://developer.android.com/reference/android/app/NotificationManager#ACTION_APP_BLOCK_STATE_CHANGED)

### Steps to reproduce

* Install the app in Android T emulator or device
* Open the Settings App -> Apps -> Notification Channels -> Enable push notifications
* Monitor log cat. The app should log an info statement as follows `The blocked state = true` when the push notifications are enabled
* If you were to keep toggling the push notification settings, the log statement `The blocked state = ${toggleState}` is displayed in logcat every time the push notification state is toggled.
* Open the app once again. The permission dialog has disappeared since the user has toggled the push notification permissions.
* Uninstall and then reinstall the app on Android T emulator or a device. You should see a permission dialog to grant push notification on the home screen as soon as the app is launched.
* Grant permission to enable push notification.
* The broadcast event `android.app.action.APP_BLOCK_STATE_CHANGED` is not dispatched by the OS.

Our case is as follows:

We would like to send some logging information to our backend when the user makes a decision either way. This is not possible if the platform does not dispatch the `android.app.action.APP_BLOCK_STATE_CHANGED`