package com.technion.coolie;



import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.gson.Gson;
import com.technion.coolie.server.parkion.ParkingLot;
import com.technion.coolie.server.techmine.TecComment;
import com.technion.coolie.server.techmine.TecLike;
import com.technion.coolie.server.techmine.TecPost;
import com.technion.coolie.server.techmine.TecTopBestComment;
import com.technion.coolie.server.techmine.TecTopBestPost;
import com.technion.coolie.server.techmine.TecUser;
import com.technion.coolie.server.techoins.BankAccount;
import com.technion.coolie.server.techoins.Product;
import com.technion.coolie.server.techoins.TechoinsTransfer;
import com.technion.coolie.server.ug.framework.CourseKey;
import com.technion.coolie.server.ug.framework.Semester;
import com.technion.coolie.server.ug.framework.Student;
import com.technion.coolie.skeleton.CoolieServerInterfaceService;
import com.technion.coolie.skeleton.CoolieStatus;
import com.technion.coolie.webcourse.gr_plusplus.CourseData;
//import com.technion.coolie.server.webcourse.framework.CourseData;


/*this is how u will use it on a your activity, u will need to create CoolieServerInterface for each function u do,
 * and deserialize the Gson appropriately on each handleResult u write..
 * for example
 *
 	CoolieServerInterface c = new CoolieServerInterface(getApplicationContext()){

			@Override
			public void handleResult(String result, CoolieStatus status) {
				List<CourseServer> myOutput;
				myOutput = new Gson().fromJson(result, new TypeToken<List<CourseServer>>() {
			    }.getType());
				
			}

			
			};
			SemesterSeason ss = SemesterSeason.WINTER;
			Semester semester = new Semester(2011,ss);
			c.getAllCourses(semester);
 */


public abstract class CoolieServerInterface {
	Context mContext;
	
	public enum Action
	{
		UG_GET_ALL_COURSES,
		UG_GET_COURSES,
		UG_GET_STUDENT_CURRENT_COURSES,
		UG_GET_ALL_ACADEMIC_EVENTS,
		UG_GET_STUDENT_EXAMS,
		UG_GET_MY_GRADES_SHEET,
		UG_GET_STUDENT_PAYMENTS,
		UG_ADD_TRACKING_STUDENT,
		UG_REMOVE_TRACKING_STUDENT_FROM_COURSE,
		TELETECH_GET_ALL_CONTACTS,
		WEBCOURSE_GET_STAFF_INFO,
		PARKION_ADD_PARKING_LOT,
		PARKION_REMOVE_PARKING_LOT,
		PARKION_GET_PARKING_LOT,
		PARKION_SET_OCCUPANCY,
		PARKION_USER_PARKED_IN_LOT,
		PARKION_USER_REPORT_PARKING_LOT_BUSY,
		PARKION_USER_LEFT_PARKING_LOT,
		PARKION_GET_FREE_PARKING_LOT,
		PARKION_GET_PARKING_IDS,
		TECHOINS_ADD_BANK_ACOUNTS,
		TECHOINS_REMOVE_BANK_ACOUNTS,
		TECHOINS_GET_BANK_ACOUNTS,
		TECHOINS_GET_BANK_HISTORY,
		TECHOINS_MOVE_MONEY,
		TECHOINS_GET_PRODUCTS_BY_ID,
		TECHOINS_GET_PRODUCTS_BY_NAME,
		TECHOINS_GET_PRODUCTS_BY_CATEGORY,
		TECHOINS_GET_X_RECENT_PRODUCTS,
		TECHOINS_GET_X_RANDOM_PRODUCTS,
		TECHOINS_ADD_PRODUCT,
		TECHOINS_GET_SOLD_PRODUCTS_BY_SELLER_ID,
		TECHOINS_GET_PURCHASED_PRODUCTS_BY_BUYER_ID,
		TECHOINS_GET_PUBLISHED_PRODUCTS_BY_SELLER_ID,
		TECHOINS_REMOVE_PRODUCT,
		TECHOINS_BUY_PRODUCT,
		TECHMINE_ADD_USER,
		TECHMINE_REMOVE_USER,
		TECHMINE_GET_USER,
		TECHMINE_ADD_TOP_BEST_POST,
		TECHMINE_REMOVE_TOP_BEST_POST,
		TECHMINE_GET_TOP_BEST_POST,
		TECHMINE_ADD_TOP_BEST_COMMENT,
		TECHMINE_REMOVE_TOP_BEST_COMMENT,
		TECHMINE_GET_TOP_BEST_COMMENT,
		TECHMINE_ADD_TEC_POST,
		TECHMINE_REMOVE_TEC_POST,
		TECHMINE_GET_TEC_POST,
		TECHMINE_ADD_TEC_COMMENT,
		TECHMINE_REMOVE_TEC_COMMENT,
		TECHMINE_GET_TEC_COMMENT,
		TECHMINE_ADD_TEC_LIKE,
		TECHMINE_REMOVE_TEC_LIKE,
		TECHMINE_GET_TEC_LIKE,
		TECHOINS_GET_ALL_USER_POSTS,
		
		
	
	}
	public CoolieServerInterface(Context c)
	{
		mContext = c;
		mContext.registerReceiver(receiver, new IntentFilter(
				CoolieServerInterfaceService.NOTIFICATION));
	}

	
	
