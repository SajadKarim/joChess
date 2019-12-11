package jchess.gamelogic;

import static org.junit.Assert.assertEquals;

import java.awt.Image;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jchess.common.IBoardAgent;
import jchess.common.IBoardData;

public class BoardAgentTest {
	static IBoardAgent oBoardAgent = new BoardAgent();
	static IBoardData oBoardData;;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		oBoardData = oBoardAgent.getBoardData();
		oBoardData.setBoardHeight(100);
		oBoardData.setBoardWidth(100);
		oBoardData.setName("UnitTest");		
		
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
	void testGetBoardHeighth()
	{
		int expected = 100;
		int actual = oBoardAgent.getBoardHeight();
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetBoardWidth()
	{
		int expected = 100;
		int actual = oBoardAgent.getBoardWidth();
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetBoardName()
	{
		String expected = "UnitTest";
		String actual = oBoardAgent.getName();
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetBoardData()
	{
		Object expected = oBoardData;
		Object actual = oBoardAgent.getBoardData();
		assertEquals(expected,actual);
	}

}
