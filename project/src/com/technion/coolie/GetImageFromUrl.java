package com.technion.coolie;

import com.technion.coolie.skeleton.CoolieStatus;
import com.technion.coolie.skeleton.GetImageFromUrlService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;

/**
 * 
 * This class handles URL requests.
 * <pre>
 * <b>usage example:</b>
 * <code>
 * GetImageFromUrl gifu = new GetImageFromUrl(getApplicationContext())
 * {
 *		@Override
 *		public void handleResult(Bitmap result, CoolieStatus status) {
 *			//your logic		
 *		}
 * };
 * gifu.getHtmlSource("https://www.google.com/images/srpr/logo11w.png", CoolieAccount.NONE);
 * </code>
 * </pre>
 */
public abstract class GetImageFromUrl {

	Context mContext;

	public GetImageFromUrl(Context c) {
		mContext = c;
		mContext.registerReceiver(receiver, new IntentFilter(
				GetImageFromUrlService.NOTIFICATION));
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
		Intent intent = new Intent(mContext, GetImageFromUrlService.class);
		// add infos for the service which file to download and where to store
		intent.putExtra(GetImageFromUrlService.URL, url);
		intent.putExtra(GetImageFromUrlService.ACCOUNT, accountNeeded);
				
		mContext.startService(intent);
	}

	/**
	 * This method will be called when the server returns the result.
	 * @param result - the requested input source string.
	 * @param status - success status
	 */
	public abstract void handleResult(Bitmap result, CoolieStatus status);

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				CoolieStatus status = (CoolieStatus) bundle
						.getSerializable(GetImageFromUrlService.STATUS);
				//TODO handle errors
					Bitmap result = (Bitmap) intent.getParcelableExtra(GetImageFromUrlService.RESULT);
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