	public abstract void handleResult(String result, CoolieStatus status);

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				CoolieStatus status = (CoolieStatus) bundle
						.getSerializable(CoolieServerInterfaceService.STATUS);
				//TODO handle errors
					String result = bundle.getString(CoolieServerInterfaceService.RESULT);
					handleResult(result, status);
			}
		}
	};

	protected void finalize() throws Throwable
	{
		mContext.unregisterReceiver(receiver);
	}
	
///////UG PART	
	public void getAllCourses(Semester semester) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_ALL_COURSES;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT,semester);		
		mContext.startService(intent);
	}
	
	public void getCourses(List<CourseKey> courseKeys) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_COURSES;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(courseKeys));
		mContext.startService(intent);
	}
	
	public void getStudentCurrentCourses(Student student,Semester semester) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_STUDENT_CURRENT_COURSES;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(student));
		intent.putExtra(CoolieServerInterfaceService.INPUT2, new Gson().toJson(semester));
		mContext.startService(intent);
	}
	
	public void getAllAcademicEvents() {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_ALL_ACADEMIC_EVENTS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		mContext.startService(intent);
	}
	
	public void getStudentExams(Student student,Semester semester) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_STUDENT_EXAMS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(student));
		intent.putExtra(CoolieServerInterfaceService.INPUT2, new Gson().toJson(semester));
		mContext.startService(intent);
	}
	
	public void getMyGradesSheet(Student student) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_MY_GRADES_SHEET;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(student));
		mContext.startService(intent);
	}
	
	public void getStudentPayments(Student student) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_GET_STUDENT_PAYMENTS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(student));
		mContext.startService(intent);
	}
	
	public void addTrackingStudent(Student student,CourseKey courseKey) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_ADD_TRACKING_STUDENT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(student));
		intent.putExtra(CoolieServerInterfaceService.INPUT2, new Gson().toJson(courseKey));
		mContext.startService(intent);
	}
	
	public void removeTrackingStudentFromCourse(Student student,CourseKey courseKey) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.UG_REMOVE_TRACKING_STUDENT_FROM_COURSE;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(student));
		intent.putExtra(CoolieServerInterfaceService.INPUT2, new Gson().toJson(courseKey));
		mContext.startService(intent);
	}
	
///////teletech PART

	public void getAllContacts() {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TELETECH_GET_ALL_CONTACTS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		mContext.startService(intent);
	}
	
	
///////webcourse PART	
	
	public void getStaffInfo(CourseData courseData) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.WEBCOURSE_GET_STAFF_INFO;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(courseData));
		mContext.startService(intent);
	}
	
///////parkion PART	
	
	public void addParkingLot(ParkingLot park) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.PARKION_ADD_PARKING_LOT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(park));
		mContext.startService(intent);
	}
	
	public void removeParkingLot(ParkingLot park) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.PARKION_REMOVE_PARKING_LOT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(park));
		mContext.startService(intent);
	}
	
	public void getParkingLot(ParkingLot park) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.PARKION_GET_PARKING_LOT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(park));
		mContext.startService(intent);
	}
	
	public void setOccupancy(ParkingLot park) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.PARKION_SET_OCCUPANCY;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(park));
		mContext.startService(intent);
	}
	
	public void userParkedInLot(ParkingLot park) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.PARKION_USER_PARKED_IN_LOT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(park));
		mContext.startService(intent);
	}
	
	public void userReportParkingLotBusy(ParkingLot park) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.PARKION_USER_REPORT_PARKING_LOT_BUSY;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(park));
		mContext.startService(intent);
	}
	
	public void userLeftParkingLot(ParkingLot park) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.PARKION_USER_LEFT_PARKING_LOT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(park));
		mContext.startService(intent);
	}
	
	public void getFreeParkingLots() {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.PARKION_GET_FREE_PARKING_LOT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		mContext.startService(intent);
	}
	
	public void getParkingIds() {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.PARKION_GET_PARKING_IDS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		mContext.startService(intent);
	}
	
