/*
 * @author: Eliyas Tadesse
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	private final ControlPanel controlPanel;
	private final DrawingPanel drawingPanel;
	private final MenuBar menuBar;

	public MainFrame() {
		controlPanel = new ControlPanel();
		drawingPanel = new DrawingPanel();
		menuBar = new MenuBar();

		Dimension screenSize = getToolkit().getScreenSize();
		setTitle("L-System Generator and Visualizer");
		setBounds(0, 0, screenSize.width, screenSize.height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		add(drawingPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.WEST);
		add(menuBar, BorderLayout.NORTH);

		// setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/lindenmayer/plant.png")));

		setVisible(true);

	}

	public MenuBar getMenu() {
		return menuBar;
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public DrawingPanel getDrawingPanel() {
		return drawingPanel;
	}
}
