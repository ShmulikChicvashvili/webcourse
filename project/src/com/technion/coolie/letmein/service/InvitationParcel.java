package com.technion.coolie.letmein.service;

import java.text.SimpleDateFormat;
import java.util.EnumMap;
import java.util.Locale;

import android.util.Log;

import com.technion.coolie.letmein.CarManufacturer;
import com.technion.coolie.letmein.Consts;
import com.technion.coolie.letmein.model.Invitation;

public class InvitationParcel {
	private static final String LOG_TAG = Consts.LOG_PREFIX
			+ InvitationParcel.class.getSimpleName();

	private final String contactName;

	private final String carNumber;
	private final String carManufacturer;
	private final String carColor;

	public String arrivalTime;
	public String arrivalDate;

	public InvitationParcel(final Invitation i) {
		contactName = i.getContactName();
		carNumber = i.getCarNumber();
		carManufacturer = CarManufacturerParcel.getManufacturerParcel(i.getCarManufacturer());
		carColor = i.getCarColor();

		arrivalTime = new SimpleDateFormat("HH:mm", Locale.US).format(i.getDate());
		arrivalDate = new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(i.getDate());

		Log.d(LOG_TAG, "Generating invitation parcel" + toString());
	}

	public String getContactName() {
		return contactName;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public String getCarManufacturer() {
		return carManufacturer;
	}

	public String getCarColor() {
		return carColor;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": [" + contactName + " " + carNumber + " "
				+ carManufacturer + " " + carColor + " " + arrivalTime + " " + arrivalDate + "]";
	}

	private static class CarManufacturerParcel {
		private static final EnumMap<CarManufacturer, String> map = new EnumMap<CarManufacturer, String>(
				CarManufacturer.class);

		static {
			map.put(CarManufacturer.BMW, "\u05D1.\u05DE.\u05D5\u05D5");
			map.put(CarManufacturer.FORD, "\u05E4\u05D5\u05E8\u05D3");
			map.put(CarManufacturer.HONDA, "\u05D4\u05D5\u05E0\u05D3\u05D4");
			map.put(CarManufacturer.MAZDA, "\u05DE\u05D0\u05D6\u05D3\u05D4");
			map.put(CarManufacturer.MITSUBISHI, "\u05DE\u05D9\u05E6\u05D5\u05D1\u05D9\u05E9\u05D9");
			map.put(CarManufacturer.NISSAN, "\u05E0\u05D9\u05E1\u05DF");
			map.put(CarManufacturer.SEAT, "\u05E1\u05D9\u05D0\u05D8");
			map.put(CarManufacturer.SUBARU, "\u05E1\u05D5\u05D1\u05D0\u05E8\u05D5");
			map.put(CarManufacturer.SUZUKI, "\u05E1\u05D5\u05D6\u05D5\u05E7\u05D9");
			map.put(CarManufacturer.TOYOTA, "\u05D8\u05D5\u05D9\u05D5\u05D8\u05D4");
			map.put(CarManufacturer.VOLKSWAGEN,
					"\u05E4\u05D5\u05DC\u05E7\u05E1\u05D5\u05D5\u05D2\u05DF");
			map.put(CarManufacturer.VOLVO, "\u05D5\u05D5\u05DC\u05D5\u05D5");
			map.put(CarManufacturer.UNDEFINED, "\u05DC\u05D0 \u05D9\u05D3\u05D5\u05E2");
		}

		public static String getManufacturerParcel(final CarManufacturer manufacturer) {
			return map.get(manufacturer);
		}
	}
}
