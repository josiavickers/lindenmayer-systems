/*
 * @author: Eliyas Tadesse
 */
package view;

import java.awt.Color;
import java.awt.Component;
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

public class ControlPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField axiomField;
	private JSpinner angleSpinner;
	private JSpinner stepSpinner;
	private JSpinner iterationSpinner;
	private JButton generateButton;
	private final JComboBox<PredefinedLSystem> presetComboBox;
	private JTextArea rules;
	private JTextArea commands;
	private final JCheckBox checkbox;

	@SuppressWarnings("serial")
	public ControlPanel() {

		setLayout(new MigLayout());
		setBackground(Color.getHSBColor(0.55f, 0.75f, 0.75f));

		// Add drop down for presets
		presetComboBox = new JComboBox<>(PredefinedLSystem.predefinedLSystems());
		presetComboBox.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value instanceof PredefinedLSystem predefinedLSystem) {
					setText(predefinedLSystem.getName());
				}else {
					setText("");
				}
				setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
		        setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
				return this;
			}
		});
		presetComboBox.setFont(new Font("Arial Black", Font.BOLD, 12));
		JLabel comboBoxLabel = new JLabel("L-systems:");
		comboBoxLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		this.add(comboBoxLabel, "split 2");
		add(presetComboBox, "wrap");

		// Axiom input
		JLabel axiomLabel = new JLabel("Axiom:");
		axiomLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		this.add(axiomLabel, "split 2");

		axiomField = new JTextField(10); // 10 is number of columns
		axiomField.setFont(new Font("Arial Black", Font.BOLD, 12));
		axiomField.setBackground(Color.LIGHT_GRAY);
		axiomField.setForeground(Color.BLACK);
		axiomField.setToolTipText("Enter Axiom");
		axiomField.setMargin(new Insets(5, 10, 5, 10));
		this.add(axiomField, "wrap");

		// Rules input
		JLabel rulesLabel = new JLabel("Rules:");
		rulesLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		this.add(rulesLabel, "split 2");
		rules = new JTextArea(2, 20);
		rules.setFont(new Font("Arial Black", Font.BOLD, 12));
		rules.setBackground(Color.LIGHT_GRAY);
		rules.setForeground(Color.BLACK);
		rules.setToolTipText("Enter Rules");
		rules.setLineWrap(true);
		rules.setMargin(new Insets(5, 10, 5, 10));
		this.add(rules, "wrap");

		// check box for predefined commands
		checkbox = new JCheckBox("Use Predefined Commands", true);
		add(checkbox, "wrap");
		
		// Disable commands field if predefined commands are selected
		checkbox.addActionListener(e -> {
			commands.setEnabled(!checkbox.isSelected());
		});

		// Command input
		JLabel commandsLabel = new JLabel("Commands:");
		commandsLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		this.add(commandsLabel, "split 2");
		commands = new JTextArea(2, 20);
		commands.setFont(new Font("Arial Black", Font.BOLD, 12));
		commands.setBackground(Color.LIGHT_GRAY);
		commands.setForeground(Color.BLACK);
		commands.setToolTipText("Enter Rules");
		commands.setLineWrap(true);
		commands.setMargin(new Insets(5, 10, 5, 10));
		this.add(commands, "wrap");

		// Angle spinner
		JLabel angleLabel = new JLabel("Angle: ");
		angleLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		this.add(angleLabel, "split 2");
		angleSpinner = new JSpinner();
		angleSpinner.setModel(new SpinnerNumberModel(45.0, 0.0, 360.0, 5.0));
		angleSpinner.setToolTipText("Enter Angle");
		angleSpinner.setFont(new Font("Arial Black", Font.BOLD, 14));
		this.add(angleSpinner, "wrap");

		// Step spinner
		JLabel stepLabel = new JLabel("Step: ");
		stepLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		this.add(stepLabel, "split 2");
		stepSpinner = new JSpinner();
		stepSpinner.setModel(new SpinnerNumberModel(5, 1, 100, 1));
		stepSpinner.setToolTipText("Enter Step");
		stepSpinner.setFont(new Font("Arial Black", Font.BOLD, 14));
		this.add(stepSpinner, "wrap");

		// Iteration spinner
		JLabel iterationLabel = new JLabel("Iteration: ");
		iterationLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		this.add(iterationLabel, "split 2");
		iterationSpinner = new JSpinner();
		iterationSpinner = new JSpinner();
		iterationSpinner.setModel(new SpinnerNumberModel(5, 1, 20, 1));
		iterationSpinner.setToolTipText("Enter Iteration");
		iterationSpinner.setFont(new Font("Arial Black", Font.BOLD, 14));
		this.add(iterationSpinner, "wrap");

		// Render button
		generateButton = new JButton("Generate");
		this.add(generateButton, "align center");

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
