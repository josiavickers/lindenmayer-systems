/*
 *@author: Eliyas Tadesse
 */
package model;

import java.awt.Color;
import java.util.Arrays;
import java.util.Comparator;

/**
 * This class represents a predefined L-System configuration. These
 * configurations are commonly used fractal patterns that can be loaded directly
 * by the application.
 */
public class PredefinedLSystem {
	private String name;
	private String axiom;
	private String rules;
	private int iterations;
	private int step;
	private double angle;
	private int thickness;
	private Color colour;

	public PredefinedLSystem(String name, String axiom, String rules, double angle, int step, int iterations, int thickness, Color colour) {
		this.name = name;
		this.axiom = axiom;
		this.rules = rules;
		this.iterations = iterations;
		this.step = step;
		this.angle = angle;
		this.thickness = thickness;
		this.colour = colour;
	}

	// Getters
	public String getName() {
		return name;
	}

	public String getAxiom() {
		return axiom;
	}

	public String getRules() {
		return rules;
	}

	public int getIterations() {
		return iterations;
	}

	public int getStep() {
		return step;
	}

	public double getAngle() {
		return angle;
	}
	
	public int getThickness() {
		return thickness;
	}
	
	public Color getColour() {
		return colour;
	}

	/**
	 * Returns a sorted array of predefined L-System configurations. Each instance
	 * represents a well-known fractal or plant pattern.
	 */
	public static PredefinedLSystem[] predefinedLSystems() {
		PredefinedLSystem[] predefined = {
				new PredefinedLSystem("Weed", "Y", "X→X[-FFF][+FFF]FX, Y→YFX[+Y][-Y]", 25.7, 5, 5, 1, Color.BLACK),
				new PredefinedLSystem("Plant3", "X", "X→F[-X][X]F[-X]+FX,F→FF", 20, 5, 5, 1, Color.BLACK),
				new PredefinedLSystem("Seaweed", "F", "F→FF+[+F-F-F]-[-F+F+F]", 22.5, 7, 4, 1, Color.BLACK),
				new PredefinedLSystem("Stick Plant", "X", "X→F[+X][-X]FX, F→FF[-F]F", 20, 6, 4, 1, Color.BLACK),
				new PredefinedLSystem("Weed Plant", "X", "X→F[+X][-X]FX, F→FF", 25, 10, 5, 1, Color.BLACK),
				new PredefinedLSystem("Plant1", "X", "F→FF, X→F[+X]F[-X]+X", 20, 5, 6, 1, Color.BLACK),
				new PredefinedLSystem("Tree1", "F", "F→F[+F][-F]", 15, 10, 6, 1, Color.BLACK),
				new PredefinedLSystem("Plant2", "X", "F→FF, X→F+[[FX]-X]-F[-FX]+X", 20, 8, 5, 1, Color.BLACK),
				new PredefinedLSystem("Tree2", "X", "F→FF, X→F[+X][-X]", 45, 10, 6, 1, Color.BLACK) };
		// Sort the predefined L-systems alphabetically by name
		Arrays.sort(predefined, Comparator.comparing(PredefinedLSystem::getName));
		return predefined;
	}
}
