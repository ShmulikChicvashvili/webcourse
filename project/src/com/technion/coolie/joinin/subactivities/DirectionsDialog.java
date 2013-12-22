package com.technion.coolie.joinin.subactivities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.technion.coolie.R;
import com.technion.coolie.joinin.data.ClientEvent;
import com.technion.coolie.joinin.directions.TravelWay;
import com.technion.coolie.joinin.map.MainMapActivity;
import com.technion.coolie.joinin.waze.WazeNavigator;

/**
 * 
 * @author Shimon Kama
 * 
 */
public class DirectionsDialog extends Dialog {
  ClientEvent event;
  Activity context;
  
  public DirectionsDialog(final Activity context, final ClientEvent event) {
    super(context);
    this.context = context;
    this.event = event;
  }
  
  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    setContentView(R.layout.ji_directions_dialog);
    final ImageView driving = (ImageView) findViewById(R.id.driveButton);
    final ImageView walking = (ImageView) findViewById(R.id.walkButton);
    driving.setImageResource(event.getTravelWay().equals(TravelWay.DRIVING) ? R.drawable.ji_drive_on : R.drawable.ji_drive);
    walking.setImageResource(event.getTravelWay().equals(TravelWay.WALKING) ? R.drawable.ji_walk_on : R.drawable.ji_walk);
    driving.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v1) {
        drivingDirections();
      }
    });
    walking.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v1) {
        walkingDirections();
      }
    });
    ((ImageView) findViewById(R.id.wazeButton)).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v1) {
        dismiss();
        try {
          WazeNavigator.navigateTo(context, event.getLatitude() / 1E6, event.getLongitude() / 1E6);
        } catch (final Exception ex) {
          new AlertDialog.Builder(context).setMessage("Waze is not installed")
              .setPositiveButton("Use TeamApp", new Dialog.OnClickListener() {
                @Override public void onClick(final DialogInterface arg0, final int arg1) {
                  drivingDirections();
                }
              }).setNegativeButton("Get Waze", new Dialog.OnClickListener() {
                @Override public void onClick(final DialogInterface arg0, final int arg1) {
                  context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze")));
                }
              }).create().show();
        }
      }
    });
  }
  
  /**
   * Show driving directions
   */
  void drivingDirections() {
    event.setTravelWay(event.getTravelWay() == TravelWay.DRIVING ? TravelWay.NO_TRAVEL : TravelWay.DRIVING);
    context.setResult(MainMapActivity.RESULT_REFRESH_DIRECTIONS, new Intent().putExtra("event", event));
    context.finish();
  }
  
  /**
   * Show walking directions
   */
  void walkingDirections() {
    event.setTravelWay(event.getTravelWay() == TravelWay.WALKING ? TravelWay.NO_TRAVEL : TravelWay.WALKING);
    context.setResult(MainMapActivity.RESULT_REFRESH_DIRECTIONS, new Intent().putExtra("event", event));
    context.finish();
  }
}