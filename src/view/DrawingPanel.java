/*
 *  @author: Eliyas Tadesse
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Turtle;
import model.TurtleCommand;

/**
 * The DrawingPanel is responsible for rendering the L-System pattern using
 * turtle graphics. It supports interactive zooming, panning, and saving the
 * rendered image.
 */
public class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// Core drawing parameters
	private String lSystemString;
	private double angle;
	private int step;
	private int thickness;
	private Color colour;
	private Map<Character, TurtleCommand> commandMap = new HashMap<>();

	// View transformation parameters
	private double scalingFactor = 1.0;
	private final double SCALE_STEP = 0.1;
	private final double MINIMUM_ZOOMOUT_SCALE = 0.1;
	private double offsetX = 0;
	private double offsetY = 0;
	private Point lastMousePosition;

	// Flag to prevent repeated error message dialog
	private boolean errorDisplayed = false;

	/**
	 * Initializes the drawing panel with default background and input listeners.
	 */
	public DrawingPanel() {
		setLayout(new BorderLayout());
		setBackground(new Color(200, 200, 164));
		setupZoomListner();
		setupPanningListener();
	}

	/**
	 * Main rendering logic for the L-System. Called automatically when repaint() is
	 * triggered.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (!validateInputs())
			return;

		Graphics2D g2 = (Graphics2D) g;

		// Apply zoom and panning transformations
		g2.translate(offsetX, offsetY);
		g2.scale(scalingFactor, scalingFactor);

		// Initialize turtle at screen midpoint
		Turtle turtle = new Turtle(g2, screenMid().x, screenMid().y);
		turtle.dropPen();

		try {
			for (char ch : lSystemString.toCharArray()) {
				TurtleCommand command = commandMap.get(ch);
				try {
					handleTurtleCommand(turtle, command);

				} catch (Exception e) {
					showErrorDialog("No command provided for character: '" + ch + "'");
					return;
				}
			}
		} catch (Exception e) {
			showErrorDialog("Unexpected error while drawing: " + e.getMessage());
		}
	}

	/**
	 * Validates that required input fields are set before rendering begins.
	 */
	private boolean validateInputs() {
		if (lSystemString == null || lSystemString.isBlank()) {
			showErrorDialog("L-System string is null or empty. Nothing to draw.");
			return false;
		}
		if (commandMap == null || commandMap.isEmpty()) {
			showErrorDialog("Command map cannot be null or empty.");
			return false;
		}
		return true;
	}

	/**
	 * Executes a turtle instruction based on the provided command.
	 */
	private void handleTurtleCommand(Turtle turtle, TurtleCommand command) {

		switch (command) {
		case MOVE -> turtle.move(step, thickness, colour);
		case TURNRIGHT -> turtle.right(angle);
		case TURNLEFT -> turtle.left(angle);
		case PUSH -> turtle.push();
		case POP -> turtle.pop();
		}
	}

	// Setters for rendering parameters
	public void setLSystemString(String lSystemString) {
		this.lSystemString = lSystemString;
		repaint();
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
	public void setThickness(int thickness) {
		this.thickness = thickness;
	}
	
	public void setColour(Color colour) {
		this.colour = colour;
	}

	public void setCommandMap(Map<Character, TurtleCommand> commandMap) {
		this.commandMap = commandMap;
	}

	/**
	 * Returns the center-bottom starting point for the turtle. Ensures consistent
	 * starting location regardless of canvas size.
	 */
	private Point screenMid() {
		int x = getWidth() / 2;
		int y = getHeight() - 100;
		return new Point(x, y);
	}

	/**
	 * Adds a mouse wheel listener to support zoom functionality.
	 */
	public void setupZoomListner() {
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {

				// Zoom in or out based on scroll direction
				double oldScale = scalingFactor;
				scalingFactor = (e.getPreciseWheelRotation() < 0) ? scalingFactor + SCALE_STEP
						: Math.max(scalingFactor - SCALE_STEP, MINIMUM_ZOOMOUT_SCALE);

				double factor = scalingFactor / oldScale;

				// Adjust offsets to keep zoom centered at cursor
				Point mousePoint = e.getPoint();
				offsetX = mousePoint.getX() - factor * (mousePoint.getX() - offsetX);
				offsetY = mousePoint.getY() - factor * (mousePoint.getY() - offsetY);

				repaint();
			}
		});
	}

	/**
	 * Adds mouse listeners to support panning of the drawing.
	 */
	private void setupPanningListener() {

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lastMousePosition = e.getPoint();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (lastMousePosition != null) {
					int dx = e.getX() - lastMousePosition.x;
					int dy = e.getY() - lastMousePosition.y;
					offsetX += dx;
					offsetY += dy;
					lastMousePosition = e.getPoint();
					repaint();
				}
			}
		});
	}

	/**
	 * Shows a dialog box for critical errors encountered during rendering. Ensures
	 * it is only shown once at a time.
	 */
	private void showErrorDialog(String message) {
		if (!errorDisplayed) {
			errorDisplayed = true;
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
				errorDisplayed = false;
			});
		}
	}
}
