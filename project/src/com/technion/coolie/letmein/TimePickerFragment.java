package com.technion.coolie.letmein;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {

	private CalendarSupplier activity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (!(activity instanceof CalendarSupplier)) {
			throw new ClassCastException(activity.toString() + " must implemenet CalendarSupplier");
		}

		this.activity = (CalendarSupplier) activity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int hour = activity.getMyCalendar().getHour(), minute = activity.getMyCalendar()
				.getMinute();

		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		activity.setTime(hourOfDay, minute);
	}
}