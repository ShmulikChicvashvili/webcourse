package com.technion.coolie.joinin.subactivities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.technion.coolie.FBClientAccount;
import com.technion.coolie.FacebookLogin;
import com.technion.coolie.R;
import com.technion.coolie.joinin.data.SerializableSparseBooleanArrayContainer;
import com.technion.coolie.joinin.facebook.FacebookQueries;
import com.technion.coolie.joinin.facebook.FacebookQueries.OnGetUserFriendsReturns;
import com.technion.coolie.joinin.facebook.PickFriendsActivity;
import com.technion.coolie.joinin.map.EventType;
import com.technion.coolie.joinin.map.MainMapActivity;

public class EventFilterActivity extends Activity {
  protected static final int NEW_FRIENDS_GROUP = 7;
  protected static final int NEW_FACEBOOK_FRIENDS_GROUP = 8;
  SparseBooleanArray checked;
  ListView listView;
  Context c = this;
  Button newGroup, newFacebookGroup;
  ImageButton apply;
  ListView mainListView;
  CategoryItem[] categories;
  ArrayAdapter<CategoryItem> listAdapter;
  FBClientAccount mLoggedAccount;
  ArrayList<CategoryItem> categoriesList;
  SharedPreferences mTeamAppPref;
  
  @SuppressWarnings("deprecation") @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ji_activity_event_filter);
    // Find the ListView resource.
    mainListView = (ListView) findViewById(R.id.mainListView);
    mLoggedAccount = (FBClientAccount) getIntent().getExtras().get("account");
    checked = ((SerializableSparseBooleanArrayContainer) getIntent().getExtras().get("categories")).getSparseArray();
    // When item is tapped, toggle checked properties of CheckBox and
    // CategoryItem.
    mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(final AdapterView<?> parent, final View item, final int position, final long id) {
        final CategoryItem category = listAdapter.getItem(position);
        category.toggleChecked();
        ((CategoryItemViewHolder) item.getTag()).getCheckBox().setChecked(category.isChecked());
      }
    });
    mainListView.setOnItemLongClickListener(new OnItemLongClickListener() {
      @Override public boolean onItemLongClick(final AdapterView<?> parent, final View item, final int position, final long id) {
        if (listAdapter.getItem(position).isGroup() && position != 6)
          deletingTheGroupDialog(listAdapter.getItem(position).getName());
        return false;
      }
    });
    // Create and populate categories.
    categories = (CategoryItem[]) getLastNonConfigurationInstance();
    if (categories == null)
      categories = new CategoryItem[] { new CategoryItem("Sports", "sports", checked.get(0)),
          new CategoryItem("Food", "food_icon", checked.get(1)), new CategoryItem("Study", "study_icon", checked.get(2)),
          new CategoryItem("Movie", "movie_icon", checked.get(3)),
          new CategoryItem("Night Life", "night_life_icon", checked.get(4)),
          new CategoryItem("Other", "other_icon", checked.get(5)), new CategoryItem("All Facebook Friends", "f", false, true) };
    setAllFaceBookFriends();
    categoriesList = new ArrayList<CategoryItem>();
    categoriesList.addAll(Arrays.asList(categories));
    getGroupsFromSharedPref();
    // Set our custom array adapter as the ListView's adapter.
    listAdapter = new CategoriesArrayAdapter(this, categoriesList);
    mainListView.setAdapter(listAdapter);
    checked = ((SerializableSparseBooleanArrayContainer) getIntent().getExtras().get("categories")).getSparseArray();
    apply = (ImageButton) findViewById(R.id.apllyButton);
    apply.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v) {
        markChecked();
        ArrayList<String> friendsFilteredBy = new ArrayList<String>();
        for (int i = 0; i < categoriesList.size(); i++)
          if (categoriesList.get(i).isGroup() && categoriesList.get(i).isChecked())
            friendsFilteredBy.addAll(Arrays.asList(categoriesList.get(i).getFriendsInGroup().split(",")));
        // remove duplications from friendsFilteredBy
        final HashSet<String> hs = new HashSet<String>();
        hs.addAll(friendsFilteredBy);
        friendsFilteredBy.clear();
        friendsFilteredBy.addAll(hs);
        if (friendsFilteredBy.isEmpty())
          friendsFilteredBy = null;
        setResult(
            MainMapActivity.RESULT_FILTER,
            new Intent().putExtra("categories", new SerializableSparseBooleanArrayContainer(checked)).putExtra("friends",
                friendsFilteredBy));
        finish();
      }
    });
    newGroup = (Button) findViewById(R.id.group_button);
    newGroup.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v) {
        final Intent friendspickerActivity = new Intent(c, PickFriendsActivity.class);
        PickFriendsActivity.populateParameters(friendspickerActivity, null, true, true);
        startActivityForResult(friendspickerActivity, NEW_FRIENDS_GROUP);
      }
    });
    newFacebookGroup = (Button) findViewById(R.id.group_facebook_button);
    newFacebookGroup.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v) {
        startActivityForResult(new Intent(c, GroupsFromFacebook.class), NEW_FRIENDS_GROUP);
      }
    });
  }
  
  /**
   * the dialog displayed upon long pressing on group to delete it
   * 
   * @param groupName
   */
  protected void deletingTheGroupDialog(final String groupName) {
    mTeamAppPref = getSharedPreferences(MainMapActivity.PREFS_NAME + mLoggedAccount.getFacebookId(), 0);
    final AlertDialog.Builder alert = new AlertDialog.Builder(this);
    alert.setTitle("Delete this group");
    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
      @Override public void onClick(final DialogInterface dialog, final int which) {
        mTeamAppPref.edit().remove("group_" + groupName);
        final ArrayList<String> hi = new ArrayList<String>(Arrays.asList(mTeamAppPref.getString("groups", "").split(",")));
        final StringBuilder groups = new StringBuilder();
        for (int i = 0; i < hi.size(); i++)
          if (hi.get(i).equals(groupName))
            hi.remove(i);
        for (int i = 0; i < hi.size(); i++)
          groups.append(hi.get(i) + ",");
        mTeamAppPref.edit().putString("groups", groups.toString()).commit();
        for (int j = 0; j < categoriesList.size(); j++)
          if (categoriesList.get(j).getName().equals(groupName))
            categoriesList.remove(j);
        listAdapter.notifyDataSetChanged();
      }
    });
    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override public void onClick(final DialogInterface dialog, final int whichButton) {
        dialog.cancel();
      }
    });
    alert.show();
  }
  
  /**
   * set the general "All Facebook friends" groups to be all of the users
   * friends
   */
  private void setAllFaceBookFriends() {
    final StringBuilder bilder = new StringBuilder();
    FacebookQueries.getUserFriends(new OnGetUserFriendsReturns() {
      @Override public void onGetUserFriendsReturns(final List<String> userNames) {
        for (final String string : userNames)
          bilder.append(string + ",");
        categories[6].setFriendsInGroup(bilder.toString());
      }
    });
  }
  
  /**
   * creates and initialize the groups which the user created and was saved on
   * his phone
   */
  private void getGroupsFromSharedPref() {
    mTeamAppPref = getSharedPreferences(MainMapActivity.PREFS_NAME + mLoggedAccount.getFacebookId(), 0);
    final String tempo = mTeamAppPref.getString("groups", null);
    if (tempo == null)
      return;
    final String[] groupsNames = tempo.split(",");
    for (final String groupsName : groupsNames) {
      if (groupsName.compareTo("") == 0)
        continue;
      final CategoryItem tempNewGroup = new CategoryItem(groupsName, "gr", false, true);
      tempNewGroup.setFriendsInGroup(mTeamAppPref.getString("group_" + groupsName, ""));
      categoriesList.add(tempNewGroup);
    }
  }
  
  /**
   * sets the local field of checked item as of the GUI's.
   */
  void markChecked() {
    for (int i = 0; i < mainListView.getCount(); i++)
      checked.put(i, ((CategoryItemViewHolder) listAdapter.getView(i, null, null).getTag()).getCheckBox().isChecked());
  }
  
  /**
   * when getting back to this activity with either result from group or filter,
   * a certain action performed such as create new facebook group automatically
   * or manualy
   */
  @SuppressWarnings("unchecked") @Override protected void onActivityResult(final int requestCode, final int resultCode,
      final Intent data) {
    FacebookLogin.onResult(this, requestCode, resultCode, data);
    // super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == MainMapActivity.RESULT_FILTER)
      switch (requestCode) { // changed from "resultCode" which was unnecessary
                             // -
                             // now changed only by the caller activity
        case MainMapActivity.RESULT_FILTER:
          Log.e("EVENT FILTER", "BACK FROM IDO");
          final Intent intent = new Intent(c, MainMapActivity.class);
          markChecked();
          intent.putExtra("categories", new SerializableSparseBooleanArrayContainer(checked));
          intent.putExtra("friends", (ArrayList<String>) data.getExtras().get("friends"));
          setResult(MainMapActivity.RESULT_FILTER, intent);
          finish();
          break;
        case NEW_FRIENDS_GROUP:
          mTeamAppPref = getSharedPreferences(MainMapActivity.PREFS_NAME + mLoggedAccount.getFacebookId(), 0);
          final AlertDialog.Builder alert = new AlertDialog.Builder(this);
          final EditText input = new EditText(this);
          if (data.getExtras().getBoolean("isFaceGroup"))
            input.setText(data.getExtras().getString("groupName"));
          alert.setView(input);
          alert.setTitle("Insert group name:");
          alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override public void onClick(final DialogInterface dialog, final int which) {
              final String inputText = input.getText().toString();
              final CategoryItem newItem = new CategoryItem(inputText.trim(), "gr", false, true);
              final ArrayList<String> friendsList = (ArrayList<String>) data.getExtras().get("friends");
              final StringBuilder group_friends = new StringBuilder();
              for (int i = 0; i < friendsList.size(); i++)
                group_friends.append(friendsList.get(i)).append(",");
              newItem.setFriendsInGroup(group_friends.toString());
              mTeamAppPref.edit().putString("group_" + inputText, group_friends.toString()).commit();
              final StringBuilder groups = new StringBuilder();
              final String grps = mTeamAppPref.getString("groups", "");
              if (grps.equals(""))
                groups.append(inputText);
              else
                groups.append(grps).append(",").append(inputText);
              mTeamAppPref.edit().putString("groups", groups.toString()).commit();
              categoriesList.add(newItem);
              listAdapter.notifyDataSetChanged();
            }
          });
          alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override public void onClick(final DialogInterface dialog, final int whichButton) {
              dialog.cancel();
            }
          });
          alert.show();
          break;
        default:
          break;
      }
  }
  
  @Override public boolean onCreateOptionsMenu(final Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.ji_activity_event_filter, menu);
    return true;
  }
  
  @Override public boolean onOptionsItemSelected(final MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.menu_my_favorites:
        checked = myFavorites();
        setResult(MainMapActivity.RESULT_FILTER,
            new Intent().putExtra("categories", new SerializableSparseBooleanArrayContainer(checked)));
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
  
  /**
   * gets the favorite categories of the user
   * 
   * @return only the favorite categories of a user via SparseBooleanArray
   */
  private SparseBooleanArray myFavorites() {
    final SparseBooleanArray $ = new SparseBooleanArray(EventType.values().length);
    final HashSet<EventType> interest = (HashSet<EventType>) mLoggedAccount.getIntrestsSet();
    for (final EventType et : EventType.values())
      $.put(et.ordinal(), interest.contains(et));
    return $;
  }
  
  // /////
  /** Holds Categories data. */
  private static class CategoryItem {
    private String name = "";
    private boolean checked = false;
    private String iconName = "other_icon";
    private boolean isGroup = false;
    private String friendsInGroup = null;
    
    @SuppressWarnings("unused") public CategoryItem() {
    }
    
    public CategoryItem(final String name, final String icon, final boolean checked) {
      this.name = name;
      iconName = icon;
      this.checked = checked;
    }
    
    public CategoryItem(final String name, final String icon, final boolean checked, final boolean isGrp) {
      this.name = name;
      iconName = icon;
      this.checked = checked;
      setIsGroup(isGrp);
    }
    
    @SuppressWarnings("unused") public CategoryItem(final String name, final boolean checked) {
      this.name = name;
      this.checked = checked;
    }
    
    public String getName() {
      return name;
    }
    
    @SuppressWarnings("unused") public void setName(final String name) {
      this.name = name;
    }
    
    public boolean isChecked() {
      return checked;
    }
    
    public void setChecked(final boolean checked) {
      this.checked = checked;
    }
    
    @Override public String toString() {
      return name;
    }
    
    public void toggleChecked() {
      checked = !checked;
    }
    
    public String getIconName() {
      return iconName;
    }
    
    @SuppressWarnings("unused") public void setIconName(final String iconName) {
      this.iconName = iconName;
    }
    
    public String getFriendsInGroup() {
      return friendsInGroup;
    }
    
    public void setFriendsInGroup(final String friendsInGroup) {
      this.friendsInGroup = friendsInGroup;
    }
    
    public boolean isGroup() {
      return isGroup;
    }
    
    public void setIsGroup(final boolean isGroup) {
      this.isGroup = isGroup;
    }
  }
  
  /** Holds child views for one row. */
  private static class CategoryItemViewHolder {
    private CheckBox checkBox;
    private TextView textView;
    private ImageView imageView;
    
    @SuppressWarnings("unused") public CategoryItemViewHolder() {
    }
    
    public CategoryItemViewHolder(final TextView textView, final CheckBox checkBox, final ImageView imageView) {
      this.checkBox = checkBox;
      this.textView = textView;
      this.imageView = imageView;
    }
    
    public CheckBox getCheckBox() {
      return checkBox;
    }
    
    @SuppressWarnings("unused") public void setCheckBox(final CheckBox checkBox) {
      this.checkBox = checkBox;
    }
    
    public TextView getTextView() {
      return textView;
    }
    
    @SuppressWarnings("unused") public void setTextView(final TextView textView) {
      this.textView = textView;
    }
    
    public ImageView getIcon() {
      return imageView;
    }
    
    @SuppressWarnings("unused") public void setIcon(final ImageView icon) {
      imageView = icon;
    }
  }
  
  /** Custom adapter for displaying an array of CategoriesItem objects. */
  private static class CategoriesArrayAdapter extends ArrayAdapter<CategoryItem> {
    private final LayoutInflater inflater;
    
    public CategoriesArrayAdapter(final Context context, final List<CategoryItem> categoriesList) {
      super(context, R.layout.ji_event_filters_item, R.id.rowTextView, categoriesList);
      // Cache the LayoutInflate to avoid asking for a new one each time.
      inflater = LayoutInflater.from(context);
    }
    
    @Override public View getView(final int position, final View convertViewParam, final ViewGroup parent) {
      // Category to display
      final CategoryItem category = getItem(position);
      // The child views in each row.
      CheckBox checkBox;
      TextView textView;
      ImageView icon;
      View convertView = convertViewParam;
      // Create a new row view
      if (convertView == null) {
        convertView = inflater.inflate(R.layout.ji_event_filters_item, null);
        // Find the child views.
        textView = (TextView) convertView.findViewById(R.id.rowTextView);
        checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);
        icon = (ImageView) convertView.findViewById(R.id.icon);
        // Optimization: Tag the row with it's child views, so we don't have to
        // call findViewById() later when we reuse the row.
        convertView.setTag(new CategoryItemViewHolder(textView, checkBox, icon));
        // If CheckBox is toggled, update the category it is tagged with.
        checkBox.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(final View v) {
            ((CategoryItem) ((CheckBox) v).getTag()).setChecked(((CheckBox) v).isChecked());
          }
        });
      }
      // Reuse existing row view
      else {
        // Because we use a ViewHolder, we avoid having to call findViewById().
        final CategoryItemViewHolder viewHolder = (CategoryItemViewHolder) convertView.getTag();
        checkBox = viewHolder.getCheckBox();
        textView = viewHolder.getTextView();
        icon = viewHolder.getIcon();
      }
      // Tag the CheckBox with the Category it is displaying, so that we can
      // access the category in onClick() when the CheckBox is toggled.
      checkBox.setTag(category);
      // Display category data
      checkBox.setChecked(category.isChecked());
      textView.setText(category.getName());
      icon.setImageResource(getContext().getResources().getIdentifier(category.getIconName(), "drawable",
          getContext().getPackageName()));
      return convertView;
    }
  }
  
  @Override public Object onRetainNonConfigurationInstance() {
    return categories;
  }
}
