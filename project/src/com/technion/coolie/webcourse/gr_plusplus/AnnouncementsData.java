package com.example.gr_plusplus;


public class AnnouncementsData {
	
	private String mAnnounceTitle;
	private String mAnnounceContent;
	private String mAnnounceTimeStamp;
	
	public AnnouncementsData(String title, String content, String timeStamp) {  
          mAnnounceTitle = title;
          mAnnounceContent = content;
          mAnnounceTimeStamp = timeStamp;
    }
	
	public String getTitle() {
		return mAnnounceTitle;
	}
	
	public String getContent() {
		return mAnnounceContent;
	}
	
	public String getTimeStamp() {
		return mAnnounceTimeStamp;
	}

}
