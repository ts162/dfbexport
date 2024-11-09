package de.ts.dfbexport.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class ExportModel {
	private final List<DfbPlayerVO> players = new ArrayList<>();

	public void loadCsvFiles(List<File> files) throws IOException {
		for (File file : files) {
			try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
				Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader()
						.parse(reader);

				Map<String, Integer> headerMap = ((CSVParser) records).getHeaderMap();
				System.out.println("Gefundene Header: " + headerMap.keySet());

				for (CSVRecord record : records) {
					String name = record.get(headerMap.keySet().stream()
							.filter(h -> h.contains("Name Künstlername") || h.contains("Name")).findFirst()
							.orElse("Name Künstlername"));
					String vorname = record.get("Vorname Rufname");
					String geburtsdatum = record.get("Geb.");
					players.add(new DfbPlayerVO(name, vorname, geburtsdatum));
				}
			} catch (IllegalArgumentException e) {
				System.err.println("Fehler beim Zugriff auf CSV-Spaltennamen: " + e.getMessage());
			}
		}

	}

	private List<DfbPlayerVO> removeDuplicates(List<DfbPlayerVO> players) {
		List<DfbPlayerVO> uniquePlayers = new ArrayList<>();

		for (DfbPlayerVO dfbPlayerVO : players) {
			boolean isDuplicate = false;
			for (DfbPlayerVO uniquePlayer : uniquePlayers) {
				if (uniquePlayer.equals(dfbPlayerVO)) {
					isDuplicate = true;
					break;
				}
			}

			if (!isDuplicate) {
				uniquePlayers.add(dfbPlayerVO);
			}
		}
		return uniquePlayers;
	}

	public void saveSortedCsv(File outputFile) throws IOException {
		List<DfbPlayerVO> uniquePlayers = this.removeDuplicates(players);
		Collections.sort(uniquePlayers);

		try (BufferedWriter writer = Files.newBufferedWriter(outputFile.toPath());
				CSVPrinter printer = new CSVPrinter(writer,
						CSVFormat.DEFAULT.withDelimiter(';').withHeader("Name", "Vorname", "Geburtsdatum"))) {

			int previousYear = -1;
			for (DfbPlayerVO player : uniquePlayers) {
				int currentYear = player.getGeburtsdatum().getYear();

				if (previousYear != -1 && previousYear != currentYear) {
					printer.println();
					printer.printRecord(" ", currentYear, " ");
					printer.println();

				}

				printer.printRecord(player.getName(), player.getVorname(),
						player.getGeburtsdatum().format(DfbPlayerVO.DATE_FORMATTER));
				previousYear = currentYear;
			}
		}
	}

	public List<DfbPlayerVO> getPlayers() {
		return players;
	}
}
