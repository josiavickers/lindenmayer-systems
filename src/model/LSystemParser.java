/*
 *  @author: Eliyas Tadesse 
 */
package model;

import java.util.HashMap;
import java.util.Map;

public class LSystemParser {

	private int iterations;
	private String axiom;
	private Map<Character, String> rules;

	public LSystemParser(String axiom, String rulesInput, int iterations) {
		this.iterations = iterations;
		this.rules = parseRules(rulesInput);
		this.axiom = axiom;

	}

	public String generateLSystemString() {
		validateInput(axiom, "Axiom input cannot be null or empty.");
		String result = axiom;
		for (int i = 0; i < iterations; i++) {
			StringBuilder newResult = new StringBuilder(result.length());
			for (char c : result.toCharArray()) {
				newResult.append(rules.getOrDefault(c, String.valueOf(c)));
			}
			result = newResult.toString();
		}

		return result;
	}

	private Map<Character, String> parseRules(String rulesInput) {
		validateInput(rulesInput, "Rules input cannot be null or empty.");
		Map<Character, String> rules = new HashMap<>();
		// Remove whiteSpace and Split into individual rules
		String[] rulePairs = rulesInput.replaceAll("\\s+", "").split(",");
		for (String pair : rulePairs) {
			// Split each rule into "key->value"
			String[] parts = pair.split("->");
			if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
				throw new IllegalArgumentException("Invalid rule format: " + pair);
			}
			char key = parts[0].charAt(0);
			rules.putIfAbsent(key, parts[1]);
		}
		return rules;
	}

	public Map<Character, TurtleCommand> parseCommands(String commandsInput) {
		validateInput(commandsInput, "Commands input cannot be null or empty.");

		Map<Character, TurtleCommand> commands = new HashMap<>();
		// Remove whiteSpace and Split into individual commands
		String[] commandPairs = commandsInput.replaceAll("\\s+", "").split(",");
		for (String pair : commandPairs) {
			// Split each command into "key:value"
			String[] parts = pair.split(":");
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

	private void validateInput(String input, String errorMessage) {
		if (input == null || input.isBlank()) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

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
