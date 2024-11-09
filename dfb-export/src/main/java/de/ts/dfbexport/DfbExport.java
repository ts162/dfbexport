package de.ts.dfbexport;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.ts.dfbexport.controller.DfbExportController;
import de.ts.dfbexport.model.ExportModel;
import de.ts.dfbexport.view.DfbExportView;

public class DfbExport {
	public static void main(String[] args) {
        SwingUtilities.invokeLater(DfbExport::createAndShowGui);
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("CSV Uploader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Create the View
        DfbExportView view = new DfbExportView();
        frame.add(view.getMainPanel(), BorderLayout.CENTER);

        // Create the Model and Controller
        ExportModel model = new ExportModel();
        new DfbExportController(model, view);

        frame.setVisible(true);
    }

}
