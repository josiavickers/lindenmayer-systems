/*
 * @author: Eliyas Tadesse
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	private ControlPanel controlPanel;
	private DrawingPanel drawingPanel;

	/**
	 * Constructs the main application window with control panel, drawing panel, and
	 * menu bar.
	 */
	public MainFrame() {
		controlPanel = new ControlPanel();
		drawingPanel = new DrawingPanel();

		Dimension screenSize = getToolkit().getScreenSize();
		setTitle("L-System Generator and Visualizer");
		setBounds(0, 0, screenSize.width, screenSize.height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Wrap the control panel in a scroll pane
		JScrollPane scrollPane = new JScrollPane(controlPanel);

		// Configure scroll pane behavior
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		add(drawingPanel, BorderLayout.CENTER);
		add(scrollPane, BorderLayout.WEST);

		setVisible(true);

	}

	// Getters
	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public DrawingPanel getDrawingPanel() {
		return drawingPanel;
	}
}
