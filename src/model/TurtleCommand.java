/*
 * @author: Eliyas Tadesse
 */
package model;

import java.util.Map;

/**
 * Enum representing turtle graphics commands used to interpret and draw an
 * L-system.
 */
public enum TurtleCommand {
	MOVE,       // Move forward and draw 
	TURNRIGHT,  // Turn right by the current angle
	TURNLEFT,   // Turn left by the current angle
	PUSH,       // Save the current drawing state 
	POP;        // Restore the last saved drawing state

	/**
	 * A predefined mapping of characters to turtle commands for common L-system
	 * usage. This allows automatic interpretation of characters included here.
	 */
	public static final Map<Character, TurtleCommand> PREDEFINED_COMMANDS = Map.of(
			'A', TurtleCommand.MOVE, 
			'B', TurtleCommand.MOVE, 
			'F', TurtleCommand.MOVE,
			'G', TurtleCommand.MOVE, 
			'X', TurtleCommand.MOVE, 
			'Y', TurtleCommand.MOVE, 
			'+', TurtleCommand.TURNRIGHT, 
			'-', TurtleCommand.TURNLEFT,
			'[', TurtleCommand.PUSH, 
			']', TurtleCommand.POP);
}
