package jchess.ruleengine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jchess.cache.CacheManager;
import jchess.cache.ICacheManager;
import jchess.common.IBoardAgent;
import jchess.common.IBoardFactory;
import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;
import jchess.common.IPositionAgent;
import jchess.gamelogic.BoardAgentFactory;
import jchess.util.AppLogger;
import jchess.util.IAppLogger;

class ThreePlayersHexBoardRuleProcessorTest {
	static IBoardAgent m_oBoard;
	static DefaultRuleProcessor m_oRuleProcessor;
	static IBoardFactory m_oBoardFactory = new BoardAgentFactory();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		IAppLogger oLogger = new AppLogger();
		ICacheManager oCacheManager = new CacheManager(oLogger);
		
		oCacheManager.loadBoardFromFile("ThreePlayersHexBoardRuleProcessorTest", "3PlayerBoard.xml");
		m_oBoard = oCacheManager.getBoard("ThreePlayersHexBoardRuleProcessorTest");

		m_oRuleProcessor = new ThreePlayersHexBoardRuleProcessor(oLogger);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testTryEvaluateAllRules_CannonPieceAtA2_EmptyBoard() {
		for (Map.Entry<String,IPositionAgent> entry : m_oBoard.getAllPositionAgents().entrySet()) {
    		IPositionAgent oPosition = entry.getValue();
    		if( !oPosition.getName().equals("a2") && oPosition.getPiece() != null) {
    			oPosition.setPiece(null);
    		}
    	}

		String[] arrExpectedPositionIds = {"a6", "b5", "c4", "d3", "e2", "f1", "a5", "b4", "c3", "d2", "e1"};
		
		IPieceAgent oPiece = ((IPositionAgent)m_oBoard.getPosition("a2")).getPiece();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		m_oRuleProcessor.tryEvaluateAllRules(m_oBoard, oPiece, mpCandidatePositions);
		
		assertEquals(mpCandidatePositions.size(), arrExpectedPositionIds.length);

		for( String stPositionId : arrExpectedPositionIds) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	@Test
	void testTryEvaluateAllRules_CannonPieceAtH2_EmptyBoard() {
		for (Map.Entry<String,IPositionAgent> entry : m_oBoard.getAllPositionAgents().entrySet()) {
    		IPositionAgent oPosition = entry.getValue();
    		if( !oPosition.getName().equals("h2") && oPosition.getPiece() != null) {
    			oPosition.setPiece(null);
    		}
    	}

		String[] arrExpectedPositionIds = {"h10", "g9", "f4", "e3", "d2", "c1", "h9", "g4", "f3", "e2", "d1"};
		
		IPieceAgent oPiece = ((IPositionAgent)m_oBoard.getPosition("h2")).getPiece();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		m_oRuleProcessor.tryEvaluateAllRules(m_oBoard, oPiece, mpCandidatePositions);
		
		assertEquals(mpCandidatePositions.size(), arrExpectedPositionIds.length);

		for( String stPositionId : arrExpectedPositionIds) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	@Test
	void testTryEvaluateAllRules_CannonPieceAtA2() {
		String[] arrExpectedPositionIds = {"a6", "b5", "c4", "d3", "a5", "b4", "c3" };
		
		IPieceAgent oPiece = ((IPositionAgent)m_oBoard.getPosition("a2")).getPiece();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		m_oRuleProcessor.tryEvaluateAllRules(m_oBoard, oPiece, mpCandidatePositions);
		
		assertEquals(mpCandidatePositions.size(), arrExpectedPositionIds.length);

		for( String stPositionId : arrExpectedPositionIds) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	@Test
	void testTryEvaluateAllRules_CannonPieceAtH2() {
		String[] arrExpectedPositionIds = {"h10", "g9", "f4", "e3", "h9", "g4", "f3"};
		
		IPieceAgent oPiece = ((IPositionAgent)m_oBoard.getPosition("h2")).getPiece();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		m_oRuleProcessor.tryEvaluateAllRules(m_oBoard, oPiece, mpCandidatePositions);
		
		assertEquals(mpCandidatePositions.size(), arrExpectedPositionIds.length);

		for( String stPositionId : arrExpectedPositionIds) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}
}
