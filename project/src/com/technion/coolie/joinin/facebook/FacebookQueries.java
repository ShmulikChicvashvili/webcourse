package com.technion.coolie.joinin.facebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.technion.coolie.FacebookLogin;
import com.technion.coolie.FacebookUser;

/**
 * Class for performing Facebook Queries in FQL language.
 * 
 * IMPORTANT: Any activity wishing to use this class, should call onResult in
 * her onActivityResult method
 * 
 * @author Ido
 * 
 */
public class FacebookQueries {
  private static final List<String> EVENTS_PERMISSIONS = Arrays.asList("user_events", "friends_events");
  private static final List<String> GROUPS_PERMISSIONS = Arrays.asList("user_groups", "friends_groups");
  
  /**
   * Any Activity wishing to use this class should call this method in its
   * onActivityResult. Takes care of inner session changes when resuming from
   * facebook app.
   * 
   * @param a
   *          - the calling activity
   * @param requestCode
   *          - requestCode from onActivityResult
   * @param resultCode
   *          - resultCode from onActivityResult
   * @param data
   *          - data from onActivityResult
   */
  public static void onResult(final Activity a, final int requestCode, final int resultCode, final Intent data) {
    if (Session.getActiveSession() != null)
      Session.getActiveSession().onActivityResult(a, requestCode, resultCode, data);
  }
  
