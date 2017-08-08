package com.example.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

	private void setPreferenceSummary(Preference preference, Object value) {
		String stringValue = value.toString();
		String key = preference.getKey();

		if (preference instanceof ListPreference) {
			ListPreference listPreference = (ListPreference) preference;
			int index = listPreference.findIndexOfValue(stringValue);

			if (index >= 0) {
				preference.setSummary(listPreference.getEntries()[index]);
			}
		} else if (!(preference instanceof CheckBoxPreference)) {
			preference.setSummary(stringValue);
		}

	}

	@Override
	public void onStart() {
		super.onStart();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		addPreferencesFromResource(R.xml.pref_general);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Preference preference = findPreference(key);

		if (!(preference instanceof CheckBoxPreference)) {
			String value = sharedPreferences.getString(preference.getKey(), "");
			setPreferenceSummary(preference, value);
		}
	}
}
