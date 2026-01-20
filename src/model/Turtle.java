/**
 * @author: Bjoern Gottfried
 */
package model;

import java.awt.*;
import java.util.*;

/**
 * This class implements methods for turtle graphics. It is based
 * on the Graphics class. 
 * 
 * What can be changed:
 * Position,
 * Orientation and
 * state of the drawing pen (raised vs. lowered).
 * 
 * At the beginning the Turtle shows to the north and the pen is raised.
 */
public class Turtle {
	private Graphics g;
	private State state;
	private Stack<State> stateStack;
	private Color currentColour = null;

	public Turtle(Graphics g, int startX, int startY) {
		this.g = g;
		state = new State();
		stateStack = new Stack<State>();
		state.x = startX;
		state.y = startY;
		turnNorth();
		liftPen();
	}

	public void move(double step) {
	// A. MODULATE STEP LENGTH
		
		// 1. ORIGINAL:
		//double effectiveStep = step;
		
		// 2. EXPONENTIAL CHANGE:
		//double effectiveStep = step * Math.pow(stepFactor, Math.max(0, i - 1));
		
		// 3. LINEAR CHANGE:
		//double effectiveStep = step + i * stepFactor;
		
		// 4. Multiply by a (random) factor
		//double effectiveStep = step * RandomUtil.getRandomInRange(1.0, 1.0);
		
	// B. MODULATE STROKE THICKNESS
		
		// 1. ORIGINAL:
		//double effectiveThickness = thickness;
		
		// 2. EXPONENTIAL CHANGE:
		//double effectiveThickness = thickness * Math.pow(thicknessFactor, Math.max(0, i - 1));
		
		// 3. LINEAR CHANGE:
		//double effectiveThickness = thickness + i * thicknessFactor;
		//double effectiveThickness = thickness*200 - i * thicknessFactor;
		
		// 4. Multiply by a (random) factor
		//double effectiveThickness = thickness * RandomUtil.getRandomInRange(1.0, 10.0);
		
	// C. MODULATE COLOUR (Note Color class has useful methods like lighter(), darker() that could be considered too)
		
		// 1. ORIGINAL:
		//Color effectiveColour = colour;
		
		// 2. NEXT SHADE IN COLOUR SPECTRUM (WORKS BEST WITH LOW NUMBER OF ITERATIONS SET)
//		if (this.currentColour == null) {
//			this.currentColour = colour;
//		} else {
//			this.currentColour = this.nextShade(this.currentColour, (float)colourFactor);
//		}
//		Color effectiveColour = this.currentColour;
		
		// Turtle Actions
		Graphics2D g2 = (Graphics2D) this.g; // Cast to accommodate stroke thickness
		//g2.setStroke(new BasicStroke((float)effectiveThickness));
		
		int dx = (int) (Math.cos(state.angle) * step);
		int dy = (int) (Math.sin(state.angle) * step);
		dy = -dy;
		if (state.pendown) {
			//g2.setColor(effectiveColour);
			g2.drawLine(state.x, state.y, state.x + dx, state.y + dy);
		}
		state.x += dx;
		state.y += dy;
	}

	/**
	 * Turn to the north.
	 */
	public void turnNorth() {
		state.angle = Math.PI / 2.0;
	}

	/**
	 * turn to the left [degree]
	 */
	public void left(double angle, double factor, double randomFactor, int i) {
		// MODULATE TURNING ANGLE
		
		// 1. ORIGINAL:
		//double effectiveAngle = angle; 
		
		// 2. EXPONENTIAL ANGLE CHANGE
        // Multiply angle by exponentially increasing factor per character iteration of the L System string:
        // i = 0 → angle
        // i = 1 → angle * factor
        // i = 2 → angle * factor^2, etc.
		//double effectiveAngle = angle * Math.pow(factor, Math.max(0, i - 1));
		
		// 3. LINEAR ANGLE CHANGE
		double effectiveAngle = angle + i * factor;
		
		// 4. Multiply by a (random) factor
		effectiveAngle = effectiveAngle * RandomUtil.getRandomInRange(1.0, randomFactor);
		
		// OPTION: ANGLE TOGGLING
//		if (i % 2 == 0) { // toggle on even iterations
//			effectiveAngle *= -1;
//		}
		
		effectiveAngle = effectiveAngle * 2 * Math.PI / 360.0;
		state.angle += effectiveAngle;
		if (state.angle >= 360.0)
			state.angle -= 360.0;
	}

	public void right(double angle, double factor, double randomFactor, int i) {
		left(-angle, factor, randomFactor, i);
	}

	/**
	 * All the movements following this command leave no trace on the screen. 
	 */
	public void liftPen() {
		state.pendown = false;
	}

	/**
	 * All the movements following this command leave a trace on the screen.
	 */
	public void dropPen() {
		state.pendown = true;
	}

	/**
	 * Save current state.
	 */
	public void push() {
		State s = new State();
		s.pendown = state.pendown;
		s.x = state.x;
		s.y = state.y;
		s.angle = state.angle;
		stateStack.push(s);
	}

	/**
	 * Activates the last saved state.
	 */
	public void pop() {
		if (!stateStack.empty())
			state = (State)stateStack.pop();
	}
	
	/**
	 * Helper method to get the next colour with gradient change controlled by factor
	 * @param current
	 * @param factor
	 * @return next shade - Color
	 */
	private Color nextShade(Color current, float factor) {

	    // Scale factor down for smoother colour transitions
		if (factor > 0) 
			factor *= 0.1f;
		
	    // Convert RGB to HSB
	    float[] hsb = Color.RGBtoHSB(
	        current.getRed(),
	        current.getGreen(),
	        current.getBlue(),
	        null
	    );

	    // Increment hue by factor (wrap around at 1.0)
	    float newHue = (hsb[0] + factor) % 1.0f;

	    // Create new color with same saturation & brightness
	    return Color.getHSBColor(newHue, hsb[1], hsb[2]);
	}


	public class State {
		boolean pendown;
		int x;
		int y;
		double angle;
	}
}
