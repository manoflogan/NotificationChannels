package com.krishnanand.mobile.notificationchannels.notifications

/**
 * An implementation of this interface creates a notification channel on start up.
 */
interface NotificationsHelper {

    /**
     * Creates a notification channel on start up.
     */
    fun createNotificationChannel()

    /**
     * Create and dispatch notification.
     */
    fun createAndDispatchNotification()
}