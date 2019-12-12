package jchess.gamelogic;

import static org.junit.Assert.assertEquals;

import java.awt.Image;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jchess.common.IPieceAgent;
import jchess.common.IPieceData;


public class PieceAgentTest {
	static IPieceAgent oPieceAgent =  new PieceAgent();
	static IPieceData oPieceData;;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		oPieceData = oPieceAgent.getPieceData();
		oPieceData.setImagePath("D:\\");
		oPieceData.setName("UnitTest");
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
	void testGetImagePath()
	{
		String expected = "D:\\";
		String actual = oPieceAgent.getImagePath();
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetName()
	{
		String expected = "UnitTest";
		String actual = oPieceAgent.getName();
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetPieceData()
	{
		Object expected = oPieceData;
		Object actual = oPieceAgent.getPieceData();
		assertEquals(expected,actual);
	}
}