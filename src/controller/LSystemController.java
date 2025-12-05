/*
 * @author: Eliyas Tadesse
 */
package controller;

import java.awt.Color;
import java.util.Map;

import javax.swing.JOptionPane;

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
	 * Adds listeners for iteration depth, turning angle, thickness, colour and step length spinners.
	 * Each change will trigger regeneration of the L-System drawing.
	 */
	private void setupEventHandler() {
		controlPanel.getIterationSpinner().addChangeListener(e -> onGenerateClicked());
		controlPanel.getAngleSpinner().addChangeListener(e -> onGenerateClicked());
		controlPanel.getAngleFactorSpinner().addChangeListener(e -> onGenerateClicked());
		controlPanel.getStepSpinner().addChangeListener(e -> onGenerateClicked());
		controlPanel.getStepFactorSpinner().addChangeListener(e -> onGenerateClicked());
		controlPanel.getThicknessSpinner().addChangeListener(e -> onGenerateClicked());
		controlPanel.getThicknessFactorSpinner().addChangeListener(e -> onGenerateClicked());
		controlPanel.getColourFactorSpinner().addChangeListener(e -> onGenerateClicked());
		controlPanel.getColourComboBox().addActionListener(e -> onGenerateClicked());
	}

	/**
	 * Handles logic when a predefined L-system is selected. Populates input fields
	 * and triggers drawing.
	 */
	private void onPresetSelected() {
		try {
			PredefinedLSystem preset = controlPanel.getSelectedPreset();

			setInputsWithPresetValue(preset.getAxiom(), preset.getRules(), preset.getAngle(), preset.getStep(),
					preset.getIterations(), preset.getThickness(), preset.getColour());

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
			int thickness = preset != null ? preset.getThickness() : controlPanel.getThickness();
			Color colour = preset != null ? preset.getColour() : controlPanel.getColour();
			int iterations = preset != null ? preset.getIterations() : controlPanel.getIteration();

			// Generate L-System string
			LSystemParser lSystem = new LSystemParser(axiom, rules, iterations);

			Map<Character, TurtleCommand> commandMap;

			// Choose default turtle commands
			commandMap = TurtleCommand.PREDEFINED_COMMANDS;

			String lSystemString = lSystem.generateLSystemString();
			
			System.out.println(lSystemString);			
	        // Multiply angle by exponentially increasing factor per iteration:
	        // iterations = 0 → baseAngle
	        // iterations = 1 → baseAngle * factor
	        // iterations = 2 → baseAngle * factor^2, etc.
			double angleFactor = controlPanel.getAngleFactor();
			double effectiveAngle = angle * Math.pow(angleFactor, Math.max(0, iterations - 1));
			//double effectiveAngle = angle + iterations * angleFactor; // linearly (slower growth)
			// "Toggling" growth
			if (iterations % 2 == 0) { // toggle on even iterations
				effectiveAngle *= -1;
			}
			
			double stepFactor = controlPanel.getStepFactor();
			double effectiveStep = step * Math.pow(stepFactor, Math.max(0, iterations - 1));
			//double effectiveStep = step + iterations * stepFactor;
			
			double thicknessFactor = controlPanel.getThicknessFactor();
			double effectiveThickness = thickness * Math.pow(thicknessFactor, Math.max(0, iterations - 1));
			//double effectiveThickness = thickness + iterations * thicknessFactor;
			
			// Colour
			Color effectiveColour = this.nextShade(colour, (float)controlPanel.getColourFactor());

			updateDrawingPanel(effectiveAngle, effectiveStep, effectiveThickness, effectiveColour, commandMap, lSystemString);
			//updateDrawingPanel(angle, step, thickness, colour, commandMap, lSystemString);

		} catch (Exception e) {
			showErrorDialog("Error generating L-System: " + e.getMessage());
		}
	}

	/**
	 * Updates the drawing panel with new configuration and L-System string.
	 */
	private void updateDrawingPanel(double angle, double step, double thickness, Color colour, Map<Character, TurtleCommand> commandMap,
			String lSystemString) {
		drawingPanel.setAngle(angle);
		drawingPanel.setStep(step);
		drawingPanel.setThickness(thickness);
		drawingPanel.setColour(colour);
		drawingPanel.setCommandMap(commandMap);
		drawingPanel.setLSystemString(lSystemString);
	}

	/**
	 * Sets input fields in the control panel using the selected predefined
	 * L-system.
	 */
	private void setInputsWithPresetValue(String axiom, String rules, double angle, int step, int iterations, int thickness, Color colour) {
		controlPanel.setAxiom(axiom);
		controlPanel.setRules(rules);
		controlPanel.setAngle(angle);
		controlPanel.setStep(step);
		controlPanel.setThickness(thickness);
		controlPanel.setColour(colour);
		controlPanel.setIterations(iterations);
	}

	/**
	 * Displays a modal error message to the user.
	 */
	private void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Helper method to get the next colour with gradient change controlled by factor
	 * @param current
	 * @param factor
	 * @return
	 */
	private Color nextShade(Color current, float factor) {
        // Convert RGB to HSB
        float[] hsb = Color.RGBtoHSB(
            current.getRed(),
            current.getGreen(),
            current.getBlue(),
            null
        );

        // Increment hue by step (wrap around at 1.0)
        float newHue = (hsb[0] + factor) % 1.0f;

        // Create new color with same saturation & brightness
        return Color.getHSBColor(newHue, hsb[1], hsb[2]);
    }
}
