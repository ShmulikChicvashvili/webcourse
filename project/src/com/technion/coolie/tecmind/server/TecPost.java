package com.technion.coolie.tecmind.server;

import java.util.Date;

public class TecPost implements IGetters {
	String id;
	Date date;
	int technionValue;
	String userID;
	int likesCount;
	int commentCount;
	String content;

	/**
	 * @param id1
	 * @param date1
	 * @param technionValue1
	 * @param userID1
	 * @param likesCount1
	 */
	public TecPost(String id1, String content1, Date date1, int technionValue1,
			String userID1, int likesCount1, int commentCount1) {
		this.id = id1;
		this.date = date1;
		this.technionValue = technionValue1;
		this.userID = userID1;
		this.likesCount = likesCount1;
		this.commentCount = commentCount1;
		this.content = content1;
	}

	TecPost() {
	}

	/**
	 * @return the id
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @param id1
	 *            the id to set
	 */
	public void setId(String id1) {
		this.id = id1;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date1
	 *            the date to set
	 */
	public void setDate(Date date1) {
		this.date = date1;
	}

	/**
	 * @return the technionValue
	 */
	public int getTechnionValue() {
		return technionValue;
	}

	/**
	 * @param technionValue1
	 *            the technionValue to set
	 */
	public void setTechnionValue(int technionValue1) {
		this.technionValue = technionValue1;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID1
	 *            the userID to set
	 */
	public void setUserID(String userID1) {
		this.userID = userID1;
	}

	/**
	 * @return the likesCount
	 */
	public int getLikesCount() {
		return likesCount;
	}

	/**
	 * @param likesCount1
	 *            the likesCount to set
	 */
	public void setLikesCount(int likesCount1) {
		this.likesCount = likesCount1;
	}

	/**
	 * @return the commentCount
	 */
	public int getCommentCount() {
		return commentCount;
	}

	/**
	 * @param commentCount1
	 *            the commentCount to set
	 */
	public void setCommentCount(int commentCount1) {
		this.commentCount = commentCount1;
	}

}
