package com.technion.coolie.joinin.places;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

/**
 * An adapter used in the autocomplete textbox
 * 
 * @author Shimon Kama
 * 
 */
public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> {
  ArrayList<String> resultList;
  
  public PlacesAutoCompleteAdapter(final Context context, final int textViewResourceId) {
    super(context, textViewResourceId);
  }
  
  @Override public int getCount() {
    return resultList.size();
  }
  
  @Override public String getItem(final int i) {
    return resultList.get(i);
  }
  
  @Override public Filter getFilter() {
    return new Filter() {
      @Override protected FilterResults performFiltering(final CharSequence c) {
        final FilterResults $ = new FilterResults();
        if (c != null) {
          try {
            resultList = GooglePlaces.autocomplete(c.toString());
          } catch (final Exception e) {
            resultList = new ArrayList<String>();
          }
          $.values = resultList;
          $.count = resultList.size();
        }
        return $;
      }
      
      @Override protected void publishResults(final CharSequence c, final FilterResults r) {
        if (r != null && r.count > 0)
          notifyDataSetChanged();
        else
          notifyDataSetInvalidated();
      }
    };
  }
}
