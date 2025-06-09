/*
 * @author: Eliyas Tadesse
 */
package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.LSystemParser;
import model.PredefinedLSystem;
import model.TurtleCommand;
import view.ControlPanel;
import view.DrawingPanel;
import view.MainFrame;
import view.MenuBar;

/**
 * Controller class handling interactions between the view (GUI) and the model
 * (logic) of the L-System application.
 */
public class LSystemController {
	private ControlPanel controlPanel;
	private DrawingPanel drawingPanel;
	private MenuBar menuBar;
	private MainFrame frame;

	public LSystemController(MainFrame frame) {
		this.frame = frame;
		this.controlPanel = frame.getControlPanel();
		this.drawingPanel = frame.getDrawingPanel();
		this.menuBar = frame.getMenu();
		initializeController();
	}

	/**
	 * Initializes the controller by loading predefined L-systems, setting default
	 * values, and wiring GUI event listeners.
	 */
	private void initializeController() {
		try {
			if (PredefinedLSystem.predefinedLSystems() == null) {
				showErrorDialog("No predefined L-Systems available.");
				throw new IllegalStateException("No predefined L-Systems available.");
			}
			// Set the default L-System preset to the first available option
			controlPanel.setSelectedPreset(PredefinedLSystem.predefinedLSystems()[0]);
			onPresetSelected();

			// Set up GUI event listeners
			controlPanel.addPresetComboBoxListener(e -> onPresetSelected());
			controlPanel.addButtonListener(e -> onGenerateClicked());
			setupEventHandler();
			setMenuListener();
		} catch (Exception e) {
			showErrorDialog("Error initializing controller: " + e.getMessage());
		}
	}

	/**
	 * Adds listeners for iteration depth, turning angle, and step length spinners.
	 * Each change will trigger regeneration of the L-System drawing.
	 */
	private void setupEventHandler() {
		controlPanel.getIterationSpinner().addChangeListener(e -> onGenerateClicked());
		controlPanel.getAngleSpinner().addChangeListener(e -> onGenerateClicked());
		controlPanel.getStepSpinner().addChangeListener(e -> onGenerateClicked());
	}

	/**
	 * Connects menu item actions to their respective event handlers.
	 */
	private void setMenuListener() {
		menuBar.getSaveMenuItem().addActionListener(e -> onSave());
		menuBar.getExitMenuItem().addActionListener(e -> frame.dispose());
		menuBar.getUserGuideMenuItem().addActionListener(e -> openUserGuide());
		menuBar.getAboutMenuItem().addActionListener(e -> showAboutDialog());
	}

	/**
	 * Handles logic when a predefined L-system is selected. Populates input fields
	 * and triggers drawing.
	 */
	private void onPresetSelected() {
		try {
			PredefinedLSystem preset = controlPanel.getSelectedPreset();

			setInputsWithPresetValue(preset.getAxiom(), preset.getRules(), preset.getAngle(), preset.getStep(),
					preset.getIterations());

			generateAndDraw(preset);
		} catch (Exception e) {
			showErrorDialog("Error selecting preset: " + e.getMessage());
		}

	}

	/**
	 * Handles the Generate button click and spinner changes. Uses manual inputs
	 * rather than a predefined L-systems.
	 */
	private void onGenerateClicked() {
		try {
			generateAndDraw(null);
		} catch (Exception e) {
			showErrorDialog("Error generating L-System: " + e.getMessage());
		}
	}

