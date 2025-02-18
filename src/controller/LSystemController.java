/*
 * @author: Eliyas Tadesse
 */
package controller;

import java.util.Map;

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

		// Set default preset and trigger drawing
		controlPanel.setSelectedPreset(PredefinedLSystem.predefinedLSystems()[0]);
		onPresetSelected();

		controlPanel.addPresetComboBoxListener(e -> onPresetSelected());
		controlPanel.addButtonListener(e -> onGenerateClicked());
		setupEventHandler();
	}

	private void setupEventHandler() {
		// adding action listener to Iteration Spinner
		controlPanel.getIterationSpinner().addChangeListener(e -> onGenerateClicked());

		// adding action listener to Angel Spinner
		controlPanel.getAngleSpinner().addChangeListener(e -> onGenerateClicked());

		// adding action listener to Step Spinner
		controlPanel.getStepSpinner().addChangeListener(e -> onGenerateClicked());

	}

	private void onPresetSelected() {
		PredefinedLSystem preset = null;
		preset = controlPanel.getSelectedPreset();
		if (preset == null)
			return;

		// Update UI fields with preset values
		setPreset(preset.getAxiom(), preset.getRules(), preset.getAngle(), preset.getStep(), preset.getIterations());

		// Trigger immediate visualization
		generateAndDraw(preset);
	}

	private void onGenerateClicked() {
		generateAndDraw(null); // Use manual input values
	}

	private void generateAndDraw(PredefinedLSystem preset) {
		String axiom = preset != null ? preset.getAxiom() : controlPanel.getAxiom();
		String rules = preset != null ? preset.getRules() : controlPanel.getRules();
		double angle = preset != null ? preset.getAngle() : controlPanel.getAngle();
		int step = preset != null ? preset.getStep() : controlPanel.getStep();
		int iterations = preset != null ? preset.getIterations() : controlPanel.getIteration();

		// Lsystem parser
		LSystemParser lSystem = new LSystemParser(axiom, rules, iterations);
		Map<Character, TurtleCommand> commandMap;

		if (preset != null) {
			controlPanel.getCheckbox().setSelected(true);
		}

		if (controlPanel.getCheckbox().isSelected()) {
			commandMap = TurtleCommand.PREDEFINED_COMMANDS;
		} else {
			// Parse custom commands from the input field
			commandMap = lSystem.parseCommands(controlPanel.getCommands());
		}

		String result = lSystem.generateLSystemString();

		// Update drawing panel
		drawingPanel.setAngle(angle);
		drawingPanel.setStep(step);
		drawingPanel.setCommandMap(commandMap);
		drawingPanel.setCommands(result);
	}

	private void setPreset(String axiom, String rules, double angle, int step, int iterations) {
		controlPanel.setAxiom(axiom);
		controlPanel.setRules(rules);
		controlPanel.setAngle(angle);
		controlPanel.setStep(step);
		controlPanel.setIterations(iterations);
	}
}
