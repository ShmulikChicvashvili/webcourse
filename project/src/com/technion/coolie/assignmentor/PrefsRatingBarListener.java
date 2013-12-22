package com.technion.coolie.assignmentor;

import com.technion.coolie.assignmentor.RatingPreference.AbstractRatingBarListener;
import com.sileria.android.Resource;

public class PrefsRatingBarListener extends AbstractRatingBarListener {

	private final int str;

	/**
	 * Construct a change listener for the specified widget.
	 */
	public PrefsRatingBarListener (RatingPreference pref) {
		this( pref, 0 );
	}

	/**
	 * Construct a change listener for the specified widget.
	 * @param pref SeekBarPreference object
	 * @param strResId string resource id that takes a integer argument
	 */
	public PrefsRatingBarListener (RatingPreference pref, int strResId) {
		super( pref );
		str = strResId;
		pref.setSummary( toSummary( pref.getRating() ) );
	}

	/**
	 * Update the summary.
	 * @param newValue Integer value
	 */
	@Override
	protected void updateSummary (Object newValue) {
		setSummary( toSummary( newValue ) );
	}

	/**
	 * Convert integer progress to summary string.
	 * @param newValue should be an Integer instance
	 */
	@Override
	protected String toSummary (Object newValue) {
		float ratingf = newValue instanceof Float ? (Float)newValue : 0;
		int rating = (int) ratingf;
		return str != 0 ? Resource.getString( str, rating ) : String.valueOf(rating);
	}
}
