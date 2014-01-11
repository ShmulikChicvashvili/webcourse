/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.technion.coolie.techpark;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.technion.coolie.R;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Service that receives ActivityRecognition updates. It receives updates
 * in the background, even if the main Activity is not visible.
 */
public class ActivityRecognitionIntentService extends IntentService {

    // Formats the timestamp in the log
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSZ";

    // A date formatter
    private SimpleDateFormat mDateFormat;

    // Store the app's shared preferences repository
    private SharedPreferences mPrefs;

    public ActivityRecognitionIntentService() {
        // Set the label for the service's background thread
        super("ActivityRecognitionIntentService");
    }

    /**
     * Called when a new activity detection update is available.
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        // Get a handle to the repository
    	// TODO: to see if the shared preferences are needed
        mPrefs = getApplicationContext().getSharedPreferences(
                ClientSideUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        // Get a date formatter, and catch errors in the returned timestamp
        try {
            mDateFormat = (SimpleDateFormat) DateFormat.getDateTimeInstance();
        } catch (Exception e) {
            Log.e(ClientSideUtils.APPTAG, getString(R.string.date_format_error));
        }

        // Format the timestamp according to the pattern, then localize the pattern
        mDateFormat.applyPattern(DATE_FORMAT_PATTERN);
        mDateFormat.applyLocalizedPattern(mDateFormat.toLocalizedPattern());

        // If the intent contains an update
        if (ActivityRecognitionResult.hasResult(intent)) {

            // Get the update
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

            // Log the update
            logActivityRecognitionResult(result);

            // Get the most probable activity from the list of activities in the update
            DetectedActivity mostProbableActivity = result.getMostProbableActivity();

            // Get the type of activity
            int activityType = mostProbableActivity.getType();

            // Check to see if the repository contains a previous activity
            if (!mPrefs.contains(ClientSideUtils.KEY_PREVIOUS_ACTIVITY_TYPE)) {

                // This is the first type an activity has been detected. Store the type
                Editor editor = mPrefs.edit();
                editor.putInt(ClientSideUtils.KEY_PREVIOUS_ACTIVITY_TYPE, activityType);
                editor.commit();

            // If the repository contains a type
            }
        }
    }

    /**
     * Write the activity recognition update to the log file

     * @param result The result extracted from the incoming Intent
     */
    private void logActivityRecognitionResult(ActivityRecognitionResult result) {
        // Get all the probably activities from the updated result
        for (DetectedActivity detectedActivity : result.getProbableActivities()) {

            // Get the activity type, confidence level, and human-readable name
            int activityType = detectedActivity.getType();
            int confidence = detectedActivity.getConfidence();

            if (confidence >= 55){
		        Intent broadcastIntent = new Intent();
		        broadcastIntent.addCategory(ClientSideUtils.CATEGORY_LOCATION_SERVICES)
		        			   .setAction(ClientSideUtils.ACTION_REFRESH_STATUS_LIST)
					           .putExtra(ClientSideUtils.EXTRA_ACTIVITY_NAME, activityType);
		        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
            }
        }
    }
}
