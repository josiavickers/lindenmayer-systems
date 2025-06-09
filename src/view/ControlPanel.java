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
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	private JSpinner stepSpinner;
	private JSpinner iterationSpinner;
	private JButton generateButton;
	private JComboBox<PredefinedLSystem> presetComboBox;
	private JTextArea rules;
	private JTextArea commands;
	private JCheckBox checkbox;

	
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

		// check box for custom commands
		checkbox = new JCheckBox("Use Custom Commands");
		checkbox.setFont(new Font("Arial Black", Font.BOLD, 19));
		checkbox.setSelected(false);
		add(checkbox, "wrap, gapy 0 10,align left");

		// Disable commands field if custom commands aren't selected
		checkbox.addActionListener(e -> {
			commands.setEnabled(checkbox.isSelected());
		});

		// Command input
		JLabel commandsLabel = new JLabel("Commands");
		commandsLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(commandsLabel, "wrap, align left");
		commands = new JTextArea(2,13);
		commands.setFont(new Font("Arial Black", Font.BOLD,20));
		commands.setToolTipText("Enter Commands");
		commands.setLineWrap(true);
		commands.setMargin(new Insets(5, 10, 5, 10));
		commands.setEnabled(false);
		add(commands, "wrap,gapy 0 10, align center");

		// Turning Angle spinner
		JLabel angleLabel = new JLabel("Turning Angle:     ");
		angleLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(angleLabel, "split 2, alignx left");
		angleSpinner = new JSpinner();
		angleSpinner.setModel(new SpinnerNumberModel(45.0, 0.0, 360.0, 0.5));
		angleSpinner.setToolTipText("Enter Rotation Angle");
		angleSpinner.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(angleSpinner, "wrap,gapy 0 10, alignx right");

		// Step Length spinner
		JLabel stepLabel = new JLabel(" Step Length:       ");
		stepLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(stepLabel, "split 2, alignx left");
		stepSpinner = new JSpinner();
		stepSpinner.setModel(new SpinnerNumberModel(5, 1, 100, 1));
		stepSpinner.setToolTipText("Enter Step Length");
		stepSpinner.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(stepSpinner, "wrap,gapy 0 10, alignx right");
		

		// Iteration Depth spinner
		JLabel iterationLabel = new JLabel(" Iteration Depth: ");
		iterationLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		
		add(iterationLabel, "split 2, alignx left");
		iterationSpinner = new JSpinner();
		iterationSpinner = new JSpinner();
		iterationSpinner.setModel(new SpinnerNumberModel(5, 1, 20, 1));
		iterationSpinner.setToolTipText("Enter Iteration Depth");
		iterationSpinner.setFont(new Font("Arial Black", Font.BOLD, 20));
		iterationSpinner.setSize(8,4);
		add(iterationSpinner, "w 83, wrap,gapy 0 10, alignx right");

		// Render button
		generateButton = new JButton("Generate");
		generateButton.setFont(new Font("Arial Black", Font.BOLD, 20));
		add(generateButton, "gapy 40 5,align center");

	}

	// Getters
	public String getAxiom() {
		return axiomField.getText();
	}

	public String getRules() {
		return rules.getText();
	}

	public String getCommands() {
		return commands.getText();
	}

	public JSpinner getAngleSpinner() {
		return angleSpinner;
	}

	public double getAngle() {
		return (double) angleSpinner.getValue();
	}

	public JSpinner getStepSpinner() {
		return stepSpinner;
	}

	public int getStep() {
		return (int) stepSpinner.getValue();
	}

	public JSpinner getIterationSpinner() {
		return iterationSpinner;
	}

	public int getIteration() {
		return (int) iterationSpinner.getValue();
	}

	public JButton getGenerateButton() {
		return generateButton;
	}

	public JCheckBox getCheckbox() {
		return checkbox;
	}

	public PredefinedLSystem getSelectedPreset() {
		return (PredefinedLSystem) presetComboBox.getSelectedItem();
	}

	// Setters
	public void setAxiom(String str) {
		axiomField.setText(str);
	}

	public void setRules(String str) {
		rules.setText(str);
	}

	public void setCommands(String str) {
		commands.setText(str);
	}

	public void setAngle(double deg) {
		angleSpinner.setValue(deg);
	}

	public void setIterations(int iterations) {

		iterationSpinner.setValue(iterations);
	}

	public void setStep(int length) {
		stepSpinner.setValue(length);
	}

	public void setSelectedPreset(PredefinedLSystem preset) {
		presetComboBox.setSelectedItem(preset);
	}

	// Add listeners
	public void addPresetComboBoxListener(ActionListener listener) {
		presetComboBox.addActionListener(listener);
	}

	public void addButtonListener(ActionListener listener) {
		generateButton.addActionListener(listener);
	}
}
