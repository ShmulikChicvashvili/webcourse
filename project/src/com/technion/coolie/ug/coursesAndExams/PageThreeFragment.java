package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

public class PageThreeFragment extends BasePage {

	public void setGroupParents() {
		ArrayList<ExamItem> child = new ArrayList<ExamItem>();
		child.add(new ExamItem("���� �'", "1.1.2014"));
		child.add(new ExamItem("���� �'", "5.3.2014"));
		parentItems
				.add(new CourseItem("������ ����� �������", "234244", "4.0",child));
		parentItems.add(new CourseItem("���� 1 �'", "134043", "5.5",child));
		parentItems.add(new CourseItem("������", "394803", "1.0",child));
		parentItems.add(new CourseItem("�������� ����� ��������", "236350",
				"3.0",child));
		parentItems.add(new CourseItem("������ �������", "094123", "2.5",child));
		parentItems.add(new CourseItem("������", "099342", "2.0",child));
	}
}
