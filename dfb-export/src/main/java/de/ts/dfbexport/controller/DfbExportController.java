package de.ts.dfbexport.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;

import de.ts.dfbexport.model.ExportModel;
import de.ts.dfbexport.view.DfbExportView;

public class DfbExportController {
	private final ExportModel model;
    private final DfbExportView view;

    public DfbExportController(ExportModel model, DfbExportView view) {
        this.model = model;
        this.view = view;

        view.getUploadButton().addActionListener(e -> uploadCsvFiles());
        view.getSaveButton().addActionListener(e -> saveSortedCsv());
    }

    private void uploadCsvFiles() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                model.loadCsvFiles(List.of(fileChooser.getSelectedFiles()));
                view.getLogArea().append("CSV Dateien erfolgreich geladen.\n");
            } catch (IOException ex) {
                view.getLogArea().append("Fehler beim Laden der Dateien: " + ex.getMessage() + "\n");
            }
        }
    }

    private void saveSortedCsv() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                model.saveSortedCsv(file);
                view.getLogArea().append("CSV Datei erfolgreich gespeichert: " + file.getPath() + "\n");
            } catch (IOException ex) {
                view.getLogArea().append("Fehler beim Speichern der Datei: " + ex.getMessage() + "\n");
            }
        }
    }

}
