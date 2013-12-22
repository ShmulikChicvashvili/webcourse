package com.technion.coolie.webcourse.gr_plusplus;


public class AnnouncementsData {
	
	private String mAnnounceTitle;
	private String mAnnounceContent;
	private String mAnnounceTimeStamp;
	
	AnnouncementsData() {
		
	}
	
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
