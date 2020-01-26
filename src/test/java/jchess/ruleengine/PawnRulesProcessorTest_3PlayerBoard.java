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

class PawnRulesProcessorTest_3PlayerBoard {
	static IAppLogger m_oLogger;
	static IBoardAgent m_oBoard;
	static IRuleProcessor m_oRuleProcessor;
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
		
		oCacheManager.loadBoardFromFile("DefaultRuleProcessorTest", "3PlayerBoard.xml");
		m_oBoard = oCacheManager.getBoard("DefaultRuleProcessorTest");
	}

	@AfterEach
	void tearDown() throws Exception {
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
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("c7");
		
		// This change is made to create a scenarios where White Pawn has reached d7 position and wants to promote itself.
		IPieceAgent oPawnPiece = m_oBoard.getPositionAgent("c2").getPiece();
		oCurrentPosition.setPiece(oPawnPiece); 
		oPawnPiece.setPosition(oCurrentPosition);

		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();		
		
		PawnRulesProcessor.tryPawnPromotionRuleForVertex(m_oBoard, oCurrentPosition.getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 2;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);

		String[] arPositionsToValidate = {"b8", "d8"};
		for (String stPositionId : arPositionsToValidate) {
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
		for (String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	@Test
	void testTryExecutePawnFirstMoveException() {
		IPositionAgent oSourcePosition = m_oBoard.getPositionAgent("d2");
		IPositionAgent oCandidatePosition = m_oBoard.getPositionAgent("d4");

		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();		
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
	
	@Test
	void testtryPawnEnPassantRule() {
		// Player 1 move Pawn from b2 to b4
		makeMoveToTesttryPawnEnPassantRule((IRuleAgent)m_oBoard.getPositionAgent("b2").getPiece().getRule("Pawn_Move"), m_oBoard.getPositionAgent("b2").getPiece(), m_oBoard.getPositionAgent("b2"), m_oBoard.getPositionAgent("b4"));
		// Player 2 move Pawn from g11 to g10
		makeMoveToTesttryPawnEnPassantRule((IRuleAgent)m_oBoard.getPositionAgent("g11").getPiece().getRule("Pawn_Move"), m_oBoard.getPositionAgent("g11").getPiece(), m_oBoard.getPositionAgent("g11"), m_oBoard.getPositionAgent("g10"));
		// Player 3 move Pawn from k7 to k6
		makeMoveToTesttryPawnEnPassantRule((IRuleAgent)m_oBoard.getPositionAgent("k7").getPiece().getRule("Pawn_Move"), m_oBoard.getPositionAgent("k7").getPiece(), m_oBoard.getPositionAgent("k7"), m_oBoard.getPositionAgent("k6"));
		// Player 1 move Pawn from b4 to b5
		makeMoveToTesttryPawnEnPassantRule((IRuleAgent)m_oBoard.getPositionAgent("b4").getPiece().getRule("Pawn_Move"), m_oBoard.getPositionAgent("b4").getPiece(), m_oBoard.getPositionAgent("b4"), m_oBoard.getPositionAgent("b5"));
		// Player 2 move Pawn from c7 to c5
		makeMoveToTesttryPawnEnPassantRule((IRuleAgent)m_oBoard.getPositionAgent("c7").getPiece().getRule("Pawn_Move"), m_oBoard.getPositionAgent("c7").getPiece(), m_oBoard.getPositionAgent("c7"), m_oBoard.getPositionAgent("c5"));
		// Player 3 move Pawn from e11 to e10
		makeMoveToTesttryPawnEnPassantRule((IRuleAgent)m_oBoard.getPositionAgent("e11").getPiece().getRule("Pawn_Move"), m_oBoard.getPositionAgent("e11").getPiece(), m_oBoard.getPositionAgent("e11"), m_oBoard.getPositionAgent("e10"));

		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		PawnRulesProcessor.tryPawnEnPassantRule(m_oBoard, m_oBoard.getPositionAgent("b5").getPiece(), mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);

		String[] arPositionsToValidate = {"c5"};
		for (String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}
	
	private void makeMoveToTesttryPawnEnPassantRule(IRuleAgent oRule, IPieceAgent oPieceToMove, IPositionAgent oCurrentPosition, IPositionAgent oNewPosition) {
		IMoveCandidate oMoveCandidate = new MoveCandidate(oRule, oPieceToMove, oCurrentPosition, oNewPosition);

		oCurrentPosition.setPiece(null);
		oNewPosition.setPiece(oPieceToMove);
		oPieceToMove.setPosition(oNewPosition);
		
		IBoardActivity oActivity = new BoardActivity(oMoveCandidate);
		oActivity.addPriorMoveEntry(oCurrentPosition, oPieceToMove);
		oActivity.addPriorMoveEntry(oNewPosition, null);
		oActivity.addPostMoveEntry(oCurrentPosition, null);
		oActivity.addPostMoveEntry(oNewPosition, oPieceToMove);
		oActivity.setPlayer(oPieceToMove.getPlayer());

		m_oBoard.addActivity(oActivity);		
	}

	@Test
	void testtryExecutePawnEnPassantRule() {
		// Player 1 move Pawn from b2 to b4
		makeMoveToTesttryPawnEnPassantRule((IRuleAgent)m_oBoard.getPositionAgent("b2").getPiece().getRule("Pawn_Move"), m_oBoard.getPositionAgent("b2").getPiece(), m_oBoard.getPositionAgent("b2"), m_oBoard.getPositionAgent("b4"));
		// Player 2 move Pawn from g11 to g10
		makeMoveToTesttryPawnEnPassantRule((IRuleAgent)m_oBoard.getPositionAgent("g11").getPiece().getRule("Pawn_Move"), m_oBoard.getPositionAgent("g11").getPiece(), m_oBoard.getPositionAgent("g11"), m_oBoard.getPositionAgent("g10"));
		// Player 3 move Pawn from k7 to k6
		makeMoveToTesttryPawnEnPassantRule((IRuleAgent)m_oBoard.getPositionAgent("k7").getPiece().getRule("Pawn_Move"), m_oBoard.getPositionAgent("k7").getPiece(), m_oBoard.getPositionAgent("k7"), m_oBoard.getPositionAgent("k6"));
		// Player 1 move Pawn from b4 to b5
		makeMoveToTesttryPawnEnPassantRule((IRuleAgent)m_oBoard.getPositionAgent("b4").getPiece().getRule("Pawn_Move"), m_oBoard.getPositionAgent("b4").getPiece(), m_oBoard.getPositionAgent("b4"), m_oBoard.getPositionAgent("b5"));
		// Player 2 move Pawn from c7 to c5
		makeMoveToTesttryPawnEnPassantRule((IRuleAgent)m_oBoard.getPositionAgent("c7").getPiece().getRule("Pawn_Move"), m_oBoard.getPositionAgent("c7").getPiece(), m_oBoard.getPositionAgent("c7"), m_oBoard.getPositionAgent("c5"));
		// Player 3 move Pawn from e11 to e10
		makeMoveToTesttryPawnEnPassantRule((IRuleAgent)m_oBoard.getPositionAgent("e11").getPiece().getRule("Pawn_Move"), m_oBoard.getPositionAgent("e11").getPiece(), m_oBoard.getPositionAgent("e11"), m_oBoard.getPositionAgent("e10"));

		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		PawnRulesProcessor.tryPawnEnPassantRule(m_oBoard, m_oBoard.getPositionAgent("b5").getPiece(), mpCandidatePositions);

		IBoardActivity oBoardActivity = PawnRulesProcessor.tryExecutePawnEnPassantRule(m_oBoard, mpCandidatePositions.get("c5"));

		IPieceAgent oPieceToMove = oBoardActivity.getPriorMoveDetails().get(m_oBoard.getPositionAgent("b5"));
		IPositionAgent oExpectedPosition = m_oBoard.getPositionAgent("c5");
		IPositionAgent oActualPosition = oPieceToMove.getPosition();
		
		assertEquals(oExpectedPosition, oActualPosition);

		IPieceAgent oPieceToCapture = oBoardActivity.getPriorMoveDetails().get(m_oBoard.getPositionAgent("c5"));
		oExpectedPosition = null;
		oActualPosition = oPieceToCapture.getPosition();
		
		assertEquals(oExpectedPosition, oActualPosition);
}
}
