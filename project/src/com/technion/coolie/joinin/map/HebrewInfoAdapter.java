package com.technion.coolie.joinin.map;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.technion.coolie.R;

/**
 * Allows GoogleMap to handle Hebrew as a marker's title.
 * 
 * @author Shimon Kama
 * 
 */
public class HebrewInfoAdapter implements InfoWindowAdapter {
  LayoutInflater inflater = null;
  
  public HebrewInfoAdapter(final LayoutInflater inflater) {
    this.inflater = inflater;
  }
  
  @Override public View getInfoWindow(final Marker m) {
    return null;
  }
  
  @Override public View getInfoContents(final Marker m) {
    final View $ = inflater.inflate(R.layout.ji_info_window_layout, null);
    ((TextView) $.findViewById(R.id.title)).setText(m.getTitle());
    return $;
  }
}
