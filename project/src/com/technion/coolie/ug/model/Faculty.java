package com.technion.coolie.ug.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.technion.coolie.R;

public enum Faculty implements Serializable {
	ALL_FACULTIES("00", R.string.ug_faculty_all_faculties),

	AE("08", R.string.ug_faculty_ae),

	ARCHITECTURE("20", R.string.ug_faculty_arch),

	BIOLOGY("13", R.string.ug_faculty_biology),

	BM("33", R.string.ug_faculty_bio),

	BIOTECH("06", R.string.ug_faculty_biotech),

	CHEMENG("12", R.string.ug_faculty_chem_eng),

	CHEMISTRY("12", R.string.ug_faculty_chem),

	CEE("01", R.string.ug_faculty_indus),

	CS("23", R.string.ug_faculty_comp),

	EDU("21", R.string.ug_faculty_educ),

	EE("04", R.string.ug_faculty_elect),

	HUMANITIES("32", R.string.ug_faculty_human),

	IE("09", R.string.ug_faculty_manage),

	MATERIALS("31", R.string.ug_faculty_materials),

	MATH("10", R.string.ug_faculty_math),

	MEENG("03", R.string.ug_faculty_machine),

	MD("27", R.string.ug_faculty_medical),

	PHYS("11", R.string.ug_faculty_phys);

	private String id;
	private int repStringId;

	private Faculty(String id, int repString) {
		this.id = id;
		this.repStringId = repString;

	}

	String getId() {
		return id;
	}

	public String getName(Context context) {
		return context.getString(repStringId);
	}

	public Faculty valueByName(String name, Context context) {
		for (final Faculty facultie : values())
			if (facultie.getName(context).equals(name))
				return facultie;
		throw new NullPointerException();
	}

	public List<String> getAllFaculties(Context context) {
		final Faculty[] faculties = values();
		final List<String> list = new ArrayList<String>();
		for (final Faculty facultie : faculties)
			list.add(facultie.getName(context));
		return list;
	}

};

// AE = Aerospace Engineering (08)
// ARCHITECTURE = Architecture and Town Planning (20)
// BIOLOGY = Biology (13)
// BM = Biomedical Engineering (33)
// BIOTECH = Biotechnology and Food Engineering (06)
// CHEMENG = Chemical Engineering (12)
// CHEMISTRY = Chemistry (12)
// CEE = Civil and Environmental Engineering (01)
// CS = Computer Science (23)
// EDU = Education in Science and Technology (21)
// EE = Electrical Engineering (04)
// HUMANITIES = Humanities and Arts (32)
// IE = Industrial Engineering and Management (09)
// MATERIALS = Materials Science & Engineering (31)
// MATH = Mathematics (10)
// MEENG = Mechanical Engineering (03)
// MD = Medicine (27)
// PHYS = Physics (11)