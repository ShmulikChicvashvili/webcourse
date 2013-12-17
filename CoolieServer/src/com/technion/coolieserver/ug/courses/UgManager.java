package com.technion.coolieserver.ug.courses;

import static com.technion.coolieserver.framework.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Ref;
import com.technion.coolieserver.ug.ReturnCodesUg;
import com.technion.coolieserver.ug.framework.CourseClient;
import com.technion.coolieserver.ug.framework.CourseServer;
import com.technion.coolieserver.ug.framework.GroupOfCoursesClient;
import com.technion.coolieserver.ug.framework.GroupOfCoursesServer;
import com.technion.coolieserver.ug.framework.Semester;

public class UgManager {

  public static ReturnCodesUg addCourse(CourseClient courseClient) {
    if (isCourseExsists(courseClient.getCourseNumber(),
        courseClient.getSemester()))
      return ReturnCodesUg.COURSE_ALREADY_EXSIST;
    CourseServer courseServer = new CourseServer();
    courseServer.setBasicVars(courseClient);

    courseServer.setPrerequisites(new ArrayList<Ref<GroupOfCoursesServer>>());
    for (GroupOfCoursesClient goc : courseClient.getPrerequisites()) {
      GroupOfCoursesServer serverGOC = new GroupOfCoursesServer(
          goc.getCourses());
      ofy().save().entity(serverGOC).now();
      courseServer.getPrerequisites().add(Ref.create(serverGOC));
    }
    ofy().save().entity(courseServer).now();
    return ReturnCodesUg.SUCCESS;
  }

  public static List<CourseClient> findCourseByNumber(String courseNumber) {
    List<CourseServer> courses = ofy().load().type(CourseServer.class)
        .filter("courseNumber", courseNumber).list();

    List<CourseClient> coursesClient = new ArrayList<CourseClient>();

    for (CourseServer courseServer : courses) {
      List<GroupOfCoursesClient> clientGocs = new ArrayList<>();
      for (GroupOfCoursesServer gocServer : courseServer.getPrerequisitesList())
        clientGocs.add(new GroupOfCoursesClient(gocServer.getCourses()));
      coursesClient.add(new CourseClient(courseServer.getCourseNumber(),
          courseServer.getName(), courseServer.getPoints(), courseServer
              .getDescription(), courseServer.getSemester(), courseServer
              .getFaculty(), courseServer.getMoedA(), courseServer.getMoedB(),
          clientGocs));
    }
    return coursesClient;
  }

  public static boolean isCourseExsists(String courseNumber, Semester semester) {
    return ofy().load().type(CourseServer.class)
        .filter("courseNumber", courseNumber)
        .filter("semester.ss", semester.getSs())
        .filter("semester.year", semester.getYear()).first().now() != null;
  }

  private static boolean chekcCourseNumber(String courseNumber) {
    int courseID = Integer.parseInt(courseNumber);
    return courseID < 1000000 && courseID >= 0 || true;
  }
}
