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

class RuleAgentCloneTest {
	static IRuleAgent oRuleAgent = new RuleAgent();
	static IRuleAgent oRuleAgentClone;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		IRuleData oRuleData = oRuleAgent.getRuleData();
		oRuleData.setDirection(Direction.EDGE);
		oRuleData.setFamily(Family.DIFFERENT);
		oRuleData.setFile(File.BACKWARD);
		oRuleData.setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRuleData.setMaxRecurrenceCount(100);
		oRuleData.setName("UnitTest");
		oRuleData.setRank(Rank.BACKWARD);
		oRuleData.setRuleType(RuleType.MOVE);
		oRuleAgent.reset();
		
		oRuleAgentClone = oRuleAgent.clone();
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
		Object expected = oRuleAgent.getName();
		Object actual = oRuleAgentClone.getName();
		assertEquals(expected, actual);
	}

	@Test
	void testGetRuleType() {
		Object expected = oRuleAgent.getRuleType();
		Object actual = oRuleAgentClone.getRuleType();
		assertEquals(expected, actual);
	}

	@Test
	void testGetDirection() {
		Object expected = oRuleAgent.getDirection();
		Object actual = oRuleAgentClone.getDirection();
		assertEquals(expected, actual);
	}

	@Test
	void testGetManoeuvreStrategy() {
		Object expected = oRuleAgent.getManoeuvreStrategy();
		Object actual = oRuleAgentClone.getManoeuvreStrategy();
		assertEquals(expected, actual);
	}

	@Test
	void testGetMaxRecurrenceCount() {
		Object expected = oRuleAgent.getMaxRecurrenceCount();
		Object actual = oRuleAgentClone.getMaxRecurrenceCount();
		assertEquals(expected, actual);
	}

	@Test
	void testGetFamily() {
		Object expected = oRuleAgent.getFamily();
		Object actual = oRuleAgentClone.getFamily();
		assertEquals(expected, actual);
	}

	@Test
	void testGetFile() {
		Object expected = oRuleAgent.getFile();
		Object actual = oRuleAgentClone.getFile();
		assertEquals(expected, actual);
	}

	@Test
	void testGetRank() {
		Object expected = oRuleAgent.getRank();
		Object actual = oRuleAgentClone.getRank();
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllRules() {
		Object expected = oRuleAgent.getAllRules();
		Object actual = oRuleAgentClone.getAllRules();
		assertEquals(expected, actual);
	}

	@Test
	void testGetRuleData() {
		Object expected = oRuleAgent.getRuleData();
		Object actual = oRuleAgentClone.getRuleData();
		assertNotEquals(expected, actual);
	}

	@Test
	void testGetNextRule() {
		Object expected = oRuleAgent.getNextRule();
		Object actual = oRuleAgentClone.getNextRule();
		assertNotEquals(expected, actual);
	}
}
