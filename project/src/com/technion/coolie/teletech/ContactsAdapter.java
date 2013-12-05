/**
 * 
 */
package com.technion.coolie.teletech;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Argaman
 * 
 */
public class ContactsAdapter extends ArrayAdapter<ContactInformation> {

	private List<ContactInformation> contacts;
	private Context context;

	public static int indexSelected;

	private class ContactTag {

		public TextView firstName;
		public TextView lastName;
		public TextView position;
		public TextView comma;
		public TextView faculty;

		public ContactTag(TextView firstName, TextView lastName,
				TextView position, TextView comma, TextView faculty) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.position = position;
			this.comma = comma;
			this.faculty = faculty;
		}

	}

	public ContactsAdapter(Context context, int resource,
			List<ContactInformation> objects) {
		super(context, resource, objects);
		contacts = objects;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View result;
		if (convertView != null)
			result = convertView;
		else
			result = initiateView();
		ContactTag tag = (ContactTag) result.getTag();
		tag.firstName.setText(contacts.get(position).firstName());
		tag.lastName.setText(contacts.get(position).lastName());
		tag.position.setText(contacts.get(position).contactPosition()
				.toString());
		tag.comma.setText(",");
		tag.faculty.setText(contacts.get(position).faculty());
		if (position == indexSelected)
			result.setBackgroundColor(result.getResources().getColor(
					android.R.color.holo_blue_light));
		else
			result.setBackgroundColor(result.getResources().getColor(
					android.R.color.background_light));
		return result;
	}

	/**
	 * @return
	 */
	private View initiateView() {
		View result;
		result = LayoutInflater.from(context).inflate(
				com.technion.coolie.R.layout.teletech_contact_list_element,
				null);
		result.setTag(initiateTagForView(result));
		return result;
	}

	private ContactTag initiateTagForView(View result) {
		return new ContactTag(
				(TextView) result
						.findViewById(com.technion.coolie.R.id.personFirstName),
				(TextView) result
						.findViewById(com.technion.coolie.R.id.personLastName),
				(TextView) result
						.findViewById(com.technion.coolie.R.id.position),
				(TextView) result.findViewById(com.technion.coolie.R.id.comma),
				(TextView) result
						.findViewById(com.technion.coolie.R.id.faculty));
	}
}
