package com.technion.coolie.teletech;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTools extends SQLiteOpenHelper {

	private String primaySort;
	private String secondarySort;

	public DBTools(Context applicationContext) {

		super(applicationContext, "contactbook.db", null, 1);
		primaySort = "firstName";
		secondarySort = "lastName";

	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		createTables(database);

	}

	private static void createTables(SQLiteDatabase database) {
		String createContactsDB = "CREATE TABLE IF NOT EXISTS contacts (contactId INTEGER PRIMARY KEY UNIQUE, firstName TEXT, "
				+ "lastName TEXT, position TEXT, faculty TEXT, officeBuilding TEXT, officeRoom TEXT, "
				+ "officeNumber TEXT, mobileNumber TEXT, homeNumber TEXT, emailAddress TEXT, officeHour TEXT, "
				+ "additionalInfo TEXT, websiteAddress TEXT, timestamp TEXT, favourite INTEGER)";

		String createFavouritesDB = "CREATE TABLE IF NOT EXISTS favourites (contactId INTEGER PRIMARY KEY UNIQUE, firstName TEXT, "
				+ "lastName TEXT, position TEXT, faculty TEXT, officeBuilding TEXT, officeRoom TEXT, "
				+ "officeNumber TEXT, mobileNumber TEXT, homeNumber TEXT, emailAddress TEXT, officeHour TEXT, "
				+ "additionalInfo TEXT, websiteAddress TEXT, timestamp TEXT, favourite INTEGER)";

		database.execSQL(createContactsDB);
		database.execSQL(createFavouritesDB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {

		String deleteContacts = "DROP TABLE IF EXISTS contacts";
		String deleteFavourites = "DROP TABLE IF EXISTS favourites";

		database.execSQL(deleteContacts);
		database.execSQL(deleteFavourites);

		onCreate(database);

	}

	public void insertContact(ContactInformation contact) {

		SQLiteDatabase database = getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put("contactId", contact.ID());
		setContactData(contact, values);
		database.insertWithOnConflict("contacts", null, values,
				SQLiteDatabase.CONFLICT_IGNORE);

		database.close();

	}

	public void insertContacts(List<ContactInformation> contacts) {

		for (ContactInformation contact : contacts)
			insertContact(contact);
	}

	public void insertFavourite(ContactInformation contact) {

		SQLiteDatabase database = getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put("contactId", contact.ID());
		setContactData(contact, values);
		database.insert("favourites", null, values);

		database.close();

	}

	private static void setContactData(ContactInformation contact,
			ContentValues values) {

		values.put("firstName", contact.firstName());
		values.put("lastName", contact.lastName());
		values.put("position",
				contact.contactPosition() == null ? Position.Staff.toString()
						: contact.contactPosition().toString());
		values.put("faculty", contact.faculty());
		OfficeLocation office = contact.office() == null ? new OfficeLocation(
				"NA", "NA") : contact.office();
		values.put("officeBuilding", office.faculty());
		values.put("officeRoom", office.officeRoom());
		values.put("officeNumber", contact.officeNumber());
		values.put("mobileNumber", contact.mobileNumber());
		values.put("homeNumber", contact.homeNumber());
		values.put("emailAddress", contact.techMail());
		if (contact.officeHours() == null) {
			List<OfficeHour> list = new LinkedList<OfficeHour>();
			list.add(new OfficeHour());
			values.put("officeHour", list.get(0).toString());
		} else
			values.put("officeHour", contact.officeHours().get(0).toString());
		values.put("additionalInfo", contact.additionalInformation());
		values.put("websiteAddress", contact.website());
		values.put("timestamp", contact.timeStamp());
		values.put("favourite", contact.isFavourite() ? 1 : 0);

	}

	public int updateContact(ContactInformation contact) {

		SQLiteDatabase database = getWritableDatabase();

		ContentValues values = new ContentValues();

		setContactData(contact, values);

		database.close();

		return database.update("contacts", values, "contactId" + " = ?",
				new String[] { contact.ID().toString() });

	}

	public void deleteContact(String id) {

		SQLiteDatabase database = getWritableDatabase();

		String deleteQuery = "DELETE FROM contacts WHERE contactId='" + id
				+ "'";

		database.execSQL(deleteQuery);
		database.close();

	}

	public void deleteFavourite(String id) {

		SQLiteDatabase database = getWritableDatabase();

		String deleteQuery = "DELETE FROM favourites WHERE contactId='" + id
				+ "'";

		database.execSQL(deleteQuery);
		database.close();

	}

	public List<ContactInformation> getAllContacts() {

		List<ContactInformation> result = new LinkedList<ContactInformation>();

		String selectAllQuery = "SELECT * FROM contacts ORDER BY " + primaySort
				+ ", " + secondarySort;

		SQLiteDatabase database = getWritableDatabase();

		getContacts(result, selectAllQuery, database);

		database.close();

		return result;

	}

	public List<ContactInformation> getAllFavourites() {

		List<ContactInformation> result = new LinkedList<ContactInformation>();

		String selectAllQuery = "SELECT * FROM favourites ORDER BY "
				+ primaySort + ", " + secondarySort;

		SQLiteDatabase database = getWritableDatabase();

		getContacts(result, selectAllQuery, database);

		database.close();

		return result;

	}

	private void getContacts(List<ContactInformation> result,
			String selectAllQuery, SQLiteDatabase database) {
		Cursor cursor = database.rawQuery(selectAllQuery, null);

		if (cursor.moveToFirst())
			do {

				ContactInformation contact = new ContactInformation(
						cursor.getString(1), cursor.getString(2),
						Position.valueOf(cursor.getString(3)),
						cursor.getString(4), new OfficeLocation(
								cursor.getString(5), cursor.getString(6)),
						cursor.getString(7), cursor.getString(10),
						setOfficeHours(cursor.getString(11)),
						cursor.getString(12), cursor.getString(13));
				contact.setID(Long.valueOf(cursor.getString(0)));

				contact.setMobileNumber(cursor.getString(8));
				contact.setHomeNumber(cursor.getString(9));
				contact.setFavourite(cursor.getInt(15) == 1);
				result.add(contact);

			} while (cursor.moveToNext());

		cursor.close();
		database.close();
	}

	private LinkedList<OfficeHour> setOfficeHours(String hour) {
		// TODO : parse the string to an accurate date.
		return null;
	}

	public ContactInformation getContactInfo(String id) {

		ContactInformation result = null;

		SQLiteDatabase database = getReadableDatabase();

		String selectQuery = "SELECT * FROM contacts WHERE contactId='" + id
				+ "'";

		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst())
			do {

				result = new ContactInformation(cursor.getString(1),
						cursor.getString(2), Position.valueOf(cursor
								.getString(3)), cursor.getString(4),
						new OfficeLocation(cursor.getString(5), cursor
								.getString(6)), cursor.getString(7),
						cursor.getString(10),
						setOfficeHours(cursor.getString(11)),
						cursor.getString(12), cursor.getString(13));

				result.setID(Long.valueOf(cursor.getString(0)));

				result.setMobileNumber(cursor.getString(8));
				result.setHomeNumber(cursor.getString(9));
				result.setFavourite(cursor.getInt(15) == 1);

			} while (cursor.moveToNext());

		cursor.close();
		database.close();
		return result;

	}

	public void clearTables() {

		SQLiteDatabase database = getWritableDatabase();

		String deleteContacts = "DROP TABLE IF EXISTS contacts";
		String deleteFavourites = "DROP TABLE IF EXISTS favourites";

		database.execSQL(deleteContacts);
		database.execSQL(deleteFavourites);

		database.close();

	}

}
