package com.example.hydrationreminder.sync;

import android.content.Context;
import com.example.hydrationreminder.utilities.PreferenceUtilities;

public class ReminderTasks {

	public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";
	//  TODO (2) Add a public static constant called ACTION_DISMISS_NOTIFICATION

	private static void incrementWaterCount(Context context) {
		PreferenceUtilities.incrementWaterCount(context);
		//      TODO (4) If the water count was incremented, clear any notifications
	}

	public static void executeTask(Context context, String action) {
		if (action.equals(ACTION_INCREMENT_WATER_COUNT)) {
			incrementWaterCount(context);
		}
		//      TODO (3) If the user ignored the reminder, clear the notification
	}
}
