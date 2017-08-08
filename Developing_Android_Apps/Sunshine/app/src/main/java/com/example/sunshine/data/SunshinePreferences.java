package com.example.sunshine.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.example.sunshine.R;

public class SunshinePreferences {

	public static final String PREF_CITY_NAME = "city_name";

	public static final String PREF_COORD_LAT = "coord_lat";
	public static final String PREF_COORD_LONG = "coord_long";

	private static final String DEFAULT_WEATHER_LOCATION = "94043,USA";
	private static final double[] DEFAULT_WEATHER_COORDINATES = {37.4284, 122.0724};

	private static final String DEFAULT_MAP_LOCATION =
			"1600 Amphitheatre Parkway, Mountain View, CA 94043";

	static public void setLocationDetails(Context c, String cityName, double lat, double lon) {
	}

	static public void setLocation(Context c, String locationSetting, double lat, double lon) {
	}

	static public void resetLocationCoordinates(Context c) {
	}

	public static String getPreferredWeatherLocation(Context context) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		String key = context.getString(R.string.pref_location_key);
		String defaultKey = context.getString(R.string.pref_location_default);

		return sharedPreferences.getString(key, defaultKey);
	}

	public static boolean isMetric(Context context) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		String keyForUnits = context.getString(R.string.pref_units_key);
		String defaultUnits = context.getString(R.string.pref_units_metric);
		String preferredUnits = sharedPreferences.getString(keyForUnits, defaultUnits);
		String metric = context.getString(R.string.pref_units_metric);

		return metric.equals(preferredUnits);
	}

	public static double[] getLocationCoordinates(Context context) {
		return getDefaultWeatherCoordinates();
	}

	public static boolean isLocationLatLonAvailable(Context context) {
		return false;
	}

	private static String getDefaultWeatherLocation() {
		return DEFAULT_WEATHER_LOCATION;
	}

	public static double[] getDefaultWeatherCoordinates() {
		return DEFAULT_WEATHER_COORDINATES;
	}
}