package com.technion.coolie.skeleton;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.CoolieServerInterface;
import com.technion.coolie.CoolieServerInterface.Action;
import com.technion.coolie.server.parkion.ParkingLot;
import com.technion.coolie.server.parkion.ParkionFactory;
import com.technion.coolie.server.parkion.ReturnCode;
import com.technion.coolie.server.techmine.TecComment;
import com.technion.coolie.server.techmine.TecLike;
import com.technion.coolie.server.techmine.TecPost;
import com.technion.coolie.server.techmine.TecTopBestComment;
import com.technion.coolie.server.techmine.TecTopBestPost;
import com.technion.coolie.server.techmine.TecUser;
import com.technion.coolie.server.techmine.TechmineFactory;
import com.technion.coolie.server.techoins.BankAccount;
import com.technion.coolie.server.techoins.Product;
import com.technion.coolie.server.techoins.TechoinsFactory;
import com.technion.coolie.server.techoins.TechoinsTransfer;
import com.technion.coolie.server.teletech.api.TeletechFactory;
import com.technion.coolie.server.ug.ReturnCodesUg;
import com.technion.coolie.server.ug.api.UgFactory;
import com.technion.coolie.server.webcourse.api.WebcourseFactory;
import com.technion.coolie.teletech.ContactInformation;
import com.technion.coolie.ug.Server.ServerCourse;
import com.technion.coolie.ug.model.AcademicCalendarEvent;
import com.technion.coolie.ug.model.AccomplishedCourse;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Exam;
import com.technion.coolie.ug.model.Payment;
import com.technion.coolie.ug.model.Semester;
import com.technion.coolie.ug.model.Student;
import com.technion.coolie.webcourse.gr_plusplus.CourseData;
import com.technion.coolie.webcourse.gr_plusplus.StaffData;

public class CoolieServerInterfaceService extends IntentService {

  public static final String INPUT = "input";
  public static final String INPUT2 = "input2";
  public static final String INPUT3 = "input3";
  public static final String INPUT4 = "input4";
  public static final String ACTION = "action";
  public static final String STATUS = "status";
  public static final String RESULT = "result";

  public static final String NOTIFICATION = "com.technion.coolie.CoolieServerInterface";

  public static final String SEMESTER = "semester";

  public CoolieServerInterfaceService() {
    super("CoolieServerInterface");

  }

  @Override
  protected void onHandleIntent(Intent arg0) {
    Intent intent = new Intent(NOTIFICATION);
    CoolieServerInterface.Action action = (Action) arg0
        .getSerializableExtra(ACTION);

    switch (action) {
    case UG_GET_ALL_COURSES: {
      Semester semester = (Semester) arg0.getSerializableExtra(INPUT);
      List<ServerCourse> list;
      list = UgFactory.getUgCourse().getAllCourses(semester);

      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(list));
      sendBroadcast(intent);
      break;
    }

    case UG_GET_COURSES: {
      List<CourseKey> listInput;
      List<ServerCourse> listOutput;
      listInput = new Gson().fromJson(
          (String) arg0.getSerializableExtra(INPUT),
          new TypeToken<List<CourseKey>>() {
          }.getType());

      listOutput = UgFactory.getUgCourse().getCourses(listInput);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(listOutput));
      sendBroadcast(intent);
      break;
    }

    case UG_GET_STUDENT_CURRENT_COURSES: {
      Student student;
      Semester semester;
      List<ServerCourse> listOutput;
      student = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Student.class);
      semester = new Gson().fromJson(
          (String) arg0.getSerializableExtra(INPUT2), Semester.class);

