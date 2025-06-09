package test.java.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import model.LSystemParser;

class LSystemParserTest {

	//tests for generateLSystemString method
	@Test
	void testGenerateLSystemString_SimpleRule() {
		LSystemParser parser = new LSystemParser("A", "A→AB", 3);
		String result = parser.generateLSystemString();
		assertEquals("ABBB", result);
	}

	@Test
	void testGenerateLSystemString_NoMatchingRule() {
		LSystemParser parser = new LSystemParser("X", "A→AB", 2);
		String result = parser.generateLSystemString();
		assertEquals("X", result);
	}

	@Test
	void testGenerateLSystemString_ZeroIterations() {
		LSystemParser parser = new LSystemParser("F", "F→FF", 0);
		String result = parser.generateLSystemString();
		assertEquals("F", result);
	}

	@Test
	void testGenerateLSystemString_ComplexRules() {
		LSystemParser parser = new LSystemParser("A", "A→AB,B→BA", 3);
		String result = parser.generateLSystemString();
		assertEquals("ABBABAAB", result); 
	}

	@Test
	void testGenerateLSystemString_BlankAxiom() {
	    LSystemParser parser = new LSystemParser("   ", "A→AB", 1);
	    assertThrows(IllegalArgumentException.class, parser::generateLSystemString);
	}
	@Test
	void testGenerateLSystemString_NullAxiom() {
	    LSystemParser parser = new LSystemParser(null, "A→AB", 1);
	    assertThrows(IllegalArgumentException.class, parser::generateLSystemString);
	}
	
	//tests for parseRules method
	@Test
	void testParseRules_ValidSingleRule() {
	    LSystemParser parser = new LSystemParser("A", "A→AB", 2);
	    Map<Character, String> rules = parser.parseRules("A→AB");
	    assertEquals(1, rules.size());
	    assertEquals("AB", rules.get('A'));
	}
	@Test
	void testParseRules_ValidMultipleRules() {
	    LSystemParser parser = new LSystemParser("A", "A→AB, B→A", 1);
	    Map<Character, String> rules = parser.parseRules("A→AB, B→A");
	    assertEquals(2, rules.size());
	    assertEquals("AB", rules.get('A'));
	    assertEquals("A", rules.get('B'));
	}
	@Test
	void testParseRules_InvalidFormatMissingArrow() {
	    LSystemParser parser = new LSystemParser("A", "A→B", 1);
	    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	        parser.parseRules("A-B");
	    });
	    assertTrue(exception.getMessage().contains("Invalid rule format"));
	}
	@Test
	void testParseRules_EmptyRuleValue() {
	    LSystemParser parser = new LSystemParser("A", "A→B", 1);
	    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	        parser.parseRules("A→");
	    });
	    assertTrue(exception.getMessage().contains("Invalid rule format"));
	}
	@Test
	void testParseRules_DuplicateRuleShouldNotOverwrite() {
	    LSystemParser parser = new LSystemParser("A", "A→AB,B→A,A→XX", 1);
	    Map<Character, String> rules = parser.parseRules("A→AB,B→A,A→XX");
	    assertEquals("AB", rules.get('A')); 
	}
	@Test
	void testParseRules_EmptyInput() {
	    LSystemParser parser = new LSystemParser("A", "A→B", 1);
	    assertThrows(IllegalArgumentException.class, () -> parser.parseRules("  "));
	}
	@Test
	void testParseRules_NullInput() {
	    LSystemParser parser = new LSystemParser("A", "A→B", 1);
	    assertThrows(IllegalArgumentException.class, () -> parser.parseRules(null));
	}
}
