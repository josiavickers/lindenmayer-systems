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
		int dx = (int) (Math.cos(state.angle) * step);
		int dy = (int) (Math.sin(state.angle) * step);
		dy = -dy;
		if (state.pendown) {
			g.drawLine(state.x, state.y, state.x + dx, state.y + dy);
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
	public void left(double angle) {
		angle = angle * 2 * Math.PI / 360.0;
		state.angle += angle;
		if (state.angle >= 360.0)
			state.angle -= 360.0;
	}

	public void right(double angle) {
		left(-angle);
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

	public class State {
		boolean pendown;
		int x;
		int y;
		double angle;
	}
}
