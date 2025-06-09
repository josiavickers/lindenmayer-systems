/*
 *  @author: Eliyas Tadesse 
 */
package model;

import java.util.HashMap;
import java.util.Map;

/**
 * LSystemParser is responsible for parsing and generating L-System strings
 * based on a given axiom, production rules, and number of iterations. It also
 * supports parsing custom turtle commands for graphical interpretation.
 */
public class LSystemParser {

	private int iterations;
	private String axiom;
	private Map<Character, String> rules;
	private final int MAX_STRING_LENGTH = 50_000_000;

	public LSystemParser(String axiom, String rulesInput, int iterations) {
		this.iterations = iterations;
		this.rules = parseRules(rulesInput);
		this.axiom = axiom;

	}

	/**
	 * Generates the L-System string by applying production rules iteratively.
	 *
	 * @return the final L-System string after all iterations
	 */
	public String generateLSystemString() {
		validateInput(axiom, "Axiom input cannot be null or empty.");
		String result = axiom;
		for (int i = 0; i < iterations; i++) {
			StringBuilder newResult = new StringBuilder(result.length());
			for (char c : result.toCharArray()) {
				newResult.append(rules.getOrDefault(c, String.valueOf(c)));
				if (newResult.length() > MAX_STRING_LENGTH) {
					throw new IllegalStateException("Generated string exceeds maximum allowed length ("
							+ MAX_STRING_LENGTH + "). \nPlease reduce the number of iterations.");
				}
			}
			result = newResult.toString();
		}
		return result;
	}

	/**
	 * Parses the production rules from a user-provided input string. Example
	 * format: "F→FF,X→XY"
	 *
	 * @param rulesInput string containing rule pairs
	 * @return a map where each character is mapped to its replacement string
	 */
	public Map<Character, String> parseRules(String rulesInput) {
		validateInput(rulesInput, "Rules input cannot be null or empty.");
		Map<Character, String> rules = new HashMap<>();
		// Remove whiteSpace and Split into rule pairs
		String[] rulePairs = rulesInput.replaceAll("\\s+", "").split(",");
		for (String pair : rulePairs) {
			// Split each rule into "key→value" pairs
			String[] parts = pair.split("→");
			if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
				throw new IllegalArgumentException("Invalid rule format: " + pair);
			}
			char key = parts[0].charAt(0);
			rules.putIfAbsent(key, parts[1]);
		}
		return rules;
	}

	/**
	 * Parses custom turtle commands provided by the user. Example format:
	 * "F=MOVE,+=TURNRIGHT,-=TURNLEFT"
	 *
	 * @param commandsInput input string defining commands
	 * @return a map of characters to corresponding TurtleCommand enums
	 */
	public Map<Character, TurtleCommand> parseCommands(String commandsInput) {
		validateInput(commandsInput, "Commands input cannot be null or empty.");

		Map<Character, TurtleCommand> commands = new HashMap<>();
		// Remove whiteSpace and Split into individual commands
		String[] commandPairs = commandsInput.replaceAll("\\s+", "").split(",");
		for (String pair : commandPairs) {
			// Split each command into "key=value"
			String[] parts = pair.split("=");
			if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
				throw new IllegalArgumentException("Invalid command format: " + pair);
			}
			char key = parts[0].charAt(0);

			TurtleCommand commandType = getTurtleCommand(parts[1].toUpperCase());

			if (commandType != null) {
				commands.putIfAbsent(key, commandType);
			}
		}
		return commands;
	}

	/**
	 * Validates that a given input string is neither null nor blank.
	 */
	private void validateInput(String input, String errorMessage) {
		if (input == null || input.isBlank()) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	/*
	 * Maps a string to a corresponding TurtleCommand enum value.
	 */
	private TurtleCommand getTurtleCommand(String command) {
		return switch (command) {
		case "MOVE" -> TurtleCommand.MOVE;
		case "TURNRIGHT" -> TurtleCommand.TURNRIGHT;
		case "TURNLEFT" -> TurtleCommand.TURNLEFT;
		case "PUSH" -> TurtleCommand.PUSH;
		case "POP" -> TurtleCommand.POP;
		default -> throw new IllegalArgumentException("Invalid command encountered: " + command);
		};
	}
}
