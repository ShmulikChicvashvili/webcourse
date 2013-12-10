package com.technion.coolie.letmein;

import com.technion.coolie.R;

import android.content.Context;

public enum CarManufacturer {
	// TODO: remember that we start at UNDEFINED, in opposite to the sap server
	UNDEFINED, HONDA, MAZDA, VOLVO, MITSUBISHI, SUBARU, VOLKSWAGEN, FORD, BMW, SUZUKI, TOYOTA, NISSAN, SEAT;

	public String getManufacturerName(Context context) {
		return context.getResources().getStringArray(R.array.lmi_car_companies)[ordinal()];
	}
}
