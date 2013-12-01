package com.technion.coolie.letmein;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	private CalendarSupplier activity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (!(activity instanceof CalendarSupplier)) {
			throw new ClassCastException(activity.toString()
					+ " must implemenet CalendarSupplier");
		}

		this.activity = (CalendarSupplier) activity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int day = activity.getDay(), month = activity.getMonth(), year = activity
				.getYear();

		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		activity.setDate(cal);
	}
}