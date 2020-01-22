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
import jchess.common.IBoardActivity;
import jchess.common.IBoardAgent;
import jchess.common.IBoardFactory;
import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;
import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;
import jchess.gamelogic.BoardActivity;
import jchess.gamelogic.BoardAgentFactory;
import jchess.gamelogic.MoveCandidate;
import jchess.util.AppLogger;
import jchess.util.IAppLogger;

class KingRulesProcessorTest_3PlayerBoard {
	static IAppLogger m_oLogger;
	static IBoardAgent m_oBoard;
	static IRuleProcessor m_oRuleProcessor ;
	private static final IBoardFactory m_oBoardFactory = new BoardAgentFactory();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		m_oLogger = new AppLogger();
		m_oRuleProcessor = new ExtendedRuleProcessor(m_oLogger);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ICacheManager oCacheManager = new CacheManager(m_oLogger);
		
		oCacheManager.loadBoardFromFile("KingRulesProcessorTest", "3PlayerBoard.xml");
		m_oBoard = oCacheManager.getBoard("KingRulesProcessorTest");
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testTryShortCastlingRule_FailureCaseEndangered() {
		//make case where path is free but endangered
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("e1"); // White King
		m_oBoard.getPositionAgent("f1").setPiece(null); // Remove White Knight
		m_oBoard.getPositionAgent("g1").setPiece(null); // Remove White Horse
		IPositionAgent oPawnPosition = m_oBoard.getPositionAgent("g2"); // Remove White Pawn
		
		// This change is made to create a scenario where a red Pawn has reached g2 position and endangers the path for castling.
		IPieceAgent oPawnPiece = m_oBoard.getPositionAgent("f11").getPiece();
		oPawnPosition.setPiece(oPawnPiece); 
		oPawnPiece.setPosition(oPawnPosition);
		
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		
		
		KingRulesProcessor.tryShortCastlingRule(m_oBoard, oCurrentPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 0;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
	}
	
	@Test
	void testTryShortCastlingRule_FailureCaseVacant() {
		//make case where path is free but endangered
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("e1"); // White King
		m_oBoard.getPositionAgent("f1").setPiece(null); // Remove White Knight
		IPositionAgent oPawnPosition = m_oBoard.getPositionAgent("g2"); // Remove White Pawn
				
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		
		
		KingRulesProcessor.tryShortCastlingRule(m_oBoard, oCurrentPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 0;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
	}
	@Test
	void testTryShortCastlingRule_SuccessCase() {
		//make case where path is free and not endangered
	}

}
