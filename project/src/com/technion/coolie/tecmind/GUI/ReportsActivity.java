package com.technion.coolie.tecmind.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

public class ReportsActivity extends CoolieActivity {
	Button spam; 
	Button staff;
	Button student;
	Button changeInClass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.techmind_activity_reports);
		spam = (Button)findViewById(R.id.report_spam);
		staff = (Button)findViewById(R.id.report_staff);
		student = (Button)findViewById(R.id.report_student);
		changeInClass = (Button)findViewById(R.id.report_class);
	}
	
	public void reportSpam(View view) {
	    Intent intent = new Intent(ReportsActivity.this, ReportSpamActivity.class);
	    startActivity(intent);
	}
	public void reportStaff(View view) {
	    Intent intent = new Intent(ReportsActivity.this, ReportStaffActivity.class);
	    startActivity(intent);
	}
	public void reportStudent(View view) {
	    Intent intent = new Intent(ReportsActivity.this, ReportStudentActivity.class);
	    startActivity(intent);
	}
	public void reportClass(View view) {
	    Intent intent = new Intent(ReportsActivity.this, ReportClassActivity.class);
	    startActivity(intent);
	}

}
