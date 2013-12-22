package com.technion.coolie.joinin.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.technion.coolie.R;
import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;
import com.technion.coolie.joinin.data.ClientAccount;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.data.SerializableSparseBooleanArrayContainer;
import com.technion.coolie.joinin.directions.MapDirections;
import com.technion.coolie.joinin.facebook.FacebookLogin;
import com.technion.coolie.joinin.places.SearchDialog;
import com.technion.coolie.joinin.subactivities.CreateEventActivity;
import com.technion.coolie.joinin.subactivities.EventActivity;
import com.technion.coolie.joinin.subactivities.EventFilterActivity;
import com.technion.coolie.joinin.subactivities.LoginActivity;
import com.technion.coolie.joinin.subactivities.MyEventsActivity;
import com.technion.coolie.joinin.subactivities.SettingsActivity;
import com.technion.coolie.joinin.subactivities.TutorialActivity;

/**
 * TeamApp Map activity
 * 
 * @author Ido Gonen, Yaniv Beaudoin, Shimon Kama, Tom Yitav (on different
 *         iterations) (with small help from On)
 * 
 */
public class MainMapActivity extends SherlockFragmentActivity implements LocationListener {
  GoogleMap map;
  final Activity mContext = this;
  public static ClientAccount mLoggedAccount = null;
  final HashMap<Marker, ClientEvent> markerToEvent = new HashMap<Marker, ClientEvent>();
  int cameraChangedCounter = 0;
  private final double TO_E6 = 1000000.0;
  private final int CAMERA_CHANGE_SENSETIVITY = 4;
  private final int MIN_ICON_DIVISION = 1;
  private final int MAX_ICON_DIVISION = 6;
  public static final String SENDER_ID = "951960160603";
  public static final int RESULT_REFRESH = RESULT_CANCELED + 1;
  public static final int RESULT_REFRESH_DIRECTIONS = RESULT_REFRESH + 1;
  public static final int RESULT_LOGIN_ACCOUNT = RESULT_REFRESH_DIRECTIONS + 1;
  public static final int RESULT_FINISH = RESULT_LOGIN_ACCOUNT + 1;
  public static final int RESULT_DO_NOTHING = RESULT_FINISH + 1;
  public static final int RESULT_FILTER = RESULT_DO_NOTHING + 1;
  public static final int RESULT_FAVORITE = RESULT_FILTER + 1;
  public static final int RESULT_DELETE = RESULT_FAVORITE + 1;
  public static String PACKAGE = "il.ac.technion.cs.cs234311.teamapp";
  SharedPreferences mTeamAppPref;
  public static final String PREFS_NAME = PACKAGE; // SharedPreferences file
  int THEME = R.style.Sherlock___Theme_Light;
  private Uri shareURI;
  private String mRegId = null;
  private SparseBooleanArray catFilter;
  private ArrayList<String> usersFilter;
  MapDirections md;
  Location location;
  private MenuItem addEventButon;
  
  @Override protected void onStop() {
    super.onStop();
  }
  
  /**
   * Init shared preferences at activity first run.
   * 
   * @param login
   *          if true the user will be transfered to the login activity
   */
  private void handleLoggedAccount(final boolean login) {
    mTeamAppPref = getSharedPreferences(PREFS_NAME, 0);
    mTeamAppPref.edit().commit();
    mLoggedAccount = mTeamAppPref.contains("account") ? ClientAccount.fromJson(mTeamAppPref.getString("account", "")) : null;
    if (login || mLoggedAccount == null)
      startActivityForResult(new Intent(this, LoginActivity.class), RESULT_LOGIN_ACCOUNT);
    else
      displaySharedEvent();
  }
  
  private static final String PLUS_TITLE = "Plus";
  private static final String V_TITLE = "V";
  
  @Override public boolean onCreateOptionsMenu(final com.actionbarsherlock.view.Menu menu) {
    getSupportMenuInflater().inflate(R.menu.ji_main_map_menu, menu);
    addEventButon = menu.findItem(R.id.menu_add);
    addEventButon.setTitle(PLUS_TITLE);
    return true;
  }
  
  /**
   * Sets the GCM and updating regId to match logged account.
   */
  private void registerDiviceToUserOnGCM() {
    if (mLoggedAccount == null)
      throw new RuntimeException("setGcm: mLoggedAccount == null");
    mRegId = GCMRegistrar.getRegistrationId(this);
    if (mRegId.equals("")) {
      Log.v("MainMapActivity", "GCMRegistrar.register(this, SENDER_ID);");
      GCMRegistrar.register(this, SENDER_ID);
      mRegId = GCMRegistrar.getRegistrationId(this);
    } else {
      Log.v("MainMapActivity", "Already registered: " + mRegId);
      ClientProxy.gcmRegisterAsync(mRegId, mLoggedAccount.getUsername());
    }
  }
  
