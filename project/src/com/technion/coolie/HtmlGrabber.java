package com.technion.coolie;

import com.technion.coolie.skeleton.CoolieStatus;
import com.technion.coolie.skeleton.HtmlGrabberService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * 
 * This class handles URL requests.
 * <pre>
 * <b>usage example:</b>
 * <code>
 * HtmlGrabber hg = new HtmlGrabber(getApplicationContext())
 * {
 *		@Override
 *		public void handleResult(String result, CoolieStatus status) {
 *			Log.v("RESULT",result);			
 *		}
 * };
 * hg.getHtmlSource("https://www.google.com/search?q=URL/", CoolieAccount.NONE);
 * </code>
 * </pre>
 */
public abstract class HtmlGrabber {

	Context mContext;

	public HtmlGrabber(Context c) {
		mContext = c;
		mContext.registerReceiver(receiver, new IntentFilter(
				HtmlGrabberService.NOTIFICATION));
	}

	/**
	 * Sends HTTP request, asynchronously, the result will be handled by "handleResoult"
	 * @param URL - valid URL string structure - "scheme://domain:port/path?query_string#fragment_id"
	 * 			(eg - "https://www.google.com/search?q=URL").
	 * @param accountNeeded
	 *            - as described in the enum above the source code from the URL
	 *            will be put in a string
	 */
	public void getHtmlSource(String url, CoolieAccount accountNeeded) {
		Intent intent = new Intent(mContext, HtmlGrabberService.class);
		// add infos for the service which file to download and where to store
		intent.putExtra(HtmlGrabberService.URL, url);
		intent.putExtra(HtmlGrabberService.ACCOUNT, accountNeeded);
				
		mContext.startService(intent);
	}

	/**
	 * This method will be called when the server returns the result.
	 * @param result - the requested input source string.
	 * @param status - success status
	 */
	public abstract void handleResult(String result, CoolieStatus status);

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				CoolieStatus status = (CoolieStatus) bundle
						.getSerializable(HtmlGrabberService.STATUS);
				//TODO handle errors
					String result = bundle.getString(HtmlGrabberService.RESULT);
					handleResult(result, status);
					//mContext.unregisterReceiver(receiver);
			}
		}
	};

	public void finalize() throws Throwable
	{
		mContext.unregisterReceiver(receiver);
	}

}