  /**
   * Performs an asynchronous request to Facebook in order to get all the users
   * friends.
   * 
   * @param callback
   *          - callback to activate when asynchronous call returns.
   * @return true if we have an open session, false otherwise. If false is
   *         returned, the callback won't be activated and no query will be send
   *         to Facebook.
   */
  public static boolean getUserFriends(final OnGetUserFriendsReturns callback) {
    if (!FacebookLogin.hasOpenSession())
      return false;
    final Bundle params = new Bundle();
    params.putString("q", "SELECT username FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me())");
    final Request request = new Request(Session.getActiveSession(), "/fql", params, HttpMethod.GET, new Request.Callback() {
      @Override public void onCompleted(final Response response) {
        final ArrayList<String> userNames = new ArrayList<String>();
        final GraphObject graphObject = response.getGraphObject();
        if (graphObject != null) {
          final JSONObject jsonObject = graphObject.getInnerJSONObject();
          try {
            final JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.length(); i++)
              userNames.add(((JSONObject) array.get(i)).get("username").toString());
          } catch (final JSONException e) {
            e.printStackTrace();
          }
        }
        callback.onGetUserFriendsReturns(userNames);
      }
    });
    Request.executeBatchAsync(request);
    return true;
  }
  
  /**
   * Interface to implement in order to use getUserFriends.
   * 
   * @author Ido
   * 
   */
  public interface OnGetUserFriendsReturns {
    /**
     * Callback that will be called after getUserFriends asynchronous call
     * returns.
     * 
     * @param userNames
     *          - list of usernames. In practice this is a list of all my
     *          Facebook friends.
     */
    public void onGetUserFriendsReturns(List<String> userNames);
  }
  
  /**
   * Performs an asynchronous request to Facebook in order to get all the
   * Facebook events that the user is attending to.
   * 
   * @param activity
   *          - the current activity.
   * @param callback
   *          - callback to activate when asynchronous call returns.
   * @return true if we have an open session, false otherwise. If false is
   *         returned, the callback won't be activated and no query will be send
   *         to Facebook.
   */
  public static boolean getUserEvents(final Activity activity, final OnGetUserEventsReturns callback) {
    if (!FacebookLogin.hasOpenSession())
      return false;
    ensurePermissions(activity, Session.getActiveSession(), EVENTS_PERMISSIONS);
    final Bundle params = new Bundle();
    params.putString("q", "SELECT eid,name,location,start_time,description  FROM event WHERE eid IN "
        + "(SELECT eid FROM event_member WHERE uid = me() AND rsvp_status=\"attending\")");
    final Request request = new Request(Session.getActiveSession(), "/fql", params, HttpMethod.GET, new Request.Callback() {
      @Override public void onCompleted(final Response response) {
        final ArrayList<FacebookEvent> events = new ArrayList<FacebookEvent>();
        final GraphObject graphObject = response.getGraphObject();
        if (graphObject != null) {
          final JSONObject jsonObject = graphObject.getInnerJSONObject();
          try {
            final JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
              final JSONObject object = (JSONObject) array.get(i);
              events.add(new FacebookEvent(Long.valueOf(object.get("eid").toString()), object.get("name").toString(), object.get(
                  "location").toString(), object.get("description").toString(), FacebookTimeConversion.timeConversion(object.get(
                  "start_time").toString())));
            }
          } catch (final JSONException e) {
            e.printStackTrace();
          }
        }
        callback.onGetUserEventsReturns(events);
      }
    });
    Request.executeBatchAsync(request);
    return true;
  }
  
  /**
   * Interface to implement in order to use getUserEvents.
   * 
   * @author Ido
   * 
   */
  public interface OnGetUserEventsReturns {
    /**
     * Callback that will be called after getUserEvents asynchronous call
     * returns.
     * 
     * @param events
     *          - list of FacebookEvents. In practice this is a list of all my
     *          Facebook events that I'm attending to.
     */
    public void onGetUserEventsReturns(List<FacebookEvent> events);
  }
  
  /**
   * Ensures the app has sufficient permissions before making a request. It
   * requests for new permissions if some of them are missing.
   * 
   * @param activity
   *          - the current activity.
   * @param session
   *          - the current session.
   * @param neededPermissions
   *          - the needed permissions to perform this action.
   */
  private static void ensurePermissions(final Activity activity, final Session session, final List<String> neededPermissions) {
    if (isSubsetOf(neededPermissions, session.getPermissions()))
      return;
    final Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(activity, neededPermissions);
    session.requestNewReadPermissions(newPermissionsRequest);
  }
  
  /**
   * @param subset
   * @param superset
   * @return true if the subset is contained in the superset, false otherwise.
   */
  private static boolean isSubsetOf(final Collection<String> subset, final Collection<String> superset) {
    for (final String string : subset)
      if (!superset.contains(string))
        return false;
    return true;
  }
  
  /**
   * 
   * Performs an asynchronous request to Facebook in order to get all the
   * attendees in a specified event.
   * 
   * @param eventID
   *          - the event ID of the queried event.
   * @param activity
   *          - the current activity.
   * @param callback
   *          - callback to activate when asynchronous call returns.
   * @return true if we have an open session, false otherwise. If false is
   *         returned, the callback won't be activated and no query will be send
   *         to Facebook.
   */
  public static boolean getEventAttendees(final Long eventID, final Activity activity, final OnGetEventAttendeesReturns callback) {
    if (!FacebookLogin.hasOpenSession())
      return false;
    ensurePermissions(activity, Session.getActiveSession(), EVENTS_PERMISSIONS);
    final Bundle params = new Bundle();
    params.putString("q",
        "SELECT username, name FROM user WHERE uid IN (SELECT uid FROM event_member WHERE eid = " + eventID.toString()
            + " AND rsvp_status=\"attending\")");
    final Request request = new Request(Session.getActiveSession(), "/fql", params, HttpMethod.GET, new Request.Callback() {
      @Override public void onCompleted(final Response response) {
        final ArrayList<FacebookUser> attendees = new ArrayList<FacebookUser>();
        final GraphObject graphObject = response.getGraphObject();
        if (graphObject != null) {
          final JSONObject jsonObject = graphObject.getInnerJSONObject();
          try {
            final JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
              final JSONObject object = (JSONObject) array.get(i);
              attendees.add(new FacebookUser(object.getString("name").toString(), object.getString("username").toString()));
            }
          } catch (final JSONException e) {
            e.printStackTrace();
          }
        }
        callback.onGetEventAttendeesReturns(attendees);
      }
    });
    Request.executeBatchAsync(request);
    return true;
  }
  
  /**
   * Interface to implement in order to use getEventAttendees.
   * 
   * @author Ido
   * 
   */
  public interface OnGetEventAttendeesReturns {
    /**
     * Callback that will be called after getEventAttendees asynchronous call
     * returns.
     * 
     * @param attendees
     *          - list of FacebookUsers that attending to this specific Facebook
     *          event.
     */
    public void onGetEventAttendeesReturns(List<FacebookUser> attendees);
  }
  
  /**
   * 
   * Performs an asynchronous request to Facebook in order to get the given
   * username full name.
   * 
   * @param username
   *          - the username we want to now its name.
   * @param callback
   *          - callback to activate when asynchronous call returns.
   * @return true if we have an open session, false otherwise. If false is
   *         returned, the callback won't be activated and no query will be send
   *         to Facebook.
   */
  public static boolean getName(final String username, final OnGetNameReturns callback) {
    if (!FacebookLogin.hasOpenSession())
      return false;
    final Bundle params = new Bundle();
    params.putString("q", "SELECT name FROM user WHERE username=\"" + username + "\"");
    final Request request = new Request(Session.getActiveSession(), "/fql", params, HttpMethod.GET, new Request.Callback() {
      @Override public void onCompleted(final Response response) {
        final FacebookUser fu = new FacebookUser();
        final GraphObject graphObject = response.getGraphObject();
        if (graphObject != null)
          try {
            fu.setFullname(((JSONObject) graphObject.getInnerJSONObject().getJSONArray("data").get(0)).getString("name").toString());
            fu.setUsername(username);
          } catch (final JSONException e) {
            e.printStackTrace();
          }
        callback.onGetNameReturns(fu);
      }
    });
    Request.executeBatchAsync(request);
    return true;
  }
  
  /**
   * Interface to implement in order to use getName.
   * 
   * @author Ido
   * 
   */
  public interface OnGetNameReturns {
    /**
     * Callback that will be called after getName asynchronous call returns.
     * 
     * @param user
     *          - the Facebook user that was requested.
     */
    public void onGetNameReturns(FacebookUser user);
  }
  
  /**
   * Performs an asynchronous request to Facebook in order to get all the
   * Facebook groups that the user is a member of.
   * 
   * @param activity
   *          - the current activity.
   * @param callback
   *          - callback to activate when asynchronous call returns.
   * @return true if we have an open session, false otherwise. If false is
   *         returned, the callback won't be activated and no query will be send
   *         to Facebook.
   */
  public static boolean getUserGroups(final Activity activity, final OnGetUserGroupsReturns callback) {
    if (!FacebookLogin.hasOpenSession())
      return false;
    ensurePermissions(activity, Session.getActiveSession(), GROUPS_PERMISSIONS);
    final Bundle params = new Bundle();
    params.putString("q", "SELECT gid, name FROM group WHERE gid IN (SELECT gid FROM group_member WHERE uid=me())");
    final Request request = new Request(Session.getActiveSession(), "/fql", params, HttpMethod.GET, new Request.Callback() {
      @Override public void onCompleted(final Response response) {
        final ArrayList<FacebookGroup> groups = new ArrayList<FacebookGroup>();
        final GraphObject graphObject = response.getGraphObject();
        if (graphObject != null)
          try {
            final JSONArray array = graphObject.getInnerJSONObject().getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
              final JSONObject object = (JSONObject) array.get(i);
              groups.add(new FacebookGroup((Long) object.get("gid"), (String) object.get("name")));
            }
          } catch (final JSONException e) {
            e.printStackTrace();
          }
        callback.onGetUserGroupsReturns(groups);
      }
    });
    Request.executeBatchAsync(request);
    return true;
  }
  
  /**
   * Interface to implement in order to use getUserGroups.
   * 
   * @author Ido
   * 
   */
  public interface OnGetUserGroupsReturns {
    /**
     * Callback that will be called after getUserGroups asynchronous call
     * returns.
     * 
     * @param groups
     *          - a list containing the FacebookGroups of the user that was
     *          requested.
     */
    public void onGetUserGroupsReturns(List<FacebookGroup> groups);
  }
  
  /**
   * 
   * Performs an asynchronous request to Facebook in order to get all the
   * members of a specified group.
   * 
   * @param groupID
   *          - the group ID of the queried group.
   * @param activity
   *          - the current activity.
   * @param callback
   *          - callback to activate when asynchronous call returns.
   * @return true if we have an open session, false otherwise. If false is
   *         returned, the callback won't be activated and no query will be send
   *         to Facebook.
   */
  public static boolean getGroupMembers(final Long groupID, final Activity activity, final OnGetGroupMembersReturns callback) {
    if (!FacebookLogin.hasOpenSession())
      return false;
    ensurePermissions(activity, Session.getActiveSession(), GROUPS_PERMISSIONS);
    final Bundle params = new Bundle();
    params.putString("q", "SELECT username FROM user WHERE uid IN (SELECT uid FROM group_member where gid = " + groupID.toString()
        + ")");
    final Request request = new Request(Session.getActiveSession(), "/fql", params, HttpMethod.GET, new Request.Callback() {
      @Override public void onCompleted(final Response response) {
        final ArrayList<String> usernames = new ArrayList<String>();
        final GraphObject graphObject = response.getGraphObject();
        if (graphObject != null)
          try {
            final JSONArray array = graphObject.getInnerJSONObject().getJSONArray("data");
            for (int i = 0; i < array.length(); i++)
              usernames.add(((JSONObject) array.get(i)).getString("username").toString());
          } catch (final JSONException e) {
            e.printStackTrace();
          }
        callback.onGetGroupMembersReturns(usernames);
      }
    });
    Request.executeBatchAsync(request);
    return true;
  }
  
  /**
   * Interface to implement in order to use getGroupMembers.
   * 
   * @author Ido
   * 
   */
  public interface OnGetGroupMembersReturns {
    /**
     * Callback that will be called after getGroupMembers asynchronous call
     * returns.
     * 
     * @param usernames
     *          - a list containing the usernames of the users who are members
     *          of the requested group.
     */
    public void onGetGroupMembersReturns(List<String> usernames);
  }
}
