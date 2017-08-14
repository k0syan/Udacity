package com.example.hydrationreminder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hydrationreminder.sync.ReminderTasks;
import com.example.hydrationreminder.sync.WaterReminderIntentService;
import com.example.hydrationreminder.utilities.NotificationUtils;
import com.example.hydrationreminder.utilities.PreferenceUtilities;

public class MainActivity extends AppCompatActivity implements
		SharedPreferences.OnSharedPreferenceChangeListener {

	private TextView mWaterCountDisplay;
	private TextView mChargingCountDisplay;
	private ImageView mChargingImageView;

	private Toast mToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mWaterCountDisplay = (TextView) findViewById(R.id.tv_water_count);
		mChargingCountDisplay = (TextView) findViewById(R.id.tv_charging_reminder_count);
		mChargingImageView = (ImageView) findViewById(R.id.iv_power_increment);

		updateWaterCount();
		updateChargingReminderCount();

		// TODO (23) Schedule the charging reminder

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
	}

	private void updateWaterCount() {
		int waterCount = PreferenceUtilities.getWaterCount(this);
		mWaterCountDisplay.setText(waterCount+"");
	}

	private void updateChargingReminderCount() {
		int chargingReminders = PreferenceUtilities.getChargingReminderCount(this);
		String formattedChargingReminders = getResources().getQuantityString(
				R.plurals.charge_notification_count, chargingReminders, chargingReminders);
		mChargingCountDisplay.setText(formattedChargingReminders);
	}

	public void incrementWater(View view) {
		if (mToast != null) mToast.cancel();
		mToast = Toast.makeText(this, R.string.water_chug_toast, Toast.LENGTH_SHORT);
		mToast.show();

		Intent incrementWaterCountIntent = new Intent(this, WaterReminderIntentService.class);
		incrementWaterCountIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
		startService(incrementWaterCountIntent);
	}

	// TODO (24) Remove the button and testNotification code
	public void testNotification(View view) {
		NotificationUtils.remindUserBecauseCharging(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (PreferenceUtilities.KEY_WATER_COUNT.equals(key)) {
			updateWaterCount();
		} else if (PreferenceUtilities.KEY_CHARGING_REMINDER_COUNT.equals(key)) {
			updateChargingReminderCount();
		}
	}
}