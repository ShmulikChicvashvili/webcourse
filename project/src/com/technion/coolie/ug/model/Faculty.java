package com.technion.coolie.ug.model;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum Faculty implements Serializable {
	ALL_FACULTIES("00"), AE("08"), ARCHITECTURE("20"), BIOLOGY("13"), BM("33"), BIOTECH("06"), CHEMENG("12"), CHEMISTRY("12"), CEE("01"), CS("23"), EDU("21"), EE("04"), HUMANITIES("32"), IE("09"), MATERIALS("31"), MATH("10"), MEENG("03"), MD("27"), PHYS("11");
	private String id;

	private Faculty(String id) {
		this.id = id;
	}
	String getId(){
		return id;
	}

	public List<String> getAllFaculties() {
		final Faculty[] faculties = values();
		final List<String> list = new ArrayList<String>();
		for (final Faculty facultie : faculties)
			list.add(facultie.toString());
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