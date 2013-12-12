package com.technion.coolie.studybuddy.Model;

import java.util.Date;

public class Exam {

	private final Date date;
	private final ExamType type;
	private final Subject subject;

	public Exam(Date date, ExamType type, Subject subject) {
		this.date = date;
		this.type = type;
		this.subject = subject;
	}

	public Date getDate() {
		return date;
	}

	public ExamType getType() {
		return type;
	}

	public Subject getSubject() {
		return subject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exam other = (Exam) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
