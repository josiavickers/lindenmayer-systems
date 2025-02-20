/*
 * @author: Eliyas Tadesse
 */
package model;

import java.util.Map;

public enum TurtleCommand {
	MOVE, TURNRIGHT, TURNLEFT, PUSH, POP;

	public static final Map<Character, TurtleCommand> PREDEFINED_COMMANDS = Map.of('A', TurtleCommand.MOVE, 'B',
			TurtleCommand.MOVE, 'F', TurtleCommand.MOVE, 'G', TurtleCommand.MOVE, 'X', TurtleCommand.MOVE, 'Y',
			TurtleCommand.MOVE, '+', TurtleCommand.TURNRIGHT, '-', TurtleCommand.TURNLEFT, '[', TurtleCommand.PUSH, ']',
			TurtleCommand.POP);

}
