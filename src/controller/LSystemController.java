/*
 * @author: Eliyas Tadesse
 */
package controller;

import java.util.Map;

import javax.swing.JOptionPane;

import model.LSystemParser;
import model.PredefinedLSystem;
import model.TurtleCommand;
import view.ControlPanel;
import view.DrawingPanel;

public class LSystemController {
	private final ControlPanel controlPanel;
	private final DrawingPanel drawingPanel;

	public LSystemController(ControlPanel controlPanel, DrawingPanel drawingPanel) {
		this.controlPanel = controlPanel;
		this.drawingPanel = drawingPanel;
		initializeController();
	}

	private void initializeController() {
		try {
			if (PredefinedLSystem.predefinedLSystems() == null) {
				showErrorDialog("No predefined L-Systems available.");
				throw new IllegalStateException("No predefined L-Systems available.");
			}
			// Set default preset and trigger drawing
			controlPanel.setSelectedPreset(PredefinedLSystem.predefinedLSystems()[0]);
			onPresetSelected();

			controlPanel.addPresetComboBoxListener(e -> onPresetSelected());
			controlPanel.addButtonListener(e -> onGenerateClicked());
			setupEventHandler();
		} catch (Exception e) {
			showErrorDialog("Error initializing controller: " + e.getMessage());
		}
	}

	private void setupEventHandler() {
		controlPanel.getIterationSpinner().addChangeListener(e -> onGenerateClicked());
		controlPanel.getAngleSpinner().addChangeListener(e -> onGenerateClicked());
		controlPanel.getStepSpinner().addChangeListener(e -> onGenerateClicked());
	}

	private void onPresetSelected() {
		try {
			PredefinedLSystem preset = controlPanel.getSelectedPreset();

			// Update UI fields with preset values
			setInputsWithPresetValue(preset.getAxiom(), preset.getRules(), preset.getAngle(), preset.getStep(),
					preset.getIterations());

			generateAndDraw(preset);
		} catch (Exception e) {
			showErrorDialog("Error selecting preset: " + e.getMessage());
		}

	}

	private void onGenerateClicked() {
		try {
			generateAndDraw(null);
		} catch (Exception e) {
			showErrorDialog("Error generating L-System: " + e.getMessage());
		}
	}

	private void generateAndDraw(PredefinedLSystem preset) {
		try {

			String axiom = preset != null ? preset.getAxiom() : controlPanel.getAxiom();
			String rules = preset != null ? preset.getRules() : controlPanel.getRules();
			double angle = preset != null ? preset.getAngle() : controlPanel.getAngle();
			int step = preset != null ? preset.getStep() : controlPanel.getStep();
			int iterations = preset != null ? preset.getIterations() : controlPanel.getIteration();

			// L-system parser
			LSystemParser lSystem = new LSystemParser(axiom, rules, iterations);
			Map<Character, TurtleCommand> commandMap;

			if (preset != null) {
				controlPanel.getCheckbox().setSelected(true);
			}

			if (controlPanel.getCheckbox().isSelected()) {
				commandMap = TurtleCommand.PREDEFINED_COMMANDS;
			} else {
				commandMap = lSystem.parseCommands(controlPanel.getCommands());
			}

			String lSystemString = lSystem.generateLSystemString();

			// Update drawing panel
			updateDrawingPanel(angle, step, commandMap, lSystemString);
		} catch (Exception e) {
			showErrorDialog("Error generating L-System: " + e.getMessage());
		}
	}

	private void updateDrawingPanel(double angle, int step, Map<Character, TurtleCommand> commandMap, String lSystemString) {
		drawingPanel.setAngle(angle);
		drawingPanel.setStep(step);
		drawingPanel.setCommandMap(commandMap);
		drawingPanel.setLSystemString(lSystemString);
	}

	private void setInputsWithPresetValue(String axiom, String rules, double angle, int step, int iterations) {
		controlPanel.setAxiom(axiom);
		controlPanel.setRules(rules);
		controlPanel.setAngle(angle);
		controlPanel.setStep(step);
		controlPanel.setIterations(iterations);
	}

	private void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
