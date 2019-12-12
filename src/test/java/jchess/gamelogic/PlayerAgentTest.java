package jchess.gamelogic;

import static org.junit.Assert.assertEquals;

import java.awt.Image;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jchess.common.IPlayerAgent;
import jchess.common.IPlayerData;



class PlayerAgentTest {
	static IPlayerAgent oPlayerAgent =  new PlayerAgent();
	static IPlayerData oPlayerData;;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		oPlayerData = oPlayerAgent.getPlayerData();
		oPlayerData.setName("UnitTest");
		oPlayerAgent.setFirstName("Test");
		oPlayerAgent.setLastName("Unit");
		Image oImage = null;
		oPlayerAgent.setImage(oImage);
		
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
	void testGetName()
	{
		String expected = "UnitTest";
		String actual = oPlayerAgent.getName();
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetFirstName()
	{
		String expected = "Test";
		String actual = oPlayerAgent.getFirstName();
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetLastName()
	{
		String expected = "Unit";
		String actual = oPlayerAgent.getLastName();
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetPlayerData()
	{
		Object expected = oPlayerData;
		Object actual = oPlayerAgent.getPlayerData();
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetImage()
	{
		Image expected = null;
		Image actual = oPlayerAgent.getImage();
		assertEquals(expected,actual);
	}
}