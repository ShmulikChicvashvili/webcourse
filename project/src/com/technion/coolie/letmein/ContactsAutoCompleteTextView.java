package com.technion.coolie.letmein;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import com.technion.coolie.letmein.model.ContactInfo;

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
