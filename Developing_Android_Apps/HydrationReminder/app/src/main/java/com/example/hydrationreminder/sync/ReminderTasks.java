package com.example.hydrationreminder.sync;

import android.content.Context;
import com.example.hydrationreminder.utilities.NotificationUtils;
import com.example.hydrationreminder.utilities.PreferenceUtilities;

public class ReminderTasks {

	public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";
	public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
	public static final String ACTION_CHARGING_REMINDER = "charging-reminder";

	public static void executeTask(Context context, String action) {
		if (action.equals(ACTION_INCREMENT_WATER_COUNT)) {
			incrementWaterCount(context);
		} else if (action.equals(ACTION_DISMISS_NOTIFICATION)) {
			NotificationUtils.clearAllNotifications(context);
		} else if (action.equals(ACTION_CHARGING_REMINDER)) {
			issueChargingReminder(context);
		}
	}

	private static void incrementWaterCount(Context context) {
		PreferenceUtilities.incrementWaterCount(context);
		NotificationUtils.clearAllNotifications(context);
	}

	private static void issueChargingReminder(Context context) {
		PreferenceUtilities.incrementChargingReminderCount(context);
		NotificationUtils.remindUserBecauseCharging(context);
	}
}
