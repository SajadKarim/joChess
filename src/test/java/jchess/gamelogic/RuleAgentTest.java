package jchess.gamelogic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jchess.common.IRuleAgent;
import jchess.common.IRuleData;
import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

class RuleAgentTest {
	static IRuleAgent oRuleAgent = new RuleAgent();
	static IRuleData oRuleData;;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		oRuleData = oRuleAgent.getRuleData();
		oRuleData .setDirection(Direction.EDGE);
		oRuleData .setFamily(Family.DIFFERENT);
		oRuleData .setFile(File.BACKWARD);
		oRuleData .setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRuleData .setMaxRecurrenceCount(100);
		oRuleData .setName("UnitTest");
		oRuleData .setRank(Rank.BACKWARD);
		oRuleData .setRuleType(RuleType.MOVE);
		oRuleAgent.reset();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetName() {
		String expected = "UnitTest";
		String actual = oRuleAgent.getName();
		assertEquals(expected, actual);
	}

	@Test
	void testGetRuleType() {
		RuleType expected = RuleType.MOVE;
		RuleType actual = oRuleAgent.getRuleType();
		assertEquals(expected, actual);
	}

	@Test
	void testGetDirection() {
		Direction expected = Direction.EDGE;
		Direction actual = oRuleAgent.getDirection();
		assertEquals(expected, actual);
	}

	@Test
	void testGetManoeuvreStrategy() {
		Manoeuvre expected = Manoeuvre.BLINKER;
		Manoeuvre actual = oRuleAgent.getManoeuvreStrategy();
		assertEquals(expected, actual);
	}

	@Test
	void testGetMaxRecurrenceCount() {
		int expected = 100;
		int actual = oRuleAgent.getMaxRecurrenceCount();
		assertEquals(expected, actual);
	}

	@Test
	void testGetFamily() {
		Family expected = Family.DIFFERENT;
		Family actual = oRuleAgent.getFamily();
		assertEquals(expected, actual);
	}

	@Test
	void testGetFile() {
		File expected = File.BACKWARD;
		File actual = oRuleAgent.getFile();
		assertEquals(expected, actual);
	}

	@Test
	void testGetRank() {
		Rank expected = Rank.BACKWARD;
		Rank actual = oRuleAgent.getRank();
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllRules() {
		int expected = 0;
		int actual = oRuleAgent.getAllRules().size();
		assertEquals(expected, actual);
	}

	@Test
	void testGetRuleData() {
		Object expected = oRuleData;
		Object actual = oRuleAgent.getRuleData();
		assertEquals(expected, actual);
	}

	@Test
	void testGetNextRule() {
		Object expected = oRuleAgent;
		Object actual = oRuleAgent.getNextRule();
		assertEquals(expected, actual);
	}

	@Test
	void testMakeRuleDead() {
		oRuleAgent.makeRuleDead();
		
		Object expected = null;
		Object actual = null;
		assertEquals(expected, actual);
	}

	@Test
	void testReset() {
		testMakeRuleDead();
		
		oRuleAgent.reset();
		
		testGetNextRule();
	}
}
