package com.technion.coolie.ug.coursesAndExams;

import java.util.ArrayList;

public class PageThreeFragment extends BasePage {

	public void setGroupParents() {
		parentItems
				.add(new CourseItem("������ ����� �������", "234244", "4.0"));
		parentItems.add(new CourseItem("���� 1 �'", "134043", "5.5"));
		parentItems.add(new CourseItem("������", "394803", "1.0"));
		parentItems.add(new CourseItem("�������� ����� ��������", "236350",
				"3.0"));
		parentItems.add(new CourseItem("������ �������", "094123", "2.5"));
		parentItems.add(new CourseItem("������", "099342", "2.0"));
	}

	public void setChildData() {

		// Android
		ArrayList<String> child = new ArrayList<String>();
		child.add("Core");
		child.add("Games");
		childItems.add(child);

		// Core Java
		child = new ArrayList<String>();
		child.add("Apache");
		child.add("Applet");
		child.add("AspectJ");
		child.add("Beans");
		child.add("Crypto");
		childItems.add(child);

		// Desktop Java
		child = new ArrayList<String>();
		child.add("Accessibility");
		child.add("AWT");
		child.add("ImageIO");
		child.add("Print");
		childItems.add(child);

		// Enterprise Java
		child = new ArrayList<String>();
		child.add("EJB3");
		child.add("GWT");
		child.add("Hibernate");
		child.add("JSP");
		childItems.add(child);
	}
}
