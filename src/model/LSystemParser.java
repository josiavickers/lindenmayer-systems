/*
 *  @author: Eliyas Tadesse 
 */
package model;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class LSystemParser {

	private int iterations;
	private String axiom;
	private Hashtable<Character, String> rules;

	public LSystemParser(String axiom, String rulesInput, int iterations) {
		this.iterations = iterations;
		this.rules = parseRules(rulesInput);
		this.axiom = axiom;

	}

	public String generateLSystemString() {
		String result = axiom;
		for (int i = 0; i < iterations; i++) {
			StringBuilder newResult = new StringBuilder();
			for (char c : result.toCharArray()) {
				newResult.append(rules.getOrDefault(c, String.valueOf(c)));
			}
			result = newResult.toString();
		}

		return result;
	}

	private Hashtable<Character, String> parseRules(String rulesInput) {
		Hashtable<Character, String> rules = new Hashtable<>();
		String str = rulesInput.replaceAll(" ", ""); // Remove whiteSpace
		String[] rulePairs = str.split(","); // Split into individual rules
		for (String pair : rulePairs) {
			// Split each rule into "key=value"
			String[] parts = pair.split("->");
			if (parts.length != 2) {
				// Skip invalid rules (e.g., missing "=")
				System.err.println("Invalid rule: " + pair);
				continue;
			}
			char key = parts[0].charAt(0);
			String value = parts[1];
			rules.put(key, value);
		}
		return rules;
	}

	public Map<Character, TurtleCommand> parseCommands(String commandsInput) {
		Map<Character, TurtleCommand> commandMap = new HashMap<>();
		String str = commandsInput.replaceAll(" ", "");
		String[] pairs = str.split(", ");
		for (String pair : pairs) {
			String[] parts = pair.split(":");
			if (parts.length != 2)
				continue;

			char key = parts[0].charAt(0);
			String cmdParts = parts[1];

			TurtleCommand cmdType = switch (cmdParts.toUpperCase()) {
			case "MOVE" -> TurtleCommand.MOVE;
			case "TURNRIGHT" -> TurtleCommand.TURNRIGHT;
			case "TURNLEFT" -> TurtleCommand.TURNLEFT;
			case "SAVE" -> TurtleCommand.SAVE;
			case "REMOVE" -> TurtleCommand.REMOVE;
			default -> null;
			};

			if (cmdType != null) {
				commandMap.put(key, cmdType);
			}
		}
		return commandMap;
	}
}
