package jchess.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

import org.junit.Assert;

class StringToEnumTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
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

	/**
	 * Following unit test ensures the successful conversion of String to Direction enumerator.
	 */
	@Test
	void testConvertStringToDirection() {
		Direction expected = Direction.EDGE;		
		Direction actual = StringToEnum.convertStringToDirection("edge");		
		Assert.assertEquals(expected, actual);

		expected = Direction.EDGE;		
		actual = StringToEnum.convertStringToDirection("EDGE");		
		Assert.assertEquals(expected, actual);

		expected = Direction.VERTEX;		
		actual = StringToEnum.convertStringToDirection("vertex");		
		Assert.assertEquals(expected, actual);

		expected = Direction.VERTEX;		
		actual = StringToEnum.convertStringToDirection("VERTEX");		
		Assert.assertEquals(expected, actual);

		expected = null;		
		actual = StringToEnum.convertStringToDirection("");		
		Assert.assertEquals(expected, actual);
	}

	@Test
	void testConvertStringToRuleType() {
		RuleType expected = RuleType.MOVE;
		RuleType actual = StringToEnum.convertStringToRuleType("move");
		Assert.assertEquals(expected, actual);
		
		expected = RuleType.MOVE;
		actual = StringToEnum.convertStringToRuleType("MOVE");
		Assert.assertEquals(expected, actual);
		
		expected = RuleType.MOVE_AND_CAPTURE;
		actual = StringToEnum.convertStringToRuleType("move_and_capture");
		Assert.assertEquals(expected, actual);
		
		expected = RuleType.MOVE_AND_CAPTURE;
		actual = StringToEnum.convertStringToRuleType("MOVE_AND_CAPTURE");
		Assert.assertEquals(expected, actual);
		
		expected = RuleType.MOVE_IFF_CAPTURE_POSSIBLE;
		actual = StringToEnum.convertStringToRuleType("move_iff_capture_possible");
		Assert.assertEquals(expected, actual);
		
		expected = RuleType.MOVE_IFF_CAPTURE_POSSIBLE;
		actual = StringToEnum.convertStringToRuleType("MOVE_IFF_CAPTURE_POSSIBLE");
		Assert.assertEquals(expected, actual);
		
		expected = RuleType.MOVE_TRANSIENT;
		actual = StringToEnum.convertStringToRuleType("move_transient");
		Assert.assertEquals(expected, actual);
		
		expected = RuleType.MOVE_TRANSIENT;
		actual = StringToEnum.convertStringToRuleType("MOVE_TRANSIENT");
		Assert.assertEquals(expected, actual);
		
		expected = null;		
		actual = StringToEnum.convertStringToRuleType("");		
		Assert.assertEquals(expected, actual);
	}

	@Test
	void testConvertStringToManoeuvre() {
		Manoeuvre expected = Manoeuvre.BLINKER;		
		Manoeuvre actual = StringToEnum.convertStringToManoeuvre("blinker");		
		Assert.assertEquals(expected, actual);

		expected = Manoeuvre.BLINKER;		
		actual = StringToEnum.convertStringToManoeuvre("BLINKER");		
		Assert.assertEquals(expected, actual);

		expected = Manoeuvre.BLINKER_WITH_FILE_AND_RANK;		
		actual = StringToEnum.convertStringToManoeuvre("blinker_with_file_and_rank");		
		Assert.assertEquals(expected, actual);

		expected = Manoeuvre.BLINKER_WITH_FILE_AND_RANK;		
		actual = StringToEnum.convertStringToManoeuvre("BLINKER_WITH_FILE_AND_RANK");		
		Assert.assertEquals(expected, actual);

		expected = null;		
		actual = StringToEnum.convertStringToManoeuvre("");		
		Assert.assertEquals(expected, actual);
	}

	@Test
	void testConvertStringToFile() {
		File expected = File.BACKWARD;		
		File actual = StringToEnum.convertStringToFile("backward");		
		Assert.assertEquals(expected, actual);
		
		expected = File.BACKWARD;		
		actual = StringToEnum.convertStringToFile("BACKWARD");		
		Assert.assertEquals(expected, actual);
		
		expected = File.FORWARD;		
		actual = StringToEnum.convertStringToFile("forward");		
		Assert.assertEquals(expected, actual);
		expected = File.FORWARD;		
		actual = StringToEnum.convertStringToFile("FORWARD");		
		Assert.assertEquals(expected, actual);
		
		expected = File.SAME;		
		actual = StringToEnum.convertStringToFile("same");		
		Assert.assertEquals(expected, actual);
		expected = File.SAME;		
		actual = StringToEnum.convertStringToFile("SAME");		
		Assert.assertEquals(expected, actual);
		
		expected = File.IGNORE;		
		actual = StringToEnum.convertStringToFile("ignore");		
		Assert.assertEquals(expected, actual);
		expected = File.IGNORE;		
		actual = StringToEnum.convertStringToFile("IGNORE");		
		Assert.assertEquals(expected, actual);
		
	}

	@Test
	void testConvertStringToRank() {
		Rank expected = Rank.BACKWARD;		
		Rank actual = StringToEnum.convertStringToRank("backward");		
		Assert.assertEquals(expected, actual);
	
		expected = Rank.BACKWARD;		
		actual = StringToEnum.convertStringToRank("BACKWARD");		
		Assert.assertEquals(expected, actual);
		
		expected = Rank.FORWARD;		
		actual = StringToEnum.convertStringToRank("forward");		
		Assert.assertEquals(expected, actual);
		
		expected = Rank.FORWARD;		
		actual = StringToEnum.convertStringToRank("FORWARD");		
		Assert.assertEquals(expected, actual);
		
		expected = Rank.SAME;		
		actual = StringToEnum.convertStringToRank("same");		
		Assert.assertEquals(expected, actual);
		
		expected = Rank.SAME;		
		actual = StringToEnum.convertStringToRank("SAME");		
		Assert.assertEquals(expected, actual);
		
		expected = Rank.IGNORE;		
		actual = StringToEnum.convertStringToRank("ignore");		
		Assert.assertEquals(expected, actual);
		
		expected = Rank.IGNORE;		
		actual = StringToEnum.convertStringToRank("IGNORE");		
		Assert.assertEquals(expected, actual);
	}

	@Test
	void testConvertStringToFamily() {
		Family expected = Family.DIFFERENT;		
		Family actual = StringToEnum.convertStringToFamily("different");		
		Assert.assertEquals(expected, actual);
		
		expected = Family.DIFFERENT;		
		actual = StringToEnum.convertStringToFamily("DIFFERENT");		
		Assert.assertEquals(expected, actual);
		
		expected = Family.PROVIDED;		
		actual = StringToEnum.convertStringToFamily("provided");		
		Assert.assertEquals(expected, actual);
		
		expected = Family.PROVIDED;		
		actual = StringToEnum.convertStringToFamily("PROVIDED");		
		Assert.assertEquals(expected, actual);
		
		expected = Family.SAME;		
		actual = StringToEnum.convertStringToFamily("same");		
		Assert.assertEquals(expected, actual);
		
		expected = Family.SAME;		
		actual = StringToEnum.convertStringToFamily("SAME");		
		Assert.assertEquals(expected, actual);
		
		expected = Family.IGNORE;		
		actual = StringToEnum.convertStringToFamily("ignore");		
		Assert.assertEquals(expected, actual);
	
		expected = Family.IGNORE;		
		actual = StringToEnum.convertStringToFamily("IGNORE");		
		Assert.assertEquals(expected, actual);
	}

}