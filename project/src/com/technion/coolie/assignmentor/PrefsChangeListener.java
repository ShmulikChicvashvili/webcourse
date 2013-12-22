package com.technion.coolie.assignmentor;

import android.preference.Preference;

public class PrefsChangeListener<T extends Preference> implements Preference.OnPreferenceChangeListener {

	protected final T pref;

	/**
	 * Construct a change listener for the specified widget.
	 */
	public PrefsChangeListener (T pref) {
		this.pref = pref;
	}

	/**
	 * Preference change callback.
	 */
	public boolean onPreferenceChange (Preference preference, Object newValue) {
		updateSummary( newValue );

		return true;
	}

	/**
	 * Update the summary text.
	 */
	protected void updateSummary (Object newValue) {
		pref.setSummary( newValue == null ? "" : String.valueOf( newValue ) );
	}
}