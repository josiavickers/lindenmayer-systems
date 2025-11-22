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

/**
 * Controller class handling interactions between the view (GUI) and the model
 * (logic) of the L-System application.
 */
public class LSystemController {
	private ControlPanel controlPanel;
	private DrawingPanel drawingPanel;
	private MainFrame frame;

	public LSystemController(MainFrame frame) {
		this.frame = frame;
		this.controlPanel = frame.getControlPanel();
		this.drawingPanel = frame.getDrawingPanel();
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
			controlPanel.addPresetComboBoxListener(e -> onPresetSelected()); // e.g. on Event e, call onPresetSelected()
			setupEventHandler();
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

			// Choose default turtle commands
			commandMap = TurtleCommand.PREDEFINED_COMMANDS;

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
}
