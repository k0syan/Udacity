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
import com.example.hydrationreminder.sync.ReminderTasks;
import com.example.hydrationreminder.sync.WaterReminderIntentService;

public class NotificationUtils {

	private static final int WATER_REMINDER_NOTIFICATION_ID = 1138;

	private static final int WATER_REMINDER_PENDING_INTENT_ID = 1234;

	private static final int ACTION_IGNORE_PENDING_INTENT_ID = 9943;

	private static final int ACTION_DRINK_PENDING_INTENT_ID = 6587;

	public static void clearAllNotifications(Context context) {
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
	}

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
				.addAction(drinkWaterAction(context))
				.addAction(ignoreReminderAction(context))
				.setAutoCancel(true);

		notificationBuilder.setPriority(NotificationManager.IMPORTANCE_MAX);
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
	}

	public static NotificationCompat.Action ignoreReminderAction(Context context) {
		Intent intent = new Intent(context, WaterReminderIntentService.class);
		intent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);
		PendingIntent pendingIntent = PendingIntent.getService(
				context,
				ACTION_IGNORE_PENDING_INTENT_ID,
				intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.ic_cancel_black_24px, "No Thanks", pendingIntent);
		return action;
	}

	public static NotificationCompat.Action drinkWaterAction(Context context) {
		Intent intent = new Intent(context, WaterReminderIntentService.class);
		intent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
		PendingIntent pendingIntent = PendingIntent.getService(
				context,
				ACTION_DRINK_PENDING_INTENT_ID,
				intent,
				PendingIntent.FLAG_UPDATE_CURRENT
		);
		NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.ic_local_drink_black_24px, "I did it!", pendingIntent);
		return action;
	}

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
