package com.technion.coolieserver.ug.tracking;

import static com.technion.coolieserver.framework.OfyService.ofy;

import java.util.Arrays;
import java.util.List;

import com.googlecode.objectify.VoidWork;
import com.technion.coolieserver.ug.ReturnCodesUg;
import com.technion.coolieserver.ug.framework.CourseKey;
import com.technion.coolieserver.ug.framework.CourseToTrack;

public class UgTrackingManager {

  public static ReturnCodesUg addTrackingStudent(final String studentId,
      CourseKey courseKey) {
    if (!checkIfCourseExsist(courseKey)) {
      return ReturnCodesUg.COURSE_DOES_NOT_EXSIST;
    }
    final CourseToTrack trackedCourse = findTrackedCourse(courseKey);
    if (trackedCourse == null)
      addFirstTrackingStudent(studentId, courseKey);
    else if (trackedCourse.getTrackingStudents().contains(studentId))
      return ReturnCodesUg.STUDENT_ALREADY_TRACKING;
    else
      ofy().transactNew(new VoidWork() {
        @Override
        public void vrun() {
          trackedCourse.addTrackingStudent(studentId);
          ofy().save().entity(trackedCourse);
        }
      });
    return ReturnCodesUg.SUCCESS;
  }

  private static void addFirstTrackingStudent(final String studentId,
      final CourseKey courseKey) {
    ofy().transactNew(new VoidWork() {
      @Override
      public void vrun() {
        ofy().save().entity(
            new CourseToTrack(courseKey.getNumber(), courseKey.getSemester(),
                Arrays.asList(studentId), 0));
      }
    });
  }

  public static List<String> getAllStudentTrackingAfterCourse(
      CourseKey courseKey) {
    CourseToTrack trackedCourse = findTrackedCourse(courseKey);
    if (trackedCourse == null)
      return null;
    return trackedCourse.getTrackingStudents();
  }

  public static ReturnCodesUg removeTrackingStudentFromCourse(String studentId,
      CourseKey courseKey) {
    if (!checkIfCourseExsist(courseKey))
      return ReturnCodesUg.COURSE_DOES_NOT_EXSIST;
    CourseToTrack trackedCourse = findTrackedCourse(courseKey);
    if (trackedCourse == null)
      return ReturnCodesUg.COURSE_DOES_NOT_ON_TRACK;
    else if (!trackedCourse.getTrackingStudents().contains(studentId))
      return ReturnCodesUg.STUDENT_NOT_TRACKING;
    else {
      trackedCourse.removeTrackingStudent(studentId);
      ofy().save().entity(trackedCourse);
    }
    return ReturnCodesUg.SUCCESS;
  }

  private static CourseToTrack findTrackedCourse(CourseKey courseKey) {
    return ofy().load().type(CourseToTrack.class)
        .filter("courseId", courseKey.getNumber())
        .filter("semester.ss", courseKey.getSemester().getSs())
        .filter("semester.year", courseKey.getSemester().getYear()).first()
        .now();
  }

  private static boolean checkIfCourseExsist(CourseKey courseKey) {
    // TODO Auto-generated method stub
    return true;
  }
}
