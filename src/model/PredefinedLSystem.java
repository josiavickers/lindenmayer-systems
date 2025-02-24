/*
 *@author: Eliyas Tadesse
 */
package model;

import java.util.Arrays;
import java.util.Comparator;

public class PredefinedLSystem {
	private final String name;
	private final String axiom;
	private final String rules;
	private final int iterations;
	private final int step;
	private final double angle;

	public PredefinedLSystem(String name, String axiom, String rules, double angle, int step, int iterations) {
		this.name = name;
		this.axiom = axiom;
		this.rules = rules;
		this.iterations = iterations;
		this.step = step;
		this.angle = angle;
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

	// Predefined LSystems
	public static PredefinedLSystem[] predefinedLSystems() {
		PredefinedLSystem[] predefined = {
				new PredefinedLSystem("Quadratic Snowflake", "F-F-F-F", "F->F+F-F-F+F", 90, 5, 4),
				new PredefinedLSystem("Dragon Curve", "FX", "X->X+YF+, Y->-FX-Y", 90, 10, 10),
				new PredefinedLSystem("Weed", "Y", "X->X[-FFF][+FFF]FX, Y->YFX[+Y][-Y]", 25.7, 5, 5),
				new PredefinedLSystem("Plant3", "X", "X->F[-X][X]F[-X]+FX,F->FF", 20, 5, 5),
				new PredefinedLSystem("Seaweed", "F", "F->FF+[+F-F-F]-[-F+F+F]", 22.5, 7, 4),
				new PredefinedLSystem("Stick Plant", "X", "X->F[+X][-X]FX, F->FF[-F]F", 20, 6, 4),
				new PredefinedLSystem("Weed Plant", "X", "X->F[+X][-X]FX, F->FF", 25, 10, 5),
				new PredefinedLSystem("Plant1", "X", "F->FF, X->F[+X]F[-X]+X", 20, 5, 6),
				new PredefinedLSystem("Tree1", "F", "F->F[+F][-F]", 15, 10, 6),
				new PredefinedLSystem("Plant2", "X", "F->FF, X->F+[[FX]-X]-F[-FX]+X", 20, 8, 5),
				new PredefinedLSystem("Tree2", "X", "F->FF, X->F[+X][-X]", 45, 10, 6),
				new PredefinedLSystem("Lévy curve", "F", "F->-F++F-", 45, 5, 10),
				new PredefinedLSystem("Rings", "F+F+F+F", "F->FF+F+F+F+F+F-F", 90, 5, 4),
				new PredefinedLSystem("Cross", "F+F+F+F", "F->F+F-F+F+F", 90, 5, 4),
				new PredefinedLSystem("Koch Snowflake", "F++F++F", "F->F-F++F-F", 60, 5, 4),
				new PredefinedLSystem("Sierpinski Arrowhead", "YF", "X->YF+XF+Y,Y -> XF-YF-X", 60, 5, 7),
				new PredefinedLSystem("Hilbert", "X", "X->-YF+XFX+FY-,Y->+XF-YFY-FX+", 90, 5, 7),
				new PredefinedLSystem("Koch Curve", "F+F+F+F", "F->F+F-F-FF+F+F-F", 90, 5, 4),
				new PredefinedLSystem("Quadratic Koch Island", "F+F+F+F", "F->F+F-F-FFF+F+F-F", 90, 5, 4),
				new PredefinedLSystem("Crystal", "F+F+F+F", "F->FF+F++F+F", 90, 5, 4),
				new PredefinedLSystem("Sierpinski Square", "F+XF+F+XF", "X -> XF-F+F-XF+F+XF-F+F-X", 90, 5, 4) };
		Arrays.sort(predefined, Comparator.comparing(PredefinedLSystem::getName));

		return predefined;
	}

}
