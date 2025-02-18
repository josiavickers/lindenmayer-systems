/*
 *  @author: Eliyas Tadesse
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import model.Turtle;
import model.TurtleCommand;



public class DrawingPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String commands;
	private double angle;
	private int step;
	private double scalingFactor = 1.0; 
	private final double SCALE_STEP = 0.1; 
	private final double MINIMUM_ZOOMOUT_SCALE = 0.1;
	private double offsetX = 0; 
	private double offsetY = 0; 
	private Map<Character, TurtleCommand> commandMap = new HashMap<>();
	public DrawingPanel() {
		setLayout(new BorderLayout());
		setBackground(new Color(200, 200, 164));
		zoomInOut();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(offsetX, offsetY);
		g2.scale(scalingFactor, scalingFactor);
		Point screenMid = screenMid();
		Turtle turtle = new Turtle(g2, screenMid.x, screenMid.y);
		turtle.dropPen();
		
		for (char c : commands.toCharArray()) {
        	TurtleCommand cmd = commandMap.get(c);
            if (cmd == null) {
            	continue; 
            }
            switch (cmd) {
                case MOVE:
                	turtle.move(step);
                    break;
                case TURNRIGHT:
                	turtle.right(angle);
                     break;
                case TURNLEFT:
                	turtle.left(angle);
                    break;
                case SAVE:
                	turtle.push();
                    break;
                case REMOVE:
                	turtle.pop();
                default:
                	break;
            }
        }
	}

	public void setCommands(String commands) {
		this.commands = commands;
		repaint();
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
	 public void setCommandMap(Map<Character, TurtleCommand> commandMap) {
	        this.commandMap = commandMap;
	    }
	    

	private Point screenMid() {
		int x = getWidth() / 2;
		int y = getHeight() - 100;
		return new Point(x, y);
	}

	public void zoomInOut() {
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// Get mouse pointer position
				Point mousePoint = e.getPoint();
				// Zoom in or out based on scroll direction
				double oldScale = scalingFactor;
				if (e.getPreciseWheelRotation() < 0) {
					scalingFactor += SCALE_STEP; 
				} else {
					scalingFactor = Math.max(scalingFactor - SCALE_STEP, MINIMUM_ZOOMOUT_SCALE); 
				}
				double scaleFactor = scalingFactor / oldScale;

				offsetX = mousePoint.getX() - scaleFactor * (mousePoint.getX() - offsetX);
				offsetY = mousePoint.getY() - scaleFactor * (mousePoint.getY() - offsetY);

				repaint();
			}
		});
	}

}
