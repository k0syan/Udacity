package com.example.hydrationreminder.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import com.example.hydrationreminder.MainActivity;
import com.example.hydrationreminder.R;

public class NotificationUtils {

	private static final int WATER_REMINDER_NOTIFICATION_ID = 1138;

	private static final int WATER_REMINDER_PENDING_INTENT_ID = 1234;

	//  TODO (1) Create a method to clear all notifications

	public static void remindUserBecauseCharging(Context context) {
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
				.setColor(ContextCompat.getColor(context, R.color.colorPrimary))
				.setSmallIcon(R.drawable.ic_drink_notification)
				.setLargeIcon(largeIcon(context))
				.setContentTitle(context.getString(R.string.charging_reminder_notification_title))
				.setContentText(context.getString(R.string.charging_reminder_notification_body))
				.setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.charging_reminder_notification_body)))
				.setDefaults(Notification.DEFAULT_VIBRATE)
				.setContentIntent(contentIntent(context))
				.setAutoCancel(true);

		notificationBuilder.setPriority(NotificationManager.IMPORTANCE_MAX);
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
	}

	//  TODO (5) Add a static method called ignoreReminderAction
	//      TODO (6) Create an Intent to launch WaterReminderIntentService
	//      TODO (7) Set the action of the intent to designate you want to dismiss the notification
	//      TODO (8) Create a PendingIntent from the intent to launch WaterReminderIntentService
	//      TODO (9) Create an Action for the user to ignore the notification (and dismiss it)
	//      TODO (10) Return the action


	//  TODO (11) Add a static method called drinkWaterAction
	//      TODO (12) Create an Intent to launch WaterReminderIntentService
	//      TODO (13) Set the action of the intent to designate you want to increment the water count
	//      TODO (14) Create a PendingIntent from the intent to launch WaterReminderIntentService
	//      TODO (15) Create an Action for the user to tell us they've had a glass of water
	//      TODO (16) Return the action

	public static PendingIntent contentIntent(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		return PendingIntent.getActivity(
				context,
				WATER_REMINDER_PENDING_INTENT_ID,
				intent,
				PendingIntent.FLAG_UPDATE_CURRENT
		);
	}

	public static Bitmap largeIcon(Context context) {
		Resources res = context.getResources();
		return BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px);
	}
}
