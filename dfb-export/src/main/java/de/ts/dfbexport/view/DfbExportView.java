package de.ts.dfbexport.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DfbExportView {
	private final JPanel mainPanel;
	private final JButton uploadButton;
	private final JButton saveButton;
	private final JTextArea logArea;

	public DfbExportView() {
	        mainPanel = new JPanel();
	        mainPanel.setLayout(new BorderLayout());

	        JPanel buttonPanel = new JPanel();
	        uploadButton = new JButton("CSV Dateien hochladen");
	        saveButton = new JButton("CSV Datei speichern");
	        buttonPanel.add(uploadButton);
	        buttonPanel.add(saveButton);

	        logArea = new JTextArea();
	        logArea.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(logArea);

	        mainPanel.add(buttonPanel, BorderLayout.NORTH);
	        mainPanel.add(scrollPane, BorderLayout.CENTER);
	    }

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public JButton getUploadButton() {
		return uploadButton;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JTextArea getLogArea() {
		return logArea;
	}

}
