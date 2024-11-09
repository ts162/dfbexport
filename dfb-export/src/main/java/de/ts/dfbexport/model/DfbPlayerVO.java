package de.ts.dfbexport.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DfbPlayerVO implements Comparable<DfbPlayerVO> {

	private String name;
	private String vorname;
	private LocalDate geburtsdatum;

	static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public DfbPlayerVO(String name, String vorname, String geburtsdatum) {
		this.name = name;
		this.vorname = vorname;
		this.geburtsdatum = LocalDate.parse(geburtsdatum, DATE_FORMATTER);
	}

	public String getName() {
		return name;
	}

	public String getVorname() {
		return vorname;
	}

	public LocalDate getGeburtsdatum() {
		return geburtsdatum;
	}

	@Override
	public int compareTo(DfbPlayerVO other) {
		return this.geburtsdatum.compareTo(other.geburtsdatum);
	}

	public boolean equals(DfbPlayerVO other) {
		return other.getName().equals(this.getName()) && other.getGeburtsdatum().equals(this.getGeburtsdatum())
				&& other.getVorname().equals(this.getVorname());
	}
}
