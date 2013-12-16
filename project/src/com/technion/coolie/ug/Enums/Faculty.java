package com.technion.coolie.ug.Enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum Faculty implements Serializable {
	ALL_FACULTIES, AE, ARCHITECTURE, BIOLOGY, BM, BIOTECH, CHEMENG, CHEMISTRY, CEE, CS, EDU, EE, HUMANITIES, IE, MATERIALS, MATH, MEENG, MD, PHYS;
	public List<String> getAllFaculties() {
		final Faculty[] faculties = values();
		final List<String> list = new ArrayList<String>();
		for (final Faculty facultie : faculties)
			list.add(facultie.toString());
		return list;
	}

};

// AE = Aerospace Engineering
// ARCHITECTURE = Architecture and Town Planning
// BIOLOGY = Biology
// BM = Biomedical Engineering
// BIOTECH = Biotechnology and Food Engineering
// CHEMENG = Chemical Engineering
// CHEMISTRY = Chemistry
// CEE = Civil and Environmental Engineering
// CS = Computer Science
// EDU = Education in Science and Technology
// EE = Electrical Engineering
// HUMANITIES = Humanities and Arts
// IE = Industrial Engineering and Management
// MATERIALS = Materials Science & Engineering
// MATH = Mathematics
// MEENG = Mechanical Engineering
// MD = Medicine
// PHYS = Physics
