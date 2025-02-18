/*
 * @author: Eliyas Tadesse
 */
package model;

import java.util.Map;

public enum TurtleCommand {
	MOVE, TURNRIGHT, TURNLEFT, SAVE, REMOVE;

	public static final Map<Character, TurtleCommand> PREDEFINED_COMMANDS = Map.of('F', TurtleCommand.MOVE, 'G',
			TurtleCommand.MOVE, 'V', TurtleCommand.MOVE, 'X', TurtleCommand.MOVE, 'Y', TurtleCommand.MOVE, 'Z',
			TurtleCommand.MOVE, '+', TurtleCommand.TURNRIGHT, '-', TurtleCommand.TURNLEFT, '[', TurtleCommand.SAVE, ']',
			TurtleCommand.REMOVE);

}