///////techoins PART	
	public void addBankAcount(BankAccount account, String password) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_ADD_BANK_ACOUNTS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(account));
		intent.putExtra(CoolieServerInterfaceService.INPUT2,password);
		mContext.startService(intent);
	}
	
	public void removeBankAcount(BankAccount account) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_REMOVE_BANK_ACOUNTS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(account));
		mContext.startService(intent);
	}
	
	public void getBankAcount(BankAccount account) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_GET_BANK_ACOUNTS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(account));
		mContext.startService(intent);
	}
	
	public void getBankHistory(BankAccount account) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_GET_BANK_HISTORY;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(account));
		mContext.startService(intent);
	}
	
	public void moveMoney(TechoinsTransfer transfer) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_MOVE_MONEY;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(transfer));
		mContext.startService(intent);
	}
	
	public void getProductsById(List<Product> products) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_GET_PRODUCTS_BY_ID;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(products));
		mContext.startService(intent);
	}
	
	public void getProductsByName(Product product) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_GET_PRODUCTS_BY_NAME;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(product));
		mContext.startService(intent);
	}
	
	public void getProductsByCategory(Product product) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_GET_PRODUCTS_BY_CATEGORY;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(product));
		mContext.startService(intent);
	}
	
	public void getXRecentProducts(int x) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_GET_X_RECENT_PRODUCTS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, x);
		mContext.startService(intent);
	}
	
	public void getXRandomProducts(int x) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_GET_X_RANDOM_PRODUCTS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, x);
		mContext.startService(intent);
	}
	
	public void addProduct(Product product) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_ADD_PRODUCT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(product));
		mContext.startService(intent);
	}
	
	public void getSoldProductsBySellerID(Product product) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_GET_SOLD_PRODUCTS_BY_SELLER_ID;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(product));
		mContext.startService(intent);
	}
	
	public void getPurchasedProductsByBuyerID(Product product) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_GET_PURCHASED_PRODUCTS_BY_BUYER_ID;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(product));
		mContext.startService(intent);
	}
	
	public void getPublishedProductsBySellerID(Product product) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_GET_PUBLISHED_PRODUCTS_BY_SELLER_ID;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(product));
		mContext.startService(intent);
	}
	
	public void removeProduct(Product product) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_REMOVE_PRODUCT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(product));
		mContext.startService(intent);
	}
	
	public void buyProduct(Product product) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_BUY_PRODUCT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(product));
		mContext.startService(intent);
	}
	
///////techmine PART

	public void addUser(TecUser user) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_ADD_USER;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(user));
		mContext.startService(intent);
	}
	
	public void removeUser(TecUser user) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_REMOVE_USER;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(user));
		mContext.startService(intent);
	}
	
	public void getUser(TecUser user) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_GET_USER;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(user));
		mContext.startService(intent);
	}
	
	public void addTopBestPost(TecTopBestPost topBestPost) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_ADD_TOP_BEST_POST;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(topBestPost));
		mContext.startService(intent);
	}
	
	public void removeTopBestPost(TecTopBestPost topBestPost) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_REMOVE_TOP_BEST_POST;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(topBestPost));
		mContext.startService(intent);
	}
	
	public void getTopBestPost(TecTopBestPost topBestPost) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_GET_TOP_BEST_POST;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(topBestPost));
		mContext.startService(intent);
	}
	
	public void addTopBestComment(TecTopBestComment topBestComment) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_ADD_TOP_BEST_COMMENT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(topBestComment));
		mContext.startService(intent);
	}
	
	public void removeTopBestComment(TecTopBestComment topBestComment) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_REMOVE_TOP_BEST_COMMENT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(topBestComment));
		mContext.startService(intent);
	}
	
	public void getTopBestCommenct(TecTopBestComment topBestComment) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_GET_TOP_BEST_COMMENT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(topBestComment));
		mContext.startService(intent);
	}

	public void addTecPost(TecPost tecPost) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_ADD_TEC_POST;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(tecPost));
		mContext.startService(intent);
	}
	
	public void removeTecPost(TecPost tecPost) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_REMOVE_TEC_POST;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(tecPost));
		mContext.startService(intent);
	}
	
	public void getTecPost(TecPost tecPost) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_GET_TEC_POST;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(tecPost));
		mContext.startService(intent);
	}
	
	public void addTecComment(TecComment tecComment) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_ADD_TEC_COMMENT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(tecComment));
		mContext.startService(intent);
	}
	
	public void removeTecComment(TecComment tecComment) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_REMOVE_TEC_COMMENT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(tecComment));
		mContext.startService(intent);
	}
	
	public void getTecComment(TecComment tecComment) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_GET_TEC_COMMENT;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(tecComment));
		mContext.startService(intent);
	}
	
	public void addTecLike(TecLike tecLike) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_ADD_TEC_LIKE;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(tecLike));
		mContext.startService(intent);
	}
	
	public void removeTecLike(TecLike tecLike) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_REMOVE_TEC_LIKE;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(tecLike));
		mContext.startService(intent);
	}
	
	public void getTecLike(TecLike tecLike) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHMINE_GET_TEC_LIKE;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(tecLike));
		mContext.startService(intent);
	}
	
	public void getAllUserPosts(TecUser user) {
		Intent intent = new Intent(mContext, CoolieServerInterfaceService.class);
		Action action = Action.TECHOINS_GET_ALL_USER_POSTS;
		intent.putExtra(CoolieServerInterfaceService.ACTION,action);
		intent.putExtra(CoolieServerInterfaceService.INPUT, new Gson().toJson(user));
		mContext.startService(intent);
	}
	

}