      listOutput = UgFactory.getUgCourse().getStudentCurrentCourses(student,
          semester);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(listOutput));
      sendBroadcast(intent);
      break;
    }

    case UG_GET_ALL_ACADEMIC_EVENTS: {

      List<AcademicCalendarEvent> listOutput;

      listOutput = UgFactory.getUgEvent().getAllAcademicEvents();
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(listOutput));
      sendBroadcast(intent);
      break;
    }

    case UG_GET_STUDENT_EXAMS: {
      Student student;
      Semester semester;
      List<Exam> listOutput;
      student = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Student.class);
      semester = new Gson().fromJson(
          (String) arg0.getSerializableExtra(INPUT2), Semester.class);

      listOutput = UgFactory.getUgExam().getStudentExams(student, semester);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(listOutput));
      sendBroadcast(intent);
      break;
    }

    case UG_GET_MY_GRADES_SHEET: {
      Student student;
      List<AccomplishedCourse> listOutput;
      student = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Student.class);

      listOutput = UgFactory.getUgGradeSheet().getMyGradesSheet(student);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(listOutput));
      sendBroadcast(intent);
      break;
    }

    case UG_GET_STUDENT_PAYMENTS: {
      Student student;
      List<Payment> listOutput;
      student = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Student.class);

      listOutput = UgFactory.getUgPayments().getStudentPayments(student);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(listOutput));
      sendBroadcast(intent);
      break;
    }

    case UG_ADD_TRACKING_STUDENT: {
      Student student;
      CourseKey courseKey;
      ReturnCodesUg Output;
      student = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Student.class);
      courseKey = new Gson().fromJson(
          (String) arg0.getSerializableExtra(INPUT2), CourseKey.class);

      Output = UgFactory.getUgTracking().addTrackingStudent(student, courseKey);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case UG_REMOVE_TRACKING_STUDENT_FROM_COURSE: {
      Student student;
      CourseKey courseKey;
      ReturnCodesUg Output;
      student = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Student.class);
      courseKey = new Gson().fromJson(
          (String) arg0.getSerializableExtra(INPUT2), CourseKey.class);

      Output = UgFactory.getUgTracking().removeTrackingStudentFromCourse(
          student, courseKey);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TELETECH_GET_ALL_CONTACTS: {

      List<ContactInformation> listOutput;

      listOutput = TeletechFactory.getTeletech().getAllContacts();
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(listOutput));
      sendBroadcast(intent);
      break;
    }

    case WEBCOURSE_GET_STAFF_INFO: {
      CourseData courseData;
      List<StaffData> listOutput;
      courseData = new Gson().fromJson(
          (String) arg0.getSerializableExtra(INPUT), CourseData.class);

      listOutput = WebcourseFactory.getWebcourseManager().getStaffInfo(
          courseData);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(listOutput));
      sendBroadcast(intent);
      break;
    }

    case PARKION_ADD_PARKING_LOT: {
      ParkingLot park;
      ReturnCode Output;
      park = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          ParkingLot.class);

      Output = ParkionFactory.getParkionAPI().addParkingLot(park);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case PARKION_REMOVE_PARKING_LOT: {
      ParkingLot park;
      ReturnCode Output;
      park = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          ParkingLot.class);

      Output = ParkionFactory.getParkionAPI().removeParkingLot(park);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case PARKION_GET_PARKING_LOT: {
      ParkingLot park;
      ParkingLot Output;
      park = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          ParkingLot.class);

      Output = ParkionFactory.getParkionAPI().getParkingLot(park);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case PARKION_SET_OCCUPANCY: {
      ParkingLot park;
      ReturnCode Output;
      park = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          ParkingLot.class);

      Output = ParkionFactory.getParkionAPI().setOccupancy(park);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case PARKION_USER_PARKED_IN_LOT: {
      ParkingLot park;
      ReturnCode Output;
      park = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          ParkingLot.class);

      Output = ParkionFactory.getParkionAPI().userParkedInLot(park);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case PARKION_USER_REPORT_PARKING_LOT_BUSY: {
      ParkingLot park;
      ReturnCode Output;
      park = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          ParkingLot.class);

      Output = ParkionFactory.getParkionAPI().userReportParkingLotBusy(park);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case PARKION_USER_LEFT_PARKING_LOT: {
      ParkingLot park;
      ReturnCode Output;
      park = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          ParkingLot.class);

      Output = ParkionFactory.getParkionAPI().userLeftParkingLot(park);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case PARKION_GET_FREE_PARKING_LOT: {

      List<String> listOutput;

      listOutput = ParkionFactory.getParkionAPI().getFreeParkingLots();
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(listOutput));
      sendBroadcast(intent);
      break;
    }

    case PARKION_GET_PARKING_IDS: {

      List<String> listOutput;

      listOutput = ParkionFactory.getParkionAPI().getIds();
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(listOutput));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_ADD_BANK_ACOUNTS: {
      BankAccount account;
      String password;
      com.technion.coolie.server.techoins.ReturnCode Output;
      account = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          BankAccount.class);
      password = (String) arg0.getSerializableExtra(INPUT2);

      Output = TechoinsFactory.getTechoinsAPI().addAccount(account, password);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_REMOVE_BANK_ACOUNTS: {
      BankAccount account;
      com.technion.coolie.server.techoins.ReturnCode Output;
      account = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          BankAccount.class);

      Output = TechoinsFactory.getTechoinsAPI().removeAccount(account);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_GET_BANK_ACOUNTS: {
      BankAccount account;
      BankAccount Output;
      account = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          BankAccount.class);

      Output = TechoinsFactory.getTechoinsAPI().getAccount(account);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_GET_BANK_HISTORY: {
      BankAccount account;
      List<TechoinsTransfer> OutputList;
      account = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          BankAccount.class);

      OutputList = TechoinsFactory.getTechoinsAPI().getHistory(account);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(OutputList));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_MOVE_MONEY: {
      TechoinsTransfer transfer;
      com.technion.coolie.server.techoins.ReturnCode Output;
      transfer = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          TechoinsTransfer.class);

      Output = TechoinsFactory.getTechoinsAPI().moveMoney(transfer);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_GET_PRODUCTS_BY_ID: {
      List<Product> products;
      List<Product> OutputList;
      products = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          new TypeToken<List<Product>>() {
          }.getType());

      OutputList = TechoinsFactory.getTechoinsAPI().getProductsByIds(products);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(OutputList));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_GET_PRODUCTS_BY_NAME: {
      Product product;
      List<Product> OutputList;
      product = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Product.class);

      OutputList = TechoinsFactory.getTechoinsAPI().getProductsByName(product);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(OutputList));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_GET_PRODUCTS_BY_CATEGORY: {
      Product product;
      List<Product> OutputList;
      product = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Product.class);

      OutputList = TechoinsFactory.getTechoinsAPI().getProductsByCategory(
          product);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(OutputList));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_GET_X_RECENT_PRODUCTS: {
      int x;
      List<Product> OutputList;
      x = arg0.getIntExtra(INPUT, 0);

      OutputList = TechoinsFactory.getTechoinsAPI().getXRecentProducts(x);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(OutputList));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_GET_X_RANDOM_PRODUCTS: {
      int x;
      List<Product> OutputList;
      x = arg0.getIntExtra(INPUT, 0);

      OutputList = TechoinsFactory.getTechoinsAPI().getXRandomProducts(x);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(OutputList));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_ADD_PRODUCT: {
      Product product;
      com.technion.coolie.server.techoins.ReturnCode Output;
      product = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Product.class);

      Output = TechoinsFactory.getTechoinsAPI().addProduct(product);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_GET_SOLD_PRODUCTS_BY_SELLER_ID: {
      Product product;
      List<Product> OutputList;
      product = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Product.class);

      OutputList = TechoinsFactory.getTechoinsAPI().getSoldProductsBySellerID(
          product);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(OutputList));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_GET_PURCHASED_PRODUCTS_BY_BUYER_ID: {
      Product product;
      List<Product> OutputList;
      product = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Product.class);

      OutputList = TechoinsFactory.getTechoinsAPI()
          .getPurchasedProductsByBuyerID(product);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(OutputList));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_GET_PUBLISHED_PRODUCTS_BY_SELLER_ID: {
      Product product;
      List<Product> OutputList;
      product = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Product.class);

      OutputList = TechoinsFactory.getTechoinsAPI()
          .getPublishedProductsBySellerID(product);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(OutputList));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_REMOVE_PRODUCT: {
      Product product;
      com.technion.coolie.server.techoins.ReturnCode Output;
      product = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Product.class);

      Output = TechoinsFactory.getTechoinsAPI().removeProduct(product);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_BUY_PRODUCT: {
      Product product;
      com.technion.coolie.server.techoins.ReturnCode Output;
      product = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          Product.class);

      Output = TechoinsFactory.getTechoinsAPI().buyProduct(product);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_ADD_USER: {
      TecUser user;
      user = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          TecUser.class);
      com.technion.coolie.server.techmine.ReturnCode Output;
      Output = TechmineFactory.getTechmineAPI().addUser(user);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_GET_USER: {
      TecUser user;
      user = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          TecUser.class);
      TecUser Output;
      Output = TechmineFactory.getTechmineAPI().getUser(user);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_ADD_TOP_BEST_POST: {
      TecTopBestPost topBestPost;
      topBestPost = new Gson().fromJson(
          (String) arg0.getSerializableExtra(INPUT), TecTopBestPost.class);
      com.technion.coolie.server.techmine.ReturnCode Output;
      Output = TechmineFactory.getTechmineAPI().addTopBestPost(topBestPost);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_REMOVE_TOP_BEST_POST: {
      TecTopBestPost topBestPost;
      topBestPost = new Gson().fromJson(
          (String) arg0.getSerializableExtra(INPUT), TecTopBestPost.class);
      com.technion.coolie.server.techmine.ReturnCode Output;
      Output = TechmineFactory.getTechmineAPI().removeTopBestPost(topBestPost);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_GET_TOP_BEST_POST: {
      List<TecPost> Output;
      Output = TechmineFactory.getTechmineAPI().getTopBestPosts();
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_ADD_TOP_BEST_COMMENT: {
      TecTopBestComment topBestComment;
      topBestComment = new Gson().fromJson(
          (String) arg0.getSerializableExtra(INPUT), TecTopBestComment.class);
      com.technion.coolie.server.techmine.ReturnCode Output;
      Output = TechmineFactory.getTechmineAPI().addTopBestComment(
          topBestComment);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_REMOVE_TOP_BEST_COMMENT: {
      TecTopBestComment topBestComment;
      topBestComment = new Gson().fromJson(
          (String) arg0.getSerializableExtra(INPUT), TecTopBestComment.class);
      com.technion.coolie.server.techmine.ReturnCode Output;
      Output = TechmineFactory.getTechmineAPI().removeTopBestComment(
          topBestComment);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_GET_TOP_BEST_COMMENT: {
      List<TecComment> Output;
      Output = TechmineFactory.getTechmineAPI().getTopBestComments();
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_ADD_TEC_POST: {
      TecPost tecPost;
      tecPost = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          TecPost.class);
      com.technion.coolie.server.techmine.ReturnCode Output;
      Output = TechmineFactory.getTechmineAPI().addTecPost(tecPost);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_REMOVE_TEC_POST: {
      TecPost tecPost;
      tecPost = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          TecPost.class);
      com.technion.coolie.server.techmine.ReturnCode Output;
      Output = TechmineFactory.getTechmineAPI().removeTecPost(tecPost);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_GET_TEC_POST: {
      TecPost tecPost;
      tecPost = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          TecPost.class);
      TecPost Output;
      Output = TechmineFactory.getTechmineAPI().getTecPost(tecPost);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_ADD_TEC_COMMENT: {
      TecComment tecComment;
      tecComment = new Gson().fromJson(
          (String) arg0.getSerializableExtra(INPUT), TecComment.class);
      com.technion.coolie.server.techmine.ReturnCode Output;
      Output = TechmineFactory.getTechmineAPI().addTecComment(tecComment);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_REMOVE_TEC_COMMENT: {
      TecComment tecComment;
      tecComment = new Gson().fromJson(
          (String) arg0.getSerializableExtra(INPUT), TecComment.class);
      com.technion.coolie.server.techmine.ReturnCode Output;
      Output = TechmineFactory.getTechmineAPI().removeTecComment(tecComment);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_GET_TEC_COMMENT: {
      TecComment tecComment;
      tecComment = new Gson().fromJson(
          (String) arg0.getSerializableExtra(INPUT), TecComment.class);
      TecComment Output;
      Output = TechmineFactory.getTechmineAPI().getTecComment(tecComment);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_ADD_TEC_LIKE: {
      TecLike tecLike;
      tecLike = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          TecLike.class);
      com.technion.coolie.server.techmine.ReturnCode Output;
      Output = TechmineFactory.getTechmineAPI().addTecLike(tecLike);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_REMOVE_TEC_LIKE: {
      TecLike tecLike;
      tecLike = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          TecLike.class);
      com.technion.coolie.server.techmine.ReturnCode Output;
      Output = TechmineFactory.getTechmineAPI().removeTecLike(tecLike);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHMINE_GET_TEC_LIKE: {
      TecLike tecLike;
      tecLike = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          TecLike.class);
      TecLike Output;
      Output = TechmineFactory.getTechmineAPI().getTecLike(tecLike);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(Output));
      sendBroadcast(intent);
      break;
    }

    case TECHOINS_GET_ALL_USER_POSTS: {
      TecUser user;
      List<TecPost> OutputList;
      user = new Gson().fromJson((String) arg0.getSerializableExtra(INPUT),
          TecUser.class);

      OutputList = TechmineFactory.getTechmineAPI().getAllUserPosts(user);
      intent.putExtra(STATUS, CoolieStatus.RESULT_OK);
      intent.putExtra(CoolieServerInterfaceService.RESULT,
          new Gson().toJson(OutputList));
      sendBroadcast(intent);
      break;
    }

    }

  }

}
