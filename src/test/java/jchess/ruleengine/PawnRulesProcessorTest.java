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
import jchess.gamelogic.BoardAgentFactory;
import jchess.gamelogic.MoveCandidate;
import jchess.util.AppLogger;
import jchess.util.IAppLogger;

class PawnRulesProcessorTest {
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
		
		oCacheManager.loadBoardFromFile("DefaultRuleProcessorTest", "2PlayerBoard");
		m_oBoard = oCacheManager.getBoard("DefaultRuleProcessorTest");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testTryPawnPromotionRuleForEdge_FailureCase() {
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d2");

		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		
		
		PawnRulesProcessor.tryPawnPromotionRuleForEdge(m_oBoard, oCurrentPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 0;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
	}

	@Test
	void testTryPawnPromotionRuleForEdge_SuccessCase() {
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d7");
		
		// This change is made to create a scenarios where White Pawn has reached d7 position and wants to promote itself.
		IPieceAgent oPawnPiece = m_oBoard.getPositionAgent("d2").getPiece();
		oCurrentPosition.setPiece(oPawnPiece); 
		oPawnPiece.setPosition(oCurrentPosition);

		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		
		
		PawnRulesProcessor.tryPawnPromotionRuleForEdge(m_oBoard, oCurrentPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);

		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("d8");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("d8").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryPawnPromotionRuleForVertex_FailureCase() {
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d2");

		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		
		
		PawnRulesProcessor.tryPawnPromotionRuleForVertex(m_oBoard, oCurrentPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 0;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
	}

	@Test
	void testTryPawnPromotionRuleForVertex_SuccessCase() {
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d7");
		
		// This change is made to create a scenarios where White Pawn has reached d7 position and wants to promote itself.
		IPieceAgent oPawnPiece = m_oBoard.getPositionAgent("d2").getPiece();
		oCurrentPosition.setPiece(oPawnPiece); 
		oPawnPiece.setPosition(oCurrentPosition);

		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		
		
		PawnRulesProcessor.tryPawnPromotionRuleForVertex(m_oBoard, oCurrentPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 2;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);

		String[] arPositionsToValidate = {"c8", "e8"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	@Test
	void testTryExecutePawnPromotionRule() {
		// TODO: There is a change required in the function. I made comments to it, and after only achieving the suggested modification
		// we shall be able to perform this test.
	}

	@Test
	void testTryPawnFirstMoveException() {
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d2");
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		
		
		PawnRulesProcessor.tryPawnFirstMoveException(m_oRuleProcessor, m_oBoard, oCurrentPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);

		String[] arPositionsToValidate = {"d4"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	@Test
	void testTryExecutePawnFirstMoveException() {
		IPositionAgent oSourcePosition = m_oBoard.getPositionAgent("d2");
		IPositionAgent oCandidatePosition = m_oBoard.getPositionAgent("d4");

		IRuleAgent oRule= (IRuleAgent)m_oBoardFactory.createRule();		
		oRule.getRuleData().setRuleType(RuleType.CUSTOM);
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.IGNORE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setName("PawnFirstMoveException_InnerRule");
		oRule.getRuleData().setCustomName("MOVE[PAWN_FIRST_MOVE_EXCEPTION]");
				
		IMoveCandidate oMoveCandidate = new MoveCandidate(oRule, oSourcePosition.getPiece(), oSourcePosition, oCandidatePosition);

		// Following Asset validates the existence of Pawn prior execution of the Rule.
		String oExpectedPieceString  = oSourcePosition.getPiece().getName();
		String oActualPieceString = "PawnWhite";

		assertEquals(oExpectedPieceString, oActualPieceString);

		IBoardActivity oMove = PawnRulesProcessor.tryExecutePawnFirstMoveException(m_oBoard, oMoveCandidate);

		// Following Asset validates the Pawn piece has moved after execution of the Rule.
		IPieceAgent oExpectedPiece  = oSourcePosition.getPiece();
		IPieceAgent oActualPiece = null;

		assertEquals(oExpectedPiece, oActualPiece);
	}

}
