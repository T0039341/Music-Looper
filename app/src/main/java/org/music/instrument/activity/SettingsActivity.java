package org.music.instrument.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import org.music.instrument.R;

/**
 * Created by GAGAN.
 */
public class SettingsActivity extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
