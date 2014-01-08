package com.technion.coolie.joinin.subactivities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.technion.coolie.FBClientAccount;
import com.technion.coolie.R;
import com.technion.coolie.joinin.communication.ClientProxy;
import com.technion.coolie.joinin.map.EventType;
import com.technion.coolie.joinin.map.MainMapActivity;

public class MyFavoritesActivity extends Activity {
  SparseBooleanArray checked;
  ListView listView;
  Context c = this;
  ImageButton friends;
  ImageButton apply;
  ListView mainListView;
  private CategoryItem[] categories;
  ArrayAdapter<CategoryItem> listAdapter;
  FBClientAccount mLoggedAccount;
  
  @SuppressWarnings("deprecation") @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ji_activity_my_favorites);
    // Find the ListView resource.
    mLoggedAccount = (FBClientAccount) getIntent().getExtras().get("account");
    checked = getTheInterestFromTheAccount();
    mainListView = (ListView) findViewById(R.id.mainListView);
    // When item is tapped, toggle checked properties of CheckBox and Category.
    mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(final AdapterView<?> parent, final View item, final int position, final long id) {
        final CategoryItem category = listAdapter.getItem(position);
        category.toggleChecked();
        ((CategoryItemViewHolder) item.getTag()).getCheckBox().setChecked(category.isChecked());
      }
    });
    // Create and populate categories.
    categories = (CategoryItem[]) getLastNonConfigurationInstance();
    if (categories == null)
      categories = new CategoryItem[] { new CategoryItem("Sports", "sports", checked.get(0)),
          new CategoryItem("Food", "food_icon", checked.get(1)), new CategoryItem("Study", "study_icon", checked.get(2)),
          new CategoryItem("Movie", "movie_icon", checked.get(3)),
          new CategoryItem("Night Life", "night_life_icon", checked.get(4)),
          new CategoryItem("Other", "other_icon", checked.get(5)) };
    final ArrayList<CategoryItem> categoriesList = new ArrayList<CategoryItem>();
    categoriesList.addAll(Arrays.asList(categories));
    // Set our custom array adapter as the ListView's adapter.
    listAdapter = new CategoriesArrayAdapter(this, categoriesList);
    mainListView.setAdapter(listAdapter);
    apply = (ImageButton) findViewById(R.id.apllyButton);
    apply.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v) {
        markChecked();
        mLoggedAccount.clearInterests();
        for (final EventType et : EventType.values())
          if (checked.get(et.ordinal()))
            mLoggedAccount.addInterest(et);
        final ProgressDialog pd = ProgressDialog.show(c, "", "Loading...");
        pd.setCancelable(false);
        ClientProxy.addAccount(mLoggedAccount, new ClientProxy.OnDone<String>() {
          @Override public void onDone(final String userNameResponce) {
            if (!userNameResponce.equals(mLoggedAccount.getUsername())) // sanity
                                                                        // check
              throw new RuntimeException("User name returned from server diffrent from current looged username");
            pd.dismiss();
            setResult(MainMapActivity.RESULT_FAVORITE, new Intent().putExtra("account", mLoggedAccount));
            finish();
          }
        }, new ClientProxy.OnError(null) {
          @Override public void beforeHandlingError() {
            pd.dismiss();
          }
        });
      }
    });
  }
  
  /**
   * extract the current favorite categories of a a user from the account data.
   * 
   * @return the favorite events of an account as SparseBooleanArray
   */
  private SparseBooleanArray getTheInterestFromTheAccount() {
    final SparseBooleanArray $ = new SparseBooleanArray(EventType.values().length);
    for (final EventType et : EventType.values())
      $.put(et.ordinal(), mLoggedAccount.getIntrestsSet().contains(et));
    return $;
  }
  
  /**
   * sets the local field of checked item as of the GUI's.
   */
  void markChecked() {
    for (int i = 0; i < mainListView.getCount(); i++) {
      final CategoryItemViewHolder hold = (CategoryItemViewHolder) listAdapter.getView(i, null, null).getTag();
      checked.put(i, hold.getCheckBox().isChecked());
    }
  }
  
///////
  /** Holds category data. */
  private static class CategoryItem {
    private String name = "";
    private boolean checked = false;
    private String iconName = "other_icon";
    
    @SuppressWarnings("unused") public CategoryItem() {
    }
    
    public CategoryItem(final String name, final String icon, final boolean checked) {
      this.name = name;
      iconName = icon;
      this.checked = checked;
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
  
  /** Custom adapter for displaying an array of CategoryItem objects. */
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
      View $ = convertViewParam;
      // Create a new row view
      if ($ == null) {
        $ = inflater.inflate(R.layout.ji_event_filters_item, null);
        // Find the child views.
        textView = (TextView) $.findViewById(R.id.rowTextView);
        checkBox = (CheckBox) $.findViewById(R.id.CheckBox01);
        icon = (ImageView) $.findViewById(R.id.icon);
        // Optimization: Tag the row with it's child views, so we don't have to
        // call findViewById() later when we reuse the row.
        $.setTag(new CategoryItemViewHolder(textView, checkBox, icon));
        // If CheckBox is toggled, update the category it is tagged with.
        checkBox.setOnClickListener(new View.OnClickListener() {
          @SuppressWarnings("hiding") @Override public void onClick(final View v) {
            final CheckBox cb = (CheckBox) v;
            final CategoryItem category = (CategoryItem) cb.getTag();
            category.setChecked(cb.isChecked());
          }
        });
      }
      // Reuse existing row view
      else {
        // Because we use a ViewHolder, we avoid having to call findViewById().
        final CategoryItemViewHolder viewHolder = (CategoryItemViewHolder) $.getTag();
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
      return $;
    }
  }
  
  @Override public Object onRetainNonConfigurationInstance() {
    return categories;
  }
}
