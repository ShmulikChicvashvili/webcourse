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

		Cursor nameCursor = null;
		Cursor phoneCursor = null;
		Cursor emailCursor = null;
		try {
			nameCursor = contentResolver
					.query(ContactsContract.Data.CONTENT_URI,
							projection.toArray(new String[projection.size()]),
							ContactsContract.Data.MIMETYPE + " = ?",
							new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE },
							ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME);

			while (nameCursor.moveToNext()) {
				final String name = nameCursor
						.getString(nameCursor
								.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));

				final Long id = nameCursor
						.getLong(nameCursor
								.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID));
				final String idStr = String.valueOf(id);

				String imageUri;
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
					imageUri = nameCursor
							.getString(nameCursor
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
				else
					imageUri = null;

				String phoneNumber;
				phoneNumber = "";
				phoneCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
						new String[] { Phone.NUMBER, Phone.TYPE, Phone.LABEL },
						ContactsContract.Data.CONTACT_ID + "=?" + " AND "
								+ ContactsContract.Data.MIMETYPE + "='" + Phone.CONTENT_ITEM_TYPE
								+ "'", new String[] { String.valueOf(id) }, null);
				if (phoneCursor.moveToFirst()) {
					final int phoneColumn = phoneCursor.getColumnIndex("data1");
					phoneNumber = phoneCursor.getString(phoneColumn);
				}

				String email = "";

				emailCursor = contentResolver.query(
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

			return aList;
		} finally {
			if (nameCursor != null)
				nameCursor.close();

			if (phoneCursor != null)
				phoneCursor.close();

			if (emailCursor != null)
				emailCursor.close();
		}
	}

	public static Uri contactIdToTumbnailPhoto(final long id, final ContentResolver contentResolver) {
		final Uri $ = null;

		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB)
			return null;

		Cursor nameCursor = null;
		try {
			final List<String> projection = new ArrayList<String>();
			projection.add(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
			projection.add(ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID);
			projection.add(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI);

			nameCursor = contentResolver.query(
					ContactsContract.Data.CONTENT_URI,
					projection.toArray(new String[projection.size()]),
					ContactsContract.Data.MIMETYPE + " = ? AND "
							+ ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?",
					new String[] {
							ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
							Long.toString(id) },
					ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);

			if (!nameCursor.moveToNext())
				return null;

			final String uriString = nameCursor.getString(nameCursor
					.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));

			return (uriString != null) ? Uri.parse(uriString) : null;
		} finally {
			if (nameCursor != null)
				nameCursor.close();
		}
	}
}
