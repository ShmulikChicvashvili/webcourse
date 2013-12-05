package com.technion.coolie.ug.gradessheet;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

public class GradesSheetActivity extends CoolieActivity implements
		OnItemClickListener {

	ArrayList<Item> items = new ArrayList<Item>();
	ListView listview = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ug_activity_grades_sheet);

		listview = (ListView) findViewById(R.id.listView_main);

		items.add(new GradesSectionItem("����  2010/2011"));
		items.add(new GradesEntryItem("���� ������", "3.5", "80"));
		items.add(new GradesEntryItem("�����", "1.0", "94"));
		items.add(new GradesEntryItem("���� ��������", "3.5", "49"));
		items.add(new GradesEntryItem("������", "3.0", "100"));

		items.add(new GradesSectionItem("���� 2011"));
		items.add(new GradesEntryItem("���� ����������", "3.5", "84"));
		items.add(new GradesEntryItem("���� ����� �����", "3.0", "98"));
		items.add(new GradesEntryItem("�������", "4.0", "77"));
		items.add(new GradesEntryItem("������ �����", "4.5", "63"));

		items.add(new GradesSectionItem("���� 2011/2012"));
		items.add(new GradesEntryItem("������� ������ �����", "3.0", "90"));
		items.add(new GradesEntryItem("������", "3.5", "99"));
		items.add(new GradesEntryItem("����� ��������", "2.5", "92"));

		items.add(new GradesSectionItem("���� 2012"));
		items.add(new GradesEntryItem("������� ����", "3.0", "65"));
		items.add(new GradesEntryItem("����", "5.5", "78"));
		items.add(new GradesEntryItem("����� ��������", "2.5", "92"));

		EntryAdapter adapter = new EntryAdapter(this, items);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

		GradesEntryItem item = (GradesEntryItem) items.get(position);
		Toast.makeText(this, "You clicked " + item.courseName,
				Toast.LENGTH_SHORT).show();
	}

}
