package com.technion.coolie.techtrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnActionExpandListener;
import com.technion.coolie.R;

public class TechTradeActivity extends SherlockFragmentActivity{

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.techtradeactionbar, menu);

		EditText search = (EditText) menu.findItem(R.id.get_search_field_action_bar).getActionView();

	    search.setOnEditorActionListener(
	    		new TextView.OnEditorActionListener() {

	    			@Override
	    			public boolean onEditorAction(TextView v, int actionId,
	    					KeyEvent event) {
	    				startSearchActivity(v.getText().toString());
	    				return false;
	    			}
	    		}
	    		);
		 

		MenuItem menuItem = menu.findItem(R.id.get_search_field_action_bar);

		menuItem.setOnActionExpandListener(
				new OnActionExpandListener() {

					@Override
					public boolean onMenuItemActionExpand(MenuItem item) {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public boolean onMenuItemActionCollapse(MenuItem item) {
						// TODO Auto-generated method stub
						return true;
					}
				});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){

		switch (item.getItemId()){
		case R.id.get_browse_by_catagory_action_bar_catagory_clothing:
			startCategoryActivity(Category.CLOTHING);
			break; 
		case R.id.get_browse_by_catagory_action_bar_catagory_electronics:
			startCategoryActivity(Category.ELECTRONICS);
			break; 
		case R.id.get_browse_by_catagory_action_bar_catagory_lecture_summeries:
			startCategoryActivity(Category.LECTURE_SUMMARIES);
			break; 
		case R.id.get_browse_by_catagory_action_bar_catagory_other:
			startCategoryActivity(Category.OTHER);
			break; 
		case R.id.get_browse_by_catagory_action_bar_catagory_toturial_summeries:
			startCategoryActivity(Category.TUTORIAL_SUMMARIES);
			break; 
		case R.id.get_browse_by_catagory_action_bar_catagory_office_supplies:
			startCategoryActivity(Category.OFFICE_SUPPLIES);
			break; 
		case R.id.get_browse_by_catagory_action_bar_catagory_books:
			startCategoryActivity(Category.BOOKS);
			break; 
		case R.id.get_browse_by_catagory_action_bar_catagory_general_summery:
			startCategoryActivity(Category.GENERAL_SUMMARIES);
			break; 
		case R.id.get_main_menu_action_bar_home:
			Intent homeIntent = new Intent(this, MainActivity.class);
			startActivity(homeIntent);
			break;
		case R.id.get_main_menu_action_bar_publish_a_product:
			Intent publishIntent = new Intent(this, UploadProduct.class);
			startActivity(publishIntent);
			break; 
		case R.id.get_main_menu_action_bar_your_transactions:
			Intent intent = new Intent(this, TransactionsActivity.class);
			startActivity(intent);
			break; 
		}
		return true;
	} 

	private void startCategoryActivity(Category category){
		Intent intent = new Intent(this, BrowseActivity.class);
		intent.putExtra("category", category.toString());//TODO guy second iteration change to serializable
		startActivity(intent);
	}
	private void startSearchActivity(String searchTerm){
		if(searchTerm == null) return;
		Intent intent = new Intent(this, SearchActivity.class);
		intent.putExtra("searchTerm", searchTerm);
		startActivity(intent);
	}

}