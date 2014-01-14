package com.technion.coolie.webcourse;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.technion.coolie.R;
import com.technion.coolie.server.webcourse.api.WebcourseFactory;
//import api.WebcourseFactory;



public class AnnouncementsFragmentTab extends SherlockListFragment {
    
	public String mMessage;
	public AnnouncementsListAdapter mAnnouncementsListAdapter;
	public ListView mList;
	//ArrayList<AnnouncementsData> mStaffList = new ArrayList<StaffData>(staffData);
	public final static String EXTRA_MESSAGE_TITLE = "com.example.gr_plusplus.MESSAGE_TITLE";
	public final static String EXTRA_MESSAGE_CONTENT = "com.example.gr_plusplus.MESSAGE_CONTENT";
	public final static String EXTRA_MESSAGE_TIME_STAMP = "com.example.gr_plusplus.MESSAGE_TIME_STAMP";
	
//	String anounceCtnt = new String("gsdfnsdfnh'ifsdsnfdhlknfsd'khgk;asdmgklasdgasdg\n" +
//								"nfmkldngmlksfdghkldfnghklsdfnhl\nkdgjasdlkgjklsa\n" +
//								"djdjsklgj\ngnadsklgnasdlknglkgdfsgsdf.\nfsdfsdfsdf.\n" +
//								"nflewqhtlwqthgklqwenglknkldsngklsdngknsdlkgnlksdng\n" + 
//								"fsdlgfmdsag\nsdglksdlgkl;sdkgl;skdfglksdgk;sdfgksfdgfsd\n" + 
//								"fnksdafsadkfmkdasmfkladsmfksdamfkl\nfasdfdsafasfsafsafasfdfasdfadsfasdfsda\n" + 
//								"fjkbdjkfbjksdabfkjbsajkfbjkabfjkbasjkfbkjasbfjkbasjbf.");
//		
//	
//	AnnouncementsData[] announcements = new AnnouncementsData[] { 
//			new AnnouncementsData("Moed B appeals",anounceCtnt,"Last updated on 25/11/2013, 23:17:35"),
//			new AnnouncementsData("Moed B grades have been published",anounceCtnt,"Created on 23/11/2013, 21:44:33"),
//			new AnnouncementsData("Moed A Scanning and Apeals.",anounceCtnt,"Created on 16/11/2013, 16:26:22"),
//			new AnnouncementsData("Moed A grades have been published",anounceCtnt,"Last updated on 11/11/2013, 02:40:32"),
//			new AnnouncementsData("Assignment 4 grades",anounceCtnt,"Last updated on 9/11/2013, 14:40:53"),
//			new AnnouncementsData("Assignment 3 grades",anounceCtnt,"Created on 8/11/2013, 01:13:01"),
//			new AnnouncementsData("Assignment 2 grades",anounceCtnt,"Created on 31/10/2013, 14:50:39  "),
//			new AnnouncementsData("Reminder: Technion teaching survey",anounceCtnt,"Created on 31/10/2013, 02:45:07"),
//			new AnnouncementsData("HW4 Deadline Extension",anounceCtnt,"Last updated on 28/10/2013, 00:09:50"),
//			new AnnouncementsData("Assignment 2",anounceCtnt,"Created on 26/10/2013, 18:50:47"),
//			new AnnouncementsData("Assignment 2 grades",anounceCtnt,"Created on 17/10/2013, 16:37:57"),
//			new AnnouncementsData("HW3 Deadline Extension",anounceCtnt,"Created on 17/10/2013, 16:36:43"),
//			new AnnouncementsData("Exercise 4 has been published",anounceCtnt,"Last updated on 10/10/2013, 14:46:04"),
//			new AnnouncementsData("Assignment 1 grades",anounceCtnt,"Last updated on 8/10/2013, 08:45:01"),
//			new AnnouncementsData("Workshop cancellation",anounceCtnt,"Created on 2/10/2013, 19:30:26")
//		};
//	List<AnnouncementsData> announcementsData; // = parser.parseAnnouncements(courseInformation);
//	ArrayList<AnnouncementsData> mAnnouncementsList = new ArrayList<AnnouncementsData>(announcementsData);
	List<AnnouncementsData> mAnnouncementsList = new ArrayList<AnnouncementsData>();
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        final View rootView = inflater.inflate(R.layout.web_announcements_fragment, container, false);
        
//        List<AnnouncementsData> announcementsData = new ArrayList<AnnouncementsData>();
        
        String courseNumber = mMessage.substring(0, 6);
        asyncParse<AnnouncementsData> a = new asyncParse<AnnouncementsData>(){

			@Override
			protected List<AnnouncementsData> doInBackground(String... params) {
				// TODO Auto-generated method stub
		        CourseData course = new CourseData(params[0], "");
				try {
					mAnnouncementsList = WebcourseFactory.getWebcourseManager().getAnnouncement(course);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(List<AnnouncementsData> result) {
		        mList = (ListView) rootView.findViewById(android.R.id.list);
		        mAnnouncementsListAdapter = new AnnouncementsListAdapter(rootView.getContext(), mAnnouncementsList);
		        setListAdapter(mAnnouncementsListAdapter);
		        super.onPostExecute(result);
			}
        	
        };
        a.execute(courseNumber);
        
//        announcementsData = 
        
//        mList = (ListView) rootView.findViewById(android.R.id.list);
//        mAnnouncementsListAdapter = new AnnouncementsListAdapter(rootView.getContext(), mAnnouncementsList);
//        setListAdapter(mAnnouncementsListAdapter);
        
        
        
        return rootView;
    }
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        
		// Get current list item data
		AnnouncementsData current = (AnnouncementsData) mList.getItemAtPosition(position);
				
		// Load announcement activity with data of current item
		loadAnnouncementActivity(current.getTitle(), current.getContent(), current.getTimeStamp());
    }
	public void loadAnnouncementActivity(String title, String content, String timeStamp) {
        
    	Intent intent = new Intent(this.getActivity(), AnnouncementActivity.class);
    	intent.putExtra(EXTRA_MESSAGE_TITLE, title);
    	intent.putExtra(EXTRA_MESSAGE_CONTENT, content);
    	intent.putExtra(EXTRA_MESSAGE_TIME_STAMP, timeStamp);
    	startActivity(intent);    	
	}
	
	public void SetIntentMessage (String message) {
		
		mMessage = message;
	}
}
