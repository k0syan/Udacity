package com.example.hydrationreminder;

import android.content.*;
import android.drm.DrmStore;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hydrationreminder.sync.ReminderTasks;
import com.example.hydrationreminder.sync.ReminderUtilities;
import com.example.hydrationreminder.sync.WaterReminderIntentService;
import com.example.hydrationreminder.utilities.NotificationUtils;
import com.example.hydrationreminder.utilities.PreferenceUtilities;

public class MainActivity extends AppCompatActivity implements
		SharedPreferences.OnSharedPreferenceChangeListener {

	private TextView mWaterCountDisplay;
	private TextView mChargingCountDisplay;
	private ImageView mChargingImageView;

	private Toast mToast;
	IntentFilter mChargingFilter;
	ChargingBroadcastReceiver mChargingReciever;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mWaterCountDisplay = (TextView) findViewById(R.id.tv_water_count);
		mChargingCountDisplay = (TextView) findViewById(R.id.tv_charging_reminder_count);
		mChargingImageView = (ImageView) findViewById(R.id.iv_power_increment);

		updateWaterCount();
		updateChargingReminderCount();

		ReminderUtilities.scheduleChargingReminder(this);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);

		mChargingFilter = new IntentFilter();
		mChargingFilter.addAction(Intent.ACTION_POWER_CONNECTED);
		mChargingFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

		mChargingReciever = new ChargingBroadcastReceiver();
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(mChargingReciever, mChargingFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mChargingReciever);
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

	private void showCharging(boolean isCharging) {
		if (isCharging) {
			mChargingImageView.setImageResource(R.drawable.ic_power_pink_80px);
		} else {
			mChargingImageView.setImageResource(R.drawable.ic_power_grey_80px);
		}
	}

	public void incrementWater(View view) {
		if (mToast != null) mToast.cancel();
		mToast = Toast.makeText(this, R.string.water_chug_toast, Toast.LENGTH_SHORT);
		mToast.show();

		Intent incrementWaterCountIntent = new Intent(this, WaterReminderIntentService.class);
		incrementWaterCountIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
		startService(incrementWaterCountIntent);
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

	private class ChargingBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			boolean isCharging = action.equals(Intent.ACTION_POWER_CONNECTED);
			showCharging(isCharging);
		}
	}
}
