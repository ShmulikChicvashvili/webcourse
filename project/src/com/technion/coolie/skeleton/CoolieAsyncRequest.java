package com.technion.coolie.skeleton;

import java.util.List;

import com.technion.coolie.server.ug.api.UgFactory;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.Server.ServerCourse;
import com.technion.coolie.ug.model.Semester;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


/*
 * Use this class to call server requests in a asynchronous manner
 * You should override actionOnServer , onResult and onProgress 
 * actionOnServer sends a requests to the server in a asynchronous manner
 * onResult gets the result back after the server has processed your request
 * onProgress handles the progressbar
 * 
 *  I made an example for usage.. one should create CoolieAsyncRequest, override the functions,
 *  and call the run() function
 *  actionOnServer calls the getUgCourse from the server
 *  onReslut gets the list of courses that was assigned by the server
 *  
 *   
 * CoolieAsyncRequest c = new CoolieAsyncRequest(this) {
		      
		    List<ServerCourse> list;

			@Override
			public Void actionOnServer(Void... params) {
				 SemesterSeason ss = SemesterSeason.WINTER;
				 Semester semester = new Semester(2011,ss);
				 list = UgFactory.getUgCourse().getAllCourses(semester);
				return null;
			}

			@Override
			public Void onResult(Void result) { 
				Log.v("result", list.toString());
				return null;
			}
		};
		
		c.run();
 * 
 * 
 */

public abstract class CoolieAsyncRequest
{

	
	private  AsyncTask<Void, Void, Void> mAsyncTask = new AsyncTask<Void, Void, Void>() {
		@Override
		final protected Void doInBackground(Void... params) {
			
			
			actionOnServer(params);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			onResult(result);
			super.onPostExecute(result);
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			onProgress(values);
			super.onProgressUpdate(values);
		}
	};
	
	private Context c;
	
	public CoolieAsyncRequest(Context c)
	{
		this.c = c;
		
	}
	
	


	abstract public Void actionOnServer(Void... params);
	abstract public Void onResult(Void result);
	public Void onProgress(Void... values){
		return null;
	}
	
	public void run()
	{
		mAsyncTask.execute();
	}


}