  /**
   * Initialize the category array for the filter activity. Sets all categories
   * on.
   */
  private void initCategoriesArray() {
    catFilter = new SparseBooleanArray(EventType.values().length);
    for (int i = 0; i < EventType.values().length; i++)
      catFilter.put(i, true);
  }
  
  /**
   * Sets all components of the map, after gcm initialization and account
   * identification
   */
  @Override protected void onCreate(final Bundle savedInstanceState) {
    setTheme(THEME);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ji_map);
    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    if (!prefs.getBoolean(getString(R.string.pref_previously_started), false)) {
      prefs.edit().putBoolean(getString(R.string.pref_previously_started), true).commit();
      startActivity(new Intent(mContext, TutorialActivity.class));
    }
    GCMRegistrar.checkDevice(this);
    GCMRegistrar.checkManifest(this);
    shareURI = getIntent().getData();
    handleLoggedAccount(shareURI == null);
    initCategoriesArray();
    map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
    setupMap();
  }
  
  /**
   * Prepare map (an instance of GoogleMap object) for action
   */
  private void setupMap() {
    initializeMap();
    addMapListeners();
    refresh();
  }
  
  /**
   * Set initial zoom and Map center to current gps location.
   */
  private void initializeMap() {
    md = new MapDirections(map);
    map.setMyLocationEnabled(true);
    final LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
    if (!service.isProviderEnabled(LocationManager.GPS_PROVIDER))
      onProviderDisabled(LocationManager.GPS_PROVIDER);
    service.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 100, this);
    location = service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    if (location != null) {
      map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
      map.animateCamera(CameraUpdateFactory.zoomTo(17));
    }
  }
  
  /**
   * Listener (handlers) to be executed when long pressing, scrolling,
   * rezooming, gps location change, pressing marker...
   */
  private void addMapListeners() {
    map.setInfoWindowAdapter(new HebrewInfoAdapter(getLayoutInflater()));
    map.setOnCameraChangeListener(new OnCameraChangeListener() {
      @Override public void onCameraChange(final CameraPosition cameraPosition) {
        Log.e("", "Camera change detected, current locations is: " + cameraPosition.target + " radius is " + getMapRadius());
        cameraChangedCounter++;
        if (cameraChangedCounter == CAMERA_CHANGE_SENSETIVITY)
          refresh();
      }
    });
    map.setOnMarkerClickListener(new OnMarkerClickListener() {
      @Override public boolean onMarkerClick(final Marker m) {
        Log.e("", "Click on marker " + m.getId() + " Detected");
        cameraChangedCounter = 0;
        return false;
      }
    });
    map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
      @Override public void onInfoWindowClick(final Marker m) {
        Log.e("", "Click on marker " + m.getId() + " Detected");
        cameraChangedCounter = 0;
        final ClientEvent e = markerToEvent.get(m);
        final Intent startEventActivity = new Intent(mContext, EventActivity.class);
        if (e != null)
          startEventActivity.putExtra("event", e);
        startEventActivity.putExtra("account", mLoggedAccount);
        startActivityForResult(startEventActivity, 1);
      }
    });
  }
  
  /**
   * Clear all events' markers, and reload events from server in a radius set by
   * map dimensions
   * 
   * @param drawDirections
   *          a boolean indicating whether to re-draw directions or not.
   */
  void refresh(final boolean drawDirections) {
    Log.e("", "Refreshing...");
    cameraChangedCounter = 0;
    ClientProxy.getEventsByRadius(getMapRadius(), (int) (map.getCameraPosition().target.latitude * TO_E6),
        (int) (map.getCameraPosition().target.longitude * TO_E6), new ClientProxy.OnDone<List<ClientEvent>>() {
          @Override public void onDone(final List<ClientEvent> l) {
            map.clear();
            if (drawDirections && location != null)
              md.drawAllActiveRoutes(mContext, new LatLng(location.getLatitude(), location.getLongitude()));
            else
              md.removeDirections();
            markerToEvent.clear();
            drawEvents(l);
          }
        }, new OnError(this) {
          @Override public void beforeHandlingError() {
            // Do nothing
          }
        });
  }
  
  /**
   * Clear all events' markers, and reload events from server in a radius set by
   * map dimensions. Re-draws directions as default.
   */
  void refresh() {
    refresh(true);
  }
  
  /**
   * @param eventsList
   *          - a list of client events to draw on map
   * 
   *          Draw events on the map, that match filters in the arrays
   *          catFilter, usersFilter
   */
  protected void drawEvents(final List<ClientEvent> eventsList) {
    for (final ClientEvent e : eventsList)
      if (catFilter.get(e.getEventType().ordinal()) && (usersFilter == null || usersFilter.contains(e.getOwner())))
        addEventToMap(e);
//    if (usersFilter == null) {
//      for (final ClientEvent e : eventsList)
//        if (catFilter.get(e.getEventType().ordinal()))
//          addEventToMap(e);
//    } else
//      for (final ClientEvent e : eventsList)
//        if (catFilter.get(e.getEventType().ordinal()) && usersFilter.contains(e.getOwner()))
//          addEventToMap(e); TODO: check if it works...
  }
  
  /**
   * @param e
   *          - Client event that will be set on map by suitable marker
   * 
   *          this method adds a marker to the map that represents the event e
   */
  private void addEventToMap(final ClientEvent e) {
    markerToEvent.put(
        map.addMarker(new MarkerOptions().position(new LatLng(e.getLatitude() / TO_E6, e.getLongitude() / TO_E6))
            .title(e.getName()).snippet(e.getOwner()).icon(getIcon(e.getEventType()))), e);
  }
  
  /**
   * @param e
   *          - Client event whose marker needs to be deleted from map
   */
  private void removeEventFromMap(final ClientEvent e) {
    for (final Marker m : markerToEvent.keySet())
      if (markerToEvent.get(m).equals(e)) {
        markerToEvent.remove(m);
        m.remove();
        return;
      }
  }
  
  /**
   * @param et
   *          - EventType of a certain event
   * @return - a BitmapDescriptor for the icon that will be shown on map
   */
  private BitmapDescriptor getIcon(final EventType et) {
    Bitmap bitmap = ((BitmapDrawable) et.getDrawable(this)).getBitmap();
    final int iconDivisionRate = (int) Math
        .ceil(Math.min(MAX_ICON_DIVISION, Math.max(MIN_ICON_DIVISION, Math.sqrt(getMapRadius()))));
    bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / iconDivisionRate, bitmap.getHeight() / iconDivisionRate, false);
    return BitmapDescriptorFactory.fromBitmap(bitmap);
  }
  
  /**
   * @return radius - the current map radius in km set by zoom.
   */
  int getMapRadius() {
    final LatLng nearLeft = map.getProjection().getVisibleRegion().nearLeft;
    final LatLng farRight = map.getProjection().getVisibleRegion().farRight;
    final float[] results = { 0 };
    Location.distanceBetween(nearLeft.latitude, nearLeft.longitude, farRight.latitude, farRight.longitude, results);
    return (int) (results[0] / 2000 + 1);
  }
  
  private void toPlus() {
    addEventButon.setIcon(R.drawable.ji_plus).setTitle(PLUS_TITLE);
    findViewById(R.id.map_center).setVisibility(View.INVISIBLE);
  }
  
  @Override public boolean onOptionsItemSelected(final MenuItem item) {
    if (item.getItemId() != R.id.menu_add && addEventButon.getTitle().equals(V_TITLE))
      toPlus();
    switch (item.getItemId()) {
      case R.id.menu_refresh:
        refresh(false);
        break;
      case R.id.menu_my_events:
        startActivityForResult(new Intent(this, MyEventsActivity.class).putExtra("account", mLoggedAccount), 1);
        break;
      case R.id.menu_search:
        new SearchDialog(this, map).show();
        break;
      case R.id.menu_mode:
        if (map.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
          map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
          item.setTitle("Map View");
        } else {
          map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
          item.setTitle("Satellite View");
        }
        break;
      case R.id.menu_filter:
        startActivityForResult(
            new Intent(this, EventFilterActivity.class).putExtra("categories",
                new SerializableSparseBooleanArrayContainer(catFilter)).putExtra("account", mLoggedAccount), RESULT_FILTER);
        break;
      case R.id.menu_settings:
        startActivityForResult(new Intent(mContext, SettingsActivity.class).putExtra("account", mLoggedAccount), RESULT_FAVORITE);
        break;
      case R.id.menu_logout:
        userLogout();
        startActivityForResult(new Intent(this, LoginActivity.class), 1);
        break;
      case R.id.menu_add:
        if (addEventButon.getTitle().equals(PLUS_TITLE)) {
          addEventButon.setIcon(R.drawable.ji_navigation_accept).setTitle(V_TITLE);
          findViewById(R.id.map_center).setVisibility(View.VISIBLE);
        } else {
          toPlus();
          final LatLng gp = map.getCameraPosition().target;
          startActivityForResult(new Intent(mContext, CreateEventActivity.class).putExtra("Latitude", (int) (gp.latitude * TO_E6))
              .putExtra("Longtitude", (int) (gp.longitude * TO_E6)).putExtra("account", mLoggedAccount), 0);
        }
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }
  
  /**
   * Loggs out a user from the application. Notice it also means the user won't
   * be able to get notifications!
   */
  void userLogout() {
    ClientProxy.gcmUnregisterAsync(mRegId, mLoggedAccount.getUsername());
    mLoggedAccount = null;
    mTeamAppPref.edit().clear().commit();
    FacebookLogin.logout();
  }
  
  @SuppressWarnings("unchecked") @Override protected void onActivityResult(final int requestCode, final int resultCode,
      final Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (resultCode) {
      case RESULT_DO_NOTHING:
        break;
      case RESULT_FINISH:
        ((LocationManager) getSystemService(LOCATION_SERVICE)).removeUpdates(this);
        finish();
        break;
      case RESULT_LOGIN_ACCOUNT:
        mLoggedAccount = (ClientAccount) data.getParcelableExtra("account");
        mTeamAppPref.edit().clear().putString("account", mLoggedAccount.toJson()).commit();
        registerDiviceToUserOnGCM();
        if (shareURI != null)
          displaySharedEvent();
        break;
      case RESULT_REFRESH:
        refresh();
        break;
      case RESULT_FILTER:
        usersFilter = (ArrayList<String>) data.getExtras().get("friends");
        catFilter = ((SerializableSparseBooleanArrayContainer) data.getExtras().get("categories")).getSparseArray();
        refresh();
        break;
      case RESULT_FAVORITE:
        mLoggedAccount = (ClientAccount) data.getParcelableExtra("account");
        break;
      case RESULT_REFRESH_DIRECTIONS:
        final ClientEvent e = (ClientEvent) data.getParcelableExtra("event");
        md.removeDirections(e);
        if (location != null)
          md.drawDirections(this, e, new LatLng(location.getLatitude(), location.getLongitude()));
        else
          onProviderDisabled(LocationManager.GPS_PROVIDER);
        for (final Marker m : markerToEvent.keySet())
          if (markerToEvent.get(m).equals(e)) {
            markerToEvent.put(m, e);
            break;
          }
        break;
      case RESULT_DELETE:
        final ClientEvent deleted = (ClientEvent) data.getParcelableExtra("event");
        md.removeDirections(deleted);
        removeEventFromMap(deleted);
        refresh();
        break;
      default:
        break;
    }
  }
  
  private void displaySharedEvent() {
    final String id = shareURI.getQueryParameter("eventid");
    if (id == null)
      return;
    try {
      final Intent startEventActivity = new Intent(mContext, EventActivity.class);
      startEventActivity.putExtra("eventId", Long.parseLong(id));
      startEventActivity.putExtra("account", mLoggedAccount);
      startActivityForResult(startEventActivity, 1);
    } catch (final Exception e) {
      Toast.makeText(this, R.string.share_not_found, Toast.LENGTH_SHORT).show();
    }
  }
  
  @Override public boolean onKeyDown(final int keyCode, final KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && addEventButon.getTitle().equals(V_TITLE)) {
      toPlus();
      return true;
    }
    if (keyCode == KeyEvent.KEYCODE_BACK)
      ((LocationManager) getSystemService(LOCATION_SERVICE)).removeUpdates(this);
    return super.onKeyDown(keyCode, event);
  }
  
  @Override protected void onResume() {
    super.onResume();
  }
  
  @Override public void onLocationChanged(final Location l) {
    location = l;
    map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(l.getLatitude(), l.getLongitude())));
    map.animateCamera(CameraUpdateFactory.zoomTo(17));
  }
  
  @Override public void onProviderDisabled(final String provider) {
    Toast.makeText(this, "Please activate your GPS", Toast.LENGTH_LONG).show();
  }
  
  @Override public void onProviderEnabled(final String provider) {
    // Do nothing
  }
  
  @Override public void onStatusChanged(final String provider, final int status, final Bundle extras) {
    // Do nothing
  }
}
