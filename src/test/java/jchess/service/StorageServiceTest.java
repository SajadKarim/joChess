package jchess.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jchess.util.AppLogger;
import jchess.util.IAppLogger;

class StorageServiceTest {

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

	@Test
	void testCreate() {
		IAppLogger oLogger = new AppLogger();
		
		Object actual = StorageService.create(StorageType.FBDB, oLogger).getClass();
		Object expected = FBDBService.class;
		assertEquals(expected, actual);
	}

}
