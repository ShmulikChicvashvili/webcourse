package com.technion.coolie.joinin.subactivities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.technion.coolie.FacebookLogin;
import com.technion.coolie.R;
import com.technion.coolie.joinin.facebook.FacebookGroup;
import com.technion.coolie.joinin.facebook.FacebookQueries;
import com.technion.coolie.joinin.facebook.FacebookQueries.OnGetGroupMembersReturns;
import com.technion.coolie.joinin.facebook.FacebookQueries.OnGetUserGroupsReturns;
import com.technion.coolie.joinin.map.MainMapActivity;

public class GroupsFromFacebook extends Activity {
  ListView mainListView;
  ListAdapter listAdapter;
  ArrayList<FacebookGroup> faceGroups;
  Context thisOne = this;
  Activity thisAct = this;
  ProgressDialog progressDialog;
  
  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ji_groups_from_facebook);
    progressDialog = ProgressDialog.show(this, "", "Loading...");
    faceGroups = new ArrayList<FacebookGroup>();
    FacebookQueries.getUserGroups(this, new OnGetUserGroupsReturns() {
      @Override public void onGetUserGroupsReturns(final List<FacebookGroup> groups) {
        if (groups == null || groups.size() == 0)
          Toast.makeText(thisOne, "You have no groups on Facebook", Toast.LENGTH_LONG).show();
        faceGroups.addAll(groups);
        listAdapter = new ArrayAdapter<String>(thisOne, R.layout.ji_simple_list_item_1_blacked, getStringListFromFaceGroups());
        mainListView.setAdapter(listAdapter);
        progressDialog.dismiss();
      }
    });
    // Find the ListView resource.
    mainListView = (ListView) findViewById(R.id.mainListView);
    // When item is tapped, toggle checked properties of CheckBox and Category.
    mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(final AdapterView<?> parent, final View item, final int position, final long id) {
        FacebookQueries.getGroupMembers(getIdOnPosition(position), thisAct, new OnGetGroupMembersReturns() {
          @Override public void onGetGroupMembersReturns(final List<String> usernames) {
            final ArrayList<String> userNames = new ArrayList<String>();
            userNames.addAll(usernames);
            final Intent newIntent = new Intent(thisOne, EventFilterActivity.class);
            newIntent.putExtra("friends", userNames);
            newIntent.putExtra("isFaceGroup", true);
            newIntent.putExtra("groupName", (String) listAdapter.getItem(position));
            setResult(MainMapActivity.RESULT_FILTER, newIntent);
            finish();
          }
        });
      }
    });
    listAdapter = new ArrayAdapter<String>(this, R.layout.ji_simple_list_item_1_blacked, new String[0]);
    mainListView.setAdapter(listAdapter);
  }
  
  /**
   * gets the group id from its position in the list
   * 
   * @param i
   * @return the group id
   */
  @SuppressWarnings("boxing") Long getIdOnPosition(final int i) {
    for (int j = 0; j < faceGroups.size(); j++)
      if (faceGroups.get(j).getGroupName().equals(listAdapter.getItem(i)))
        return faceGroups.get(j).getGroupId();
    return (long) 0;
  }
  
  /**
   * turns the list of Facebook groups into a displayable string list
   * 
   * @return array list of the groups names
   */
  ArrayList<String> getStringListFromFaceGroups() {
    final ArrayList<String> $ = new ArrayList<String>();
    for (int i = 0; i < faceGroups.size(); i++)
      $.add(faceGroups.get(i).getGroupName());
    return $;
  }
  
  @Override protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    FacebookLogin.onResult(this, requestCode, resultCode, data);
    super.onActivityResult(requestCode, resultCode, data);
  }
}
