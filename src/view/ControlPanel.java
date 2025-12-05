/*
 * @author: Eliyas Tadesse
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import model.PredefinedLSystem;
import net.miginfocom.swing.MigLayout;

/**
 * ControlPanel is the user interface panel that provides input controls 
 * for defining and generating L-Systems. This includes fields for axiom, 
 * production rules, drawing commands, and numerical parameters such as 
 * turning angle, step length, and iteration depth.
 * 
 * This panel also includes a preset drop-down to quickly load commonly 
 * known L-Systems and options for custom command mappings.
 * 
 * The layout is managed using MigLayout for flexibility and clarity.
 */
public class ControlPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField axiomField;
	private JSpinner angleSpinner;
	private JSpinner angleFactorSpinner;
	private JSpinner stepSpinner;
	private JSpinner stepFactorSpinner;
	private JSpinner iterationSpinner;
	private JSpinner thicknessSpinner;
	private JSpinner thicknessFactorSpinner;
	private JSpinner colourFactorSpinner;
	private JComboBox<PredefinedLSystem> presetComboBox;
	private JComboBox<Color> colourComboBox;
	private JTextArea rules;

	
	/**
	 * Constructs the ControlPanel with all required input fields
	 */
	@SuppressWarnings("serial")
	public ControlPanel() {
		
		Dimension screenSize = getToolkit().getScreenSize();
		setLayout(new MigLayout("insets 0 10 0 30"));
		setBackground(Color.getHSBColor(0.55f, 0.75f, 0.75f));
		setMaximumSize(new Dimension(50,screenSize.height));
		
		// Add drop down for presets
		presetComboBox = new JComboBox<>(PredefinedLSystem.predefinedLSystems());
		presetComboBox.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value instanceof PredefinedLSystem predefinedLSystem) {
					setText(predefinedLSystem.getName());
					setHorizontalAlignment(CENTER);
				} else {
					setText("");
				}
				setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
				setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
				return this;
			}
		});
		presetComboBox.setFont(new Font("Arial Black", Font.BOLD, 20));
		JLabel comboBoxLabel = new JLabel("Common L-systems");
		comboBoxLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(comboBoxLabel, "wrap, align left");
		add(presetComboBox, "wrap,gapy 0 10, align center");

		// Axiom input
		JLabel axiomLabel = new JLabel("Axiom");
		axiomLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(axiomLabel, "wrap,align left");

		axiomField = new JTextField(13); // 12 is number of columns
		axiomField.setFont(new Font("Arial Black", Font.BOLD, 20));
		axiomField.setToolTipText("Enter Axiom");
		axiomField.setMargin(new Insets(5, 10, 5, 10));
		axiomField.setHorizontalAlignment(JTextField.CENTER);
		add(axiomField, "wrap,gapy 0 10, align center");

		// Production Rules input
		JLabel rulesLabel = new JLabel("Production Rules");
		rulesLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(rulesLabel, "wrap, align left");
		rules = new JTextArea(2,13);
		rules.setFont(new Font("Arial Black", Font.BOLD, 20));
		rules.setToolTipText("Enter Production Rules");
		rules.setAlignmentY(CENTER_ALIGNMENT);
		rules.setLineWrap(true);
		rules.setMargin(new Insets(5, 10, 5, 10));
		add(rules, "wrap,gapy 0 10, align center");

		// Turning Angle spinner
		JLabel angleLabel = new JLabel("Turning Angle:     ");
		angleLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(angleLabel, "split 2, alignx left");
		angleSpinner = new JSpinner();
		angleSpinner.setModel(new SpinnerNumberModel(45.0, 0.0, 360.0, 0.5));
		angleSpinner.setToolTipText("Enter Rotation Angle");
		angleSpinner.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(angleSpinner, "wrap,gapy 0 10, alignx right");
		
		// Turning Angle Factor spinner
		JLabel angleFactorLabel = new JLabel("Angle Factor:     ");
		angleFactorLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(angleFactorLabel, "split 2, alignx left");
		angleFactorSpinner = new JSpinner();
		angleFactorSpinner.setModel(new SpinnerNumberModel(1.0, 0.1, 2.0, 0.1));
		angleFactorSpinner.setToolTipText("Enter Factor");
		angleFactorSpinner.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(angleFactorSpinner, "wrap,gapy 0 10, alignx right");

		// Step Length spinner
		JLabel stepLabel = new JLabel(" Step Length:       ");
		stepLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(stepLabel, "split 2, alignx left");
		stepSpinner = new JSpinner();
		stepSpinner.setModel(new SpinnerNumberModel(5, 1, 100, 1));
		stepSpinner.setToolTipText("Enter Step Length");
		stepSpinner.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(stepSpinner, "wrap,gapy 0 10, alignx right");
		
		// Step Length Factor spinner
		JLabel stepFactorLabel = new JLabel("Step Factor:     ");
		stepFactorLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(stepFactorLabel, "split 2, alignx left");
		stepFactorSpinner = new JSpinner();
		stepFactorSpinner.setModel(new SpinnerNumberModel(1.0, 0.1, 2.0, 0.1));
		stepFactorSpinner.setToolTipText("Enter Factor");
		stepFactorSpinner.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(stepFactorSpinner, "wrap,gapy 0 10, alignx right");
		
		// Iteration Depth spinner
		JLabel iterationLabel = new JLabel(" Iteration Depth: ");
		iterationLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(iterationLabel, "split 2, alignx left");
		iterationSpinner = new JSpinner();
		iterationSpinner.setModel(new SpinnerNumberModel(5, 1, 20, 1));
		iterationSpinner.setToolTipText("Enter Iteration Depth");
		iterationSpinner.setFont(new Font("Arial Black", Font.BOLD, 20));
		iterationSpinner.setSize(8,4);
		add(iterationSpinner, "w 83, wrap,gapy 0 10, alignx right");
		
		// Thickness spinner
		JLabel thicknessLabel = new JLabel(" Stroke Thickness: ");
		thicknessLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(thicknessLabel, "split 2, alignx left");
		thicknessSpinner = new JSpinner();
		thicknessSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		thicknessSpinner.setToolTipText("Enter Stroke Thickness");
		thicknessSpinner.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(thicknessSpinner, "w 83, wrap,gapy 0 10, alignx right");
		
		// Thickness Factor spinner
		JLabel thicknessFactorLabel = new JLabel("Thickness Factor:     ");
		thicknessFactorLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(thicknessFactorLabel, "split 2, alignx left");
		thicknessFactorSpinner = new JSpinner();
		thicknessFactorSpinner.setModel(new SpinnerNumberModel(1.0, 0.1, 2.0, 0.1));
		thicknessFactorSpinner.setToolTipText("Enter Factor");
		thicknessFactorSpinner.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(thicknessFactorSpinner, "wrap,gapy 0 10, alignx right");
		
		// Add drop down for colour
		colourComboBox = new JComboBox<>(new Color[] {
			    Color.RED,
			    Color.BLUE,
			    Color.GREEN,
			    Color.YELLOW,
			    Color.BLACK,
			    Color.WHITE,
			    Color.ORANGE,
			    Color.GRAY
		});
		
		colourComboBox.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
		        if (value instanceof Color color) {
		            setText(getColourName(color));
		        } else {
		            setText("");
		        }
				setHorizontalAlignment(CENTER);
				setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
				setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
				return this;
			}
			
		    private String getColourName(Color color) {
		        if (Color.RED.equals(color))     return "Red";
		        if (Color.BLUE.equals(color))    return "Blue";
		        if (Color.GREEN.equals(color))   return "Green";
		        if (Color.YELLOW.equals(color))  return "Yellow";
		        if (Color.BLACK.equals(color))   return "Black";
		        if (Color.WHITE.equals(color))   return "White";
		        if (Color.ORANGE.equals(color))  return "Orange";
		        if (Color.GRAY.equals(color))    return "Gray";

		        // For unexpected colours
		        return String.format("RGB(%d,%d,%d)", color.getRed(), color.getGreen(), color.getBlue());
		    }
		});
		colourComboBox.setFont(new Font("Arial Black", Font.BOLD, 20));
		JLabel colourBoxLabel = new JLabel("Colour:");
		colourBoxLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(colourBoxLabel, "split 2, alignx left");
		add(colourComboBox, "wrap,gapy 0 10, align right");
		
		// Thickness Factor spinner
		JLabel colourFactorLabel = new JLabel("Colour Factor:     ");
		colourFactorLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(colourFactorLabel, "split 2, alignx left");
		colourFactorSpinner = new JSpinner();
		colourFactorSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.01));
		colourFactorSpinner.setToolTipText("Enter Factor");
		colourFactorSpinner.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(colourFactorSpinner, "w 83, wrap,gapy 0 10, alignx right");
	}

	// Getters
	public String getAxiom() {
		return axiomField.getText();
	}

	public String getRules() {
		return rules.getText();
	}

	public JSpinner getAngleSpinner() {
		return angleSpinner;
	}

	public double getAngle() {
		return (double) angleSpinner.getValue();
	}
	
	public JSpinner getAngleFactorSpinner() {
		return angleFactorSpinner;
	}

	public double getAngleFactor() {
		return (double) angleFactorSpinner.getValue();
	}

	public JSpinner getStepSpinner() {
		return stepSpinner;
	}

	public int getStep() {
		return (int) stepSpinner.getValue();
	}
	
	public JSpinner getStepFactorSpinner() {
		return stepFactorSpinner;
	}

	public double getStepFactor() {
		return (double) stepFactorSpinner.getValue();
	}

	public JSpinner getIterationSpinner() {
		return iterationSpinner;
	}
	
	public JSpinner getThicknessSpinner() {
		return thicknessSpinner;
	}
	
	public int getThickness() {
		return (int) thicknessSpinner.getValue();
	}
	
	public JSpinner getThicknessFactorSpinner() {
		return thicknessFactorSpinner;
	}

	public double getThicknessFactor() {
		return (double) thicknessFactorSpinner.getValue();
	}
	
	public JSpinner getColourFactorSpinner() {
		return colourFactorSpinner;
	}

	public double getColourFactor() {
		return (double) colourFactorSpinner.getValue();
	}

	public int getIteration() {
		return (int) iterationSpinner.getValue();
	}

	public PredefinedLSystem getSelectedPreset() {
		return (PredefinedLSystem) presetComboBox.getSelectedItem();
	}
	
	public JComboBox<Color> getColourComboBox() {
		return colourComboBox;
	}
	
	public Color getColour() {
		return (Color) colourComboBox.getSelectedItem();
	}

	// Setters
	public void setAxiom(String str) {
		axiomField.setText(str);
	}

	public void setRules(String str) {
		rules.setText(str);
	}

	public void setAngle(double deg) {
		angleSpinner.setValue(deg);
	}
	
	public void setAngleFactor(double deg) {
		angleFactorSpinner.setValue(deg);
	}

	public void setIterations(int iterations) {
		iterationSpinner.setValue(iterations);
	}
	
	public void setThickness(int thickness) {
		thicknessSpinner.setValue(thickness);
	}
	
	public void setThicknessFactor(double deg) {
		thicknessFactorSpinner.setValue(deg);
	}

	public void setStep(int length) {
		stepSpinner.setValue(length);
	}
	
	public void setStepFactor(double deg) {
		stepFactorSpinner.setValue(deg);
	}
	
	public void setColour(Color colour) {
		colourComboBox.setSelectedItem(colour);
	}

	public void setSelectedPreset(PredefinedLSystem preset) {
		presetComboBox.setSelectedItem(preset);
	}

	// Add listeners
	public void addPresetComboBoxListener(ActionListener listener) {
		presetComboBox.addActionListener(listener);
	}
}
