package jchess.service;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jchess.common.enumerator.Direction;
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
		fail("Not yet implemented");
	}

	@Test
	void testConvertStringToManoeuvre() {
		fail("Not yet implemented");
	}

	@Test
	void testConvertStringToFile() {
		fail("Not yet implemented");
	}

	@Test
	void testConvertStringToRank() {
		fail("Not yet implemented");
	}

	@Test
	void testConvertStringToFamily() {
		fail("Not yet implemented");
	}

}