	/**
	 * Core method to parse the L-System string and update the drawing panel.
	 * Applies validation to ensure rendering performance and safety.
	 */
	private void generateAndDraw(PredefinedLSystem preset) {
		try {
			// Use values from either the preset or user inputs
			String axiom = preset != null ? preset.getAxiom() : controlPanel.getAxiom();
			String rules = preset != null ? preset.getRules() : controlPanel.getRules();
			double angle = preset != null ? preset.getAngle() : controlPanel.getAngle();
			int step = preset != null ? preset.getStep() : controlPanel.getStep();
			int iterations = preset != null ? preset.getIterations() : controlPanel.getIteration();

			// Generate L-System string
			LSystemParser lSystem = new LSystemParser(axiom, rules, iterations);

			Map<Character, TurtleCommand> commandMap;

			// Choose between default or custom turtle commands
			if (!controlPanel.getCheckbox().isSelected()) {
				commandMap = TurtleCommand.PREDEFINED_COMMANDS;
			} else {
				commandMap = lSystem.parseCommands(controlPanel.getCommands());
			}

			String lSystemString = lSystem.generateLSystemString();

			updateDrawingPanel(angle, step, commandMap, lSystemString);

		} catch (Exception e) {
			showErrorDialog("Error generating L-System: " + e.getMessage());
		}
	}

	/**
	 * Updates the drawing panel with new configuration and L-System string.
	 */
	private void updateDrawingPanel(double angle, int step, Map<Character, TurtleCommand> commandMap,
			String lSystemString) {
		drawingPanel.setAngle(angle);
		drawingPanel.setStep(step);
		drawingPanel.setCommandMap(commandMap);
		drawingPanel.setLSystemString(lSystemString);
	}

	/**
	 * Sets input fields in the control panel using the selected predefined
	 * L-system.
	 */
	private void setInputsWithPresetValue(String axiom, String rules, double angle, int step, int iterations) {
		controlPanel.setAxiom(axiom);
		controlPanel.setRules(rules);
		controlPanel.setAngle(angle);
		controlPanel.setStep(step);
		controlPanel.setIterations(iterations);
	}

	/**
	 * Displays a modal error message to the user.
	 */
	private void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Handles the save functionality using a file chooser. Supports PNG format only
	 * and handles overwrite confirmations.
	 */
	private void onSave() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save Drawing");

		FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG Image (*.png)", "png");
		fileChooser.setFileFilter(pngFilter);
		fileChooser.setSelectedFile(new File("drawing.png"));
		while (true) {
			int result = fileChooser.showSaveDialog(null);
			if (result != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File file = fileChooser.getSelectedFile();

			// Ensure the file ends with .png
			if (!file.getName().toLowerCase().endsWith(".png")) {
				file = new File(file.getAbsolutePath() + ".png");
			}

			// Prompt overwrite confirmation
			if (file.exists()) {
				int confirm = JOptionPane.showConfirmDialog(null,
						"File \"" + file.getName() + "\" already exists.\nDo you want to overwrite it?",
						"Confirm Overwrite", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.NO_OPTION) {
					continue;
				} else if (confirm != JOptionPane.YES_OPTION) {
					return;
				}
			}
			// Save image to selected file
			try {
				drawingPanel.saveImage(file, "png");
				JOptionPane.showMessageDialog(null, "Image saved successfully.", "Success",
						JOptionPane.INFORMATION_MESSAGE);

			} catch (Exception ex) {
				showErrorDialog("Error saving image: " + ex.getMessage());
			}

			break;
		}
	}

	/**
	 * Opens the user guide PDF bundled in the application resources. Uses the
	 * system default PDF viewer to display the file.
	 */
	private void openUserGuide() {
		try (InputStream pdfStream = MenuBar.class.getResourceAsStream("/resources/User Guide.pdf")) {
			if (pdfStream == null) {
				showErrorDialog("User guide not found in application resources");
				return;
			}

			File tempFile = File.createTempFile("UserGuide", ".pdf");
			tempFile.deleteOnExit();

			Files.copy(pdfStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(tempFile);
			} else {
				showErrorDialog("Cannot open PDF - no desktop support");
			}
		} catch (IOException ex) {
			showErrorDialog("Failed to open user guide: " + ex.getMessage());
		}
	}

	/**
	 * Displays application version and author information.
	 */
	private void showAboutDialog() {
		JOptionPane.showMessageDialog(null, "L-Systems Generator and Visualizer v1.0\n(c) 2025 Eliyas Tadesse", "About",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
