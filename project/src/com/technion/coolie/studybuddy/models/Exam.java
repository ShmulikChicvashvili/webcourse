package com.technion.coolie.studybuddy.models;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "exams")
public class Exam implements Comparable<Exam>
{

	@DatabaseField(generatedId = true)
	private UUID		id;

	@DatabaseField
	private Date		date;

	@DatabaseField
	private ExamType	type;

	@DatabaseField(foreign = true)
	private Course		course;

	public Exam()
	{

	}

	public Exam(Date date, ExamType type, Course subject)
	{
		this.date = date;
		this.type = type;
		course = subject;
	}

	public Date getDate()
	{
		return date;
	}

	public ExamType getType()
	{
		return type;
	}

	public Course getSubject()
	{
		return course;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exam other = (Exam) obj;
		if (date == null)
		{
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (course == null)
		{
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public int compareTo(Exam another)
	{
		return date.compareTo(another.date);
	}
}
