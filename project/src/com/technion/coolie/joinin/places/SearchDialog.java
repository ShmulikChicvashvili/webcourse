package com.technion.coolie.joinin.places;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.technion.coolie.R;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;

/**
 * @author Shimon Kama
 * 
 */
public class SearchDialog extends Dialog {
  final Activity context;
  final GoogleMap map;
  
  public SearchDialog(final Activity context, final GoogleMap map) {
    super(context);
    this.context = context;
    this.map = map;
  }
  
  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.ji_dialog_search);
    setCanceledOnTouchOutside(false);
    final AutoCompleteTextView searchBox = (AutoCompleteTextView) findViewById(R.id.search_box);
    searchBox.setAdapter(new PlacesAutoCompleteAdapter(context, R.layout.ji_drop_down_item));
    searchBox.setOnEditorActionListener(new OnEditorActionListener() {
      @Override public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
        if (actionId != EditorInfo.IME_ACTION_SEARCH)
          return false;
        search(searchBox);
        return true;
      }
    });
    ((ImageButton) findViewById(R.id.searchButton)).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v) {
        search(searchBox);
      }
    });
  }
  
  /**
   * Perform an async. text search
   * 
   * @param searchBox
   *          the textbox containing the text
   */
  void search(final AutoCompleteTextView searchBox) {
    GooglePlaces.search(searchBox.getText().toString(), new OnDone<PlacesList>() {
      @Override public void onDone(final PlacesList pl) {
        if (pl.getResults().size() <= 0) {
          Toast.makeText(context, "No Results...", Toast.LENGTH_LONG).show();
          return;
        }
        if (pl.getResults().size() == 1) {
          dismiss();
          map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(pl.getResults().get(0).getGeometry().location.lat, pl
              .getResults().get(0).getGeometry().location.lng)));
          return;
        }
        ((LinearLayout) findViewById(R.id.results_layout)).setVisibility(View.VISIBLE);
        final LinearLayout resultList = (LinearLayout) findViewById(R.id.result_list_layout);
        resultList.removeAllViews();
        for (final Place p : pl.getResults()) {
          final LinearLayout result = (LinearLayout) getLayoutInflater().inflate(R.layout.ji_search_result_member, null);
          ((TextView) result.findViewById(R.id.result_name)).setText(p.getName());
          ((TextView) result.findViewById(R.id.result_address)).setText(p.getFormatted_address());
          result.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(final View v1) {
              dismiss();
              map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(p.getGeometry().location.lat, p.getGeometry().location.lng)));
            }
          });
          resultList.addView(result);
        }
      }
    }, new OnError(context) {
      @Override public void beforeHandlingError() {
        // Do nothing.
      }
    });
  }
}
