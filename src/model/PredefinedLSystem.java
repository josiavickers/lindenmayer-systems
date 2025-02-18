/*
 *@author: Eliyas Tadesse
 */
package model;

public class PredefinedLSystem {
	private final String name;
	private final String axiom;
	private final String rules;
	private final int iterations;
	private final int step;
	private double angle;

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
	// https://paulbourke.net/fractals/lsys/ -> source
	public static PredefinedLSystem[] predefinedLSystems() {
		return new PredefinedLSystem[] { new PredefinedLSystem("Quadratic Snowflake", "F-F-F-F", "F->F+F-F-F+F", 90, 5, 4),
				new PredefinedLSystem("Sierpiński Triangle", "F-G-G", "F->F-G+F+G-F, G->GG", 120, 5, 5),
				new PredefinedLSystem("Dragon Curve", "FX", "X->X+YF+, Y->-FX-Y", 90, 10, 10),
				new PredefinedLSystem("L-System Bush1", "Y", "X->X[-FFF][+FFF]FX, Y->YFX[+Y][-Y]", 25.7, 5, 5),
				new PredefinedLSystem("L-System Bush2", "VZFFF", "V->[+++G][---G]YV, G->+X[-G]Z], X->-G[+X]Z, Y->YZ, Z -> [-FFF][+FFF]F", 20, 5, 5),
				new PredefinedLSystem("L-System Bush3", "F", "F->FF+[+F-F-F]-[-F+F+F]", 22.5, 5, 5),
				new PredefinedLSystem("Flower Like Bush", "FX", "X -> >[-FX]+FX", 40, 5, 5),
				new PredefinedLSystem("L-System Weed", "F", "F->FF-[XY]+[XY], X->+FY, Y->-FX", 22.5, 5, 4),
				new PredefinedLSystem("L-System Sticks", "X", "F->FF, X->F[+X]F[-X]+X", 20, 5, 6),
				new PredefinedLSystem("Lévy curve", "F", "F->-F++F-", 45, 5, 4),
				new PredefinedLSystem("Rings", "F+F+F+F", "F->FF+F+F+F+F+F-F", 90, 5, 4),
				new PredefinedLSystem("Cross", "F+F+F+F", "F->F+F-F+F+F", 90, 5, 4),
				new PredefinedLSystem("Koch Snowflake", "F++F++F", "F->F-F++F-F", 60, 5, 4),
				new PredefinedLSystem("Sierpinski Arrowhead", "YF", "X->YF+XF+Y,Y -> XF-YF-X", 60, 5, 7),
				new PredefinedLSystem("Hilbert", "X", "X->-YF+XFX+FY-,Y->+XF-YFY-FX+", 90, 5, 7),
				new PredefinedLSystem("Koch Curve", "F+F+F+F", "F->F+F-F-FF+F+F-F", 90, 5, 4),
				new PredefinedLSystem("Quadratic Koch Island", "F+F+F+F", "F->F+F-F-FFF+F+F-F", 90, 5, 4),
				new PredefinedLSystem("Crystal", "F+F+F+F", "F->FF+F++F+F", 90, 5, 4),
				new PredefinedLSystem("Square Sierpinski", "F+XF+F+XF", "X -> XF-F+F-XF+F+XF-F+F-X", 90, 5, 4)

		};
	}
}
