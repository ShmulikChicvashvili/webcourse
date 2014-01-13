package com.technion.coolie.tecmind;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.util.Pair;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MineDateView {
	
	private static MineDateView mineDateViewIns = new MineDateView();
	
	public static MineDateView getInstance() {
		return mineDateViewIns;
	}
	
	public static Pair<String, String> dateToString(Date date) {

			Map<Integer, String> monthMap = new HashMap<Integer, String>();
			monthMap.put(1, "Jan");
			monthMap.put(2, "Fab");
			monthMap.put(3, "Mar");
			monthMap.put(4, "Apr");
			monthMap.put(5, "May");
			monthMap.put(6, "Jun");
			monthMap.put(7, "Jul");
			monthMap.put(8, "Aug");
			monthMap.put(9, "Sep");
			monthMap.put(10, "Oct");
			monthMap.put(11, "Nov");
			monthMap.put(12, "Dec");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int monthInt = cal.get(Calendar.MONTH);
			Integer dayInt = cal.get(Calendar.DAY_OF_MONTH);
				
		return new Pair<String, String>(monthMap.get(monthInt+1), dayInt.toString());
	}
	
	public void setDateView(Date date, TextView monthView, TextView dayView) {
			Pair<String, String> formatedDate = dateToString(MineActivity.exMiningDate); 
			monthView.setText(formatedDate.first);
			dayView.setText(formatedDate.second);
	}

}
