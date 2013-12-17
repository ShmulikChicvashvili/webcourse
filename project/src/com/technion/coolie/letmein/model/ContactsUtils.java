package com.technion.coolie.letmein.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class ContactsUtils {

	public static List<ContactInfo> getAllContacts(final ContentResolver contentResolver) {
		// Each row in the list stores country name, currency and flag
		final List<ContactInfo> aList = new ArrayList<ContactInfo>();

		final List<String> projection = new ArrayList<String>();
		projection.add(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME);
		projection.add(ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID);

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
			projection.add(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI);

		final Cursor nameCur = contentResolver.query(ContactsContract.Data.CONTENT_URI,
				projection.toArray(new String[projection.size()]), ContactsContract.Data.MIMETYPE
						+ " = ?",
				new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE },
				ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME);

		while (nameCur.moveToNext()) {
			final String name = nameCur.getString(nameCur
					.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));

			final Long id = nameCur.getLong(nameCur
					.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID));
			final String idStr = String.valueOf(id);

			String imageUri;
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
				imageUri = nameCur
						.getString(nameCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
			else
				imageUri = null;

			String phoneNumber;
			phoneNumber = "";
			final Cursor phoneCursor = contentResolver
					.query(ContactsContract.Data.CONTENT_URI, new String[] { Phone.NUMBER,
							Phone.TYPE, Phone.LABEL }, ContactsContract.Data.CONTACT_ID + "=?"
							+ " AND " + ContactsContract.Data.MIMETYPE + "='"
							+ Phone.CONTENT_ITEM_TYPE + "'", new String[] { String.valueOf(id) },
							null);
			if (phoneCursor.moveToFirst()) {
				final int phoneColumn = phoneCursor.getColumnIndex("data1");
				phoneNumber = phoneCursor.getString(phoneColumn);
			}

			String email = "";

			final Cursor emailCursor = contentResolver.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
					new String[] { idStr }, null);
			while (emailCursor.moveToNext()) {
				email = emailCursor.getString(emailCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				if (email == null)
					email = "";
			}
			emailCursor.close();

			// this is ugly, but android is uglier
			if (!((name == null || name.equals(""))))
				aList.add(new ContactInfo(name, id, imageUri, phoneNumber, email));

		}

		nameCur.close();
		return aList;
	}

	public static Uri contactIdToTumbnailPhoto(final long id, final ContentResolver contentResolver) {
		Uri $ = null;

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {

			final List<String> projection = new ArrayList<String>();
			projection.add(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
			projection.add(ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID);
			projection.add(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI);

			final Cursor nameCur = contentResolver.query(
					ContactsContract.Data.CONTENT_URI,
					projection.toArray(new String[projection.size()]),
					ContactsContract.Data.MIMETYPE + " = ? AND "
							+ ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?",
					new String[] {
							ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
							Long.toString(id) },
					ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);

			while (nameCur.moveToNext()) {
				final String uriString = nameCur
						.getString(nameCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
				if (uriString != null)
					$ = Uri.parse(uriString);
				else
					$ = null;

			}
		}
		return $;
	}

}
