package com.technion.coolie.letmein.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class ContactsUtils {

	public static List<ContactInfo> getAllContacts(ContentResolver contentResolver) {
		// Each row in the list stores country name, currency and flag
		List<ContactInfo> aList = new ArrayList<ContactInfo>();

		List<String> projection = new ArrayList<String>();
		projection.add(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
		projection.add(ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID);

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			projection.add(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI);
		}

		final Cursor nameCur = contentResolver.query(ContactsContract.Data.CONTENT_URI,
				projection.toArray(new String[projection.size()]), ContactsContract.Data.MIMETYPE
						+ " = ?",
				new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE },
				ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);

		while (nameCur.moveToNext()) {
			String name = nameCur.getString(nameCur
					.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));

			Long id = nameCur.getLong(nameCur
					.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID));
			String idStr = String.valueOf(id);

			String imageUri;
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
				imageUri = nameCur
						.getString(nameCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
			} else {
				imageUri = null;
			}

			String phoneNumber;
			phoneNumber = "";
			Cursor phoneCursor = contentResolver
					.query(ContactsContract.Data.CONTENT_URI, new String[] { Phone.NUMBER,
							Phone.TYPE, Phone.LABEL }, ContactsContract.Data.CONTACT_ID + "=?"
							+ " AND " + ContactsContract.Data.MIMETYPE + "='"
							+ Phone.CONTENT_ITEM_TYPE + "'", new String[] { String.valueOf(id) },
							null);
			if (phoneCursor.moveToFirst()) {
				int phoneColumn = phoneCursor.getColumnIndex("data1");
				phoneNumber = phoneCursor.getString(phoneColumn);
			}

			String email = "";

			Cursor emailCursor = contentResolver.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
					new String[] { idStr }, null);
			while (emailCursor.moveToNext()) {
				email = emailCursor.getString(emailCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				if (email == null) {
					email = "";
				}
			}
			emailCursor.close();

			// this is ugly, but android is uglier
			if (!((name == null || name.equals("")))) {
				aList.add(new ContactInfo(name, id, imageUri, phoneNumber, email));
			}

		}

		nameCur.close();
		return aList;
	}

	public static Uri contactIdToTumbnailPhoto(long id, ContentResolver contentResolver) {
		Uri $ = null;

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {

			List<String> projection = new ArrayList<String>();
			projection.add(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
			projection.add(ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID);

			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
				projection.add(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI);
			}

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
				$ = Uri.parse(nameCur.getString(nameCur
						.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)));
			}

		}
		return $;
	}

}
