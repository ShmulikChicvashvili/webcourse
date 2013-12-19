package com.technion.coolie.assignmentor;

import com.sileria.android.Tools;
import com.sileria.android.event.PrefsChangeListener;
import com.sileria.android.view.SeekBarPreference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingPreference extends Preference implements RatingBar.OnRatingBarChangeListener {

	private RatingBar ratingBar;
	private int mRate;
	private int max = 5;
	private TextView summary;
	private boolean discard;

	/**
	 * Perform inflation from XML and apply a class-specific base style. 
	 *
	 * @param context The Context this is associated with, through which it can
	 *            access the current theme, resources, {@link android.content.SharedPreferences},
	 *            etc.
	 * @param attrs The attributes of the XML tag that is inflating the preference.
	 * @param defStyle The default style to apply to this preference. If 0, no style
	 *            will be applied (beyond what is included in the theme). This
	 *            may either be an attribute resource, whose value will be
	 *            retrieved from the current theme, or an explicit style
	 *            resource.
	 * @see #RatingPreference(Context, AttributeSet)
	 */
	public RatingPreference (Context context, AttributeSet attrs, int defStyle) {
		super( context, attrs, defStyle );
	}

	/**
	 * Constructor that is called when inflating a Preference from XML.
	 *
	 * @param context The Context this is associated with, through which it can
	 *            access the current theme, resources, {@link android.content.SharedPreferences},
	 *            etc.
	 * @param attrs The attributes of the XML tag that is inflating the
	 *            preference.
	 * @see #RatingPreference(Context, AttributeSet, int)
	 */
	public RatingPreference (Context context, AttributeSet attrs) {
		super( context, attrs );
	}

	/**
	 * Constructor to create a Preference.
	 *
	 * @param context The Context in which to store Preference values.
	 */
	public RatingPreference (Context context) {
		super( context );
	}

	/**
	 * Create progress bar and other view contents.
	 */
	protected View onCreateView (ViewGroup p) {

		final Context ctx = getContext();
		final Tools T = new Tools( ctx );

		LinearLayout layout = new LinearLayout( ctx );
		layout.setId( android.R.id.widget_frame );
		layout.setOrientation( LinearLayout.VERTICAL );
		T.setPadding( layout, 6, 10, 6, 10 );

		TextView title = new TextView( ctx );
		int textColor = title.getCurrentTextColor();
		title.setId( android.R.id.title );
		title.setSingleLine();
		title.setTextAppearance( ctx, android.R.style.TextAppearance_Medium );
		title.setTextColor( textColor );
		layout.addView( title );

		ratingBar = new RatingBar( ctx );
		
		ratingBar.setId( android.R.id.progress );
		
		ratingBar.setNumStars(5);
		ratingBar.setStepSize(1f);
		ratingBar.setMax( max );
		ratingBar.setOnRatingBarChangeListener( this );
		layout.addView( ratingBar );

		summary = new TextView( ctx );
		summary.setId( android.R.id.summary );
		summary.setSingleLine();
		summary.setTextAppearance( ctx, android.R.style.TextAppearance_Small );
		summary.setTextColor( textColor );
		layout.addView( summary );

		return layout;
	}

	/**
	 * Binds the created View to the data for this Preference.
	 */
	@Override
	protected void onBindView (View view) {
		super.onBindView( view );

		if (ratingBar != null)
			
			ratingBar.setRating((float)mRate);
	}
	
	
	// make sure the number of stars will show as expected.
	@Override
	public View getView(View convertView, ViewGroup parent) {
		
		View v = super.getView(convertView, parent); 
		
		RatingBar rb = (RatingBar) v.findViewById(android.R.id.progress);
		
		LayoutParams params = rb.getLayoutParams();
		params.width = LayoutParams.WRAP_CONTENT;
		params.height = LayoutParams.WRAP_CONTENT;
		
		rb.setLayoutParams(params);
		return v;
	}

	/**
	 * <p>Set the current progress to the specified value. Does not do anything
	 * if the progress bar is in indeterminate mode.</p>
	 *
	 * @param pcnt the new progress, between 0 and {@link RatingBar#getMax()}
	 */
	
	public void setRating (float r) {
			int newRating = (int) r;
			if (mRate != newRating) {
				persistInt(mRate = newRating);
				notifyDependencyChange(shouldDisableDependents());
				notifyChanged();
			}
	}

	/**
	 * <p>Get the progress bar's current level of progress. Return 0 when the
	 * progress bar is in indeterminate mode.</p>
	 *
	 * @return the current progress, between 0 and {@link RatingBar#getMax()}
	 */
	
	public int getRating() {
		return /*(float)*/mRate;
	}

	/**
	 * Set the max value for the <code>RatingBar</code> object.
	 *
	 * @param max max value
	 */
	public void setMax (int max) {
		this.max = max;
		if (ratingBar != null) 
			ratingBar.setMax( max );
	}

	/**
	 * Get the underlying <code>RatingBar</code> object.
	 *
	 * @return <code>RatingBar</code> object
	 */
	
	@SuppressWarnings("unused")
	private RatingBar getRatingBar() {
		return ratingBar;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object onGetDefaultValue (TypedArray a, int index) {
		return a.getInt(index, mRate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onSetInitialValue (boolean restoreValue, Object defaultValue) {
		setRating(restoreValue ? getPersistedInt(mRate) : (Integer)defaultValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldDisableDependents () {
		return mRate == 0 || super.shouldDisableDependents();
	}

	/**
	 * Set the progress of the preference.
	 */
	
	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		discard = !callChangeListener(rating);
		if(!discard) {
			setRating(ratingBar.getRating());

			OnPreferenceChangeListener listener = getOnPreferenceChangeListener();
			if (listener instanceof AbstractRatingBarListener)
				setSummary( ((AbstractRatingBarListener)listener).toSummary( ratingBar.getRating() ) );
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void onStartTrackingTouch (RatingBar ratingBar) {
		discard = false;
	}

	/**
	 * {@inheritDoc}
	 */

	public void onStopTrackingTouch (RatingBar ratingBar) {
		
		if (discard)
			ratingBar.setRating((float)mRate);
		else {
			setRating(ratingBar.getRating());

			OnPreferenceChangeListener listener = getOnPreferenceChangeListener();
			if (listener instanceof AbstractRatingBarListener)
				setSummary( ((AbstractRatingBarListener)listener).toSummary( ratingBar.getRating() ) );
		}
	}
	

	/**
	 * Abstract rating bar summary updater.
	 *
	 * @see #setSummary(String)
	 */
	public static abstract class AbstractRatingBarListener extends PrefsChangeListener<RatingPreference> {

		/**
		 * Construct a change listener for the specified widget.
		 */
		public AbstractRatingBarListener (RatingPreference pref) {
			super( pref );
		}

		/**
		 * Sets the summary string directly into the text view
		 * to avoid {@link RatingPreference#notifyChanged()} call
		 * which was interrupting in the rating bar's thumb movement.
		 */
		protected final void setSummary (String text) {
			if (pref.summary != null)
				pref.summary.setText( text );
		}

		/**
		 * Convert integer progress to summary string.
		 * @param newValue should be an Integer instance
		 */
		protected abstract String toSummary (Object newValue);
		
	}
}


// extending SeekBarPreference class to make sure the title will appear in medium size.
class ProgressPreference extends SeekBarPreference {

	public ProgressPreference(Context context) {
		super(context);
	}
	
	@Override
	protected View onCreateView(ViewGroup p) {
		View layout = super.onCreateView(p);
		layout.setPadding(6, 10, 6, 10);
		Context ctx = getContext();
		TextView title = (TextView) layout.findViewById(android.R.id.title);
		title.setTextAppearance( ctx, android.R.style.TextAppearance_Medium );
		return layout;
	}
}
