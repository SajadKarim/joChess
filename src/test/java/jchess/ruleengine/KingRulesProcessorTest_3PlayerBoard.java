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
	void testTryCastlingRule_Short_FailureCaseEndangered() {
		//make case where path is free but endangered
		IPositionAgent oKingPosition = m_oBoard.getPositionAgent("e1"); // White King
		m_oBoard.getPositionAgent("f1").setPiece(null); // Remove White Knight
		m_oBoard.getPositionAgent("g1").setPiece(null); // Remove White Horse
		IPositionAgent oPawnPosition = m_oBoard.getPositionAgent("g2"); // Remove White Pawn
		
		// This change is made to create a scenario where a red Pawn has reached position g2 and endangers the path for castling.
		IPieceAgent oPawnPiece = m_oBoard.getPositionAgent("f11").getPiece();
		oPawnPosition.setPiece(oPawnPiece); 
		oPawnPiece.setPosition(oPawnPosition);		
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		
		
		KingRulesProcessor.tryCastlingRule(m_oRuleProcessor, File.FORWARD, m_oBoard, oKingPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 0;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
	}
	
	@Test
	void testTryCastlingRule_Long_FailureCaseEndangered() {
		//make case where path is free but endangered
		IPositionAgent oKingPosition = m_oBoard.getPositionAgent("e1"); // White King
		m_oBoard.getPositionAgent("d1").setPiece(null); // Remove White Queen
		m_oBoard.getPositionAgent("c1").setPiece(null); // Remove White Knight
		m_oBoard.getPositionAgent("b1").setPiece(null); // Remove White Horse
		IPositionAgent oPawnPosition = m_oBoard.getPositionAgent("b2"); // Remove White Pawn
		
		// This change is made to create a scenario where a black Pawn has reached position b2 and endangers the path for castling.
		IPieceAgent oPawnPiece = m_oBoard.getPositionAgent("b7").getPiece();
		oPawnPosition.setPiece(oPawnPiece); 
		oPawnPiece.setPosition(oPawnPosition);		
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		
		
		KingRulesProcessor.tryCastlingRule(m_oRuleProcessor, File.BACKWARD, m_oBoard, oKingPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 0;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
	}
	
	@Test
	void testTryCastlingRule_Short_FailureCaseVacant() {
		//make case where path is free but endangered
		IPositionAgent oKingPosition = m_oBoard.getPositionAgent("e1"); // White King
		m_oBoard.getPositionAgent("f1").setPiece(null); // Remove White Knight
		IPositionAgent oPawnPosition = m_oBoard.getPositionAgent("g2"); // Remove White Pawn
				
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		
		
		KingRulesProcessor.tryCastlingRule(m_oRuleProcessor, File.FORWARD, m_oBoard, oKingPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 0;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
	}
	
	@Test
	void testTryCastlingRule_Long_FailureCaseVacant() {
		//make case where path is free but endangered
		IPositionAgent oKingPosition = m_oBoard.getPositionAgent("e1"); // White King
		m_oBoard.getPositionAgent("d1").setPiece(null); // Remove White Queen
		m_oBoard.getPositionAgent("c1").setPiece(null); // Remove White Knight
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		
		
		KingRulesProcessor.tryCastlingRule(m_oRuleProcessor, File.BACKWARD, m_oBoard, oKingPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 0;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
	}
	
	@Test
	void testTryCastlingRule_ShortSuccessCase() {
		//make case where path is free and not endangered
		IPositionAgent oKingPosition = m_oBoard.getPositionAgent("e1"); // White King
		m_oBoard.getPositionAgent("f1").setPiece(null); // Remove White Knight
		m_oBoard.getPositionAgent("g1").setPiece(null); // Remove White Horse

		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		
		
		KingRulesProcessor.tryCastlingRule(m_oRuleProcessor, File.FORWARD, m_oBoard, oKingPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
	}
	
	@Test
	void testTryCastlingRule_SuccessCase() {
		//make case where path is free and not endangered
		IPositionAgent oKingPosition = m_oBoard.getPositionAgent("e1"); // White King
		m_oBoard.getPositionAgent("d1").setPiece(null); // Remove White Queen
		m_oBoard.getPositionAgent("c1").setPiece(null); // Remove White Knight
		m_oBoard.getPositionAgent("b1").setPiece(null); // Remove White Horse
		m_oBoard.getPositionAgent("f1").setPiece(null); // Remove White Knight
		m_oBoard.getPositionAgent("g1").setPiece(null); // Remove White Horse

		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		

		KingRulesProcessor.tryCastlingRule(m_oRuleProcessor, File.BACKWARD, m_oBoard, oKingPosition.getPiece(), mpCandidatePositions);
		KingRulesProcessor.tryCastlingRule(m_oRuleProcessor, File.FORWARD, m_oBoard, oKingPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 2;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
	}

}
