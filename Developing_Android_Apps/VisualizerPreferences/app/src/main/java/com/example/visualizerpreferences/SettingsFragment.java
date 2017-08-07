package com.example.visualizerpreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragmentCompat implements OnSharedPreferenceChangeListener {

	@Override
	public void onCreatePreferences(Bundle bundle, String s) {

		addPreferencesFromResource(R.xml.pref_visualizer);

		SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
		PreferenceScreen preferenceScreen = getPreferenceScreen();

		int count = preferenceScreen.getPreferenceCount();

		for (int i = 0; i < count; ++i) {
			Preference p = preferenceScreen.getPreference(i);
			if (!(p instanceof CheckBoxPreference)) {
				String value = sharedPreferences.getString(p.getKey(), "");
				setPreferenceSummary(p, value);
			}
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Preference preference = findPreference(key);
		if (null != preference) {
			if (!(preference instanceof CheckBoxPreference)) {
				String value = sharedPreferences.getString(preference.getKey(), "");
				setPreferenceSummary(preference, value);
			}
		}
	}

	private void setPreferenceSummary(Preference preference, String value) {
		if (preference instanceof ListPreference) {
			ListPreference listPreference = (ListPreference) preference;
			int index = listPreference.findIndexOfValue(value);
			if (index >= 0) {
				listPreference.setSummary(listPreference.getEntries()[index]);
			}
		} else if (preference instanceof EditTextPreference) {
			preference.setSummary(value);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
}
