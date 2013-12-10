package com.technion.coolie.letmein;

import java.util.HashMap;

import com.technion.coolie.letmein.model.ContactInfo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class ContactsAutoCompleteTextView  extends AutoCompleteTextView {

	public ContactsAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected String convertSelectionToString(Object selectedItem) {
		ContactInfo hm = (ContactInfo) selectedItem;
		return hm.name;
		
		//HashMap<String, String> hm = (HashMap<String, String>) selectedItem;
        //return hm.get("txt");
	}

}
