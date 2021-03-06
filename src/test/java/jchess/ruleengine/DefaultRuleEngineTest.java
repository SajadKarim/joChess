package jchess.ruleengine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jchess.cache.CacheManager;
import jchess.common.IBoardActivity;
import jchess.common.IBoardAgent;
import jchess.common.IBoardFactory;
import jchess.common.ICacheManager;
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

/**
 * This class tests the underlying logic of the board that is common to all the board and that complies with XML.
 * 
 * @author	Sajad Karim
 * @since	14 Dec 2019
 */

class DefaultRuleEngineTest {
	static IBoardAgent m_oBoard;
	static IRuleEngine m_oRuleEngine;
	private static final IBoardFactory m_oBoardFactory = new BoardAgentFactory();
	
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

		oCacheManager.loadBoardFromFile("DefaultRuleEngineTest", "2PlayerBoard.xml");
		m_oBoard = oCacheManager.getBoard("DefaultRuleEngineTest");

		m_oRuleEngine = new DefaultRuleEngine(new DefaultRuleProcessor(oLogger), new GUIManagerTest(oLogger), oLogger);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * This method verifies the capturing and movement functionality of a piece when it moves upwards by 1 (to 'd3').
	 * This method tests that it should be moved to at the position ('d3').
	 */
	@Test
	void testTryExecuteRule_MOVE_CAPTURE_BY_1() { 
		IPositionAgent oSourcePosition = m_oBoard.getPositionAgent("d2");
		IPositionAgent oCandidatePosition = m_oBoard.getPositionAgent("d3");

		IRuleAgent oRule= (IRuleAgent)m_oBoardFactory.createRule();		
		oRule.getRuleData().setRuleType(RuleType.MOVE_AND_CAPTURE);
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.IGNORE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setName("MOVE");
		oRule.getRuleData().setCustomName("");
				
		IMoveCandidate oMoveCandidate = new MoveCandidate(oRule, oSourcePosition.getPiece(), oSourcePosition, oCandidatePosition);
		
		// Following Asset validates the existence of Pawn prior execution of the Rule.
		String oExpectedPieceString  = oSourcePosition.getPiece().getName();
		String oActualPieceString = "PawnWhite";

		assertEquals(oExpectedPieceString, oActualPieceString);

		IBoardActivity oMove = m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidate);

		// Following Asset validates the Pawn piece has moved after execution of the Rule.
		IPieceAgent oExpectedPiece  = oSourcePosition.getPiece();
		IPieceAgent oActualPiece = null;

		assertEquals(oExpectedPiece, oActualPiece);
	}

	/**
	 * This method verifies the capturing functionality of of when it moves upwards by * (to 'd7' - actually d7 has opponents piece so algo should stop).
	 * This method tests that it should capture the piece as there is a piece at the position ('d7').
	 */
	@Test
	void testTryExecuteRule_MOVE_CAPTURE_BY_INF() { 
		IPositionAgent oSourcePosition = m_oBoard.getPositionAgent("d2");
		IPositionAgent oCandidatePosition = m_oBoard.getPositionAgent("d7");

		IRuleAgent oRule= (IRuleAgent)m_oBoardFactory.createRule();		
		oRule.getRuleData().setRuleType(RuleType.MOVE_AND_CAPTURE);
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.IGNORE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setName("MOVE");
		oRule.getRuleData().setCustomName("");
				
		IMoveCandidate oMoveCandidate = new MoveCandidate(oRule, oSourcePosition.getPiece(), oSourcePosition, oCandidatePosition);
		
		// Following Asset validates the existence of Pawn prior execution of the Rule.
		String oExpectedPieceString  = oSourcePosition.getPiece().getName();
		String oActualPieceString = "PawnWhite";

		assertEquals(oExpectedPieceString, oActualPieceString);

		IBoardActivity oMove = m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidate);

		// Following Asset validates the Pawn piece has moved after execution of the Rule.
		IPieceAgent oExpectedPiece  = oSourcePosition.getPiece();
		IPieceAgent oActualPiece = null;

		assertEquals(oExpectedPiece, oActualPiece);
	}
}
