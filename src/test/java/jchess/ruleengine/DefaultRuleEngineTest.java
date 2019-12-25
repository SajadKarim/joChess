package jchess.ruleengine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jchess.cache.CacheManager;
import jchess.cache.ICacheManager;
import jchess.common.IBoardAgent;
import jchess.common.IMove;
import jchess.common.IMoveCandidacy;
import jchess.common.IPieceAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;
import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;
import jchess.gamelogic.MoveCandidacy;
import jchess.util.AppLogger;
import jchess.util.IAppLogger;

class DefaultRuleEngineTest {
	static IBoardAgent m_oBoard;
	static IRuleEngine m_oRuleEngine;
	
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
		
		oCacheManager.loadBoardFromFile("DefaultRuleEngineTest", "2PlayerBoard");
		m_oBoard = oCacheManager.getBoard("DefaultRuleEngineTest");

		m_oRuleEngine = new DefaultRuleEngine(new DefaultRuleProcessor(oLogger), new GUIManagerTest(oLogger), oLogger);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testTryExecuteRule_MOVE_CAPTURE_BY_1() { 
		IPositionAgent oSourcePosition = m_oBoard.getPositionAgent("d2");
		IPositionAgent oCandidatePosition = m_oBoard.getPositionAgent("d3");

		IRuleAgent oRule= (IRuleAgent)m_oBoard.createRule();		
		oRule.getRuleData().setRuleType(RuleType.MOVE_AND_CAPTURE);
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.IGNORE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setName("MOVE");
		oRule.getRuleData().setCustomName("");
				
		IMoveCandidacy oMoveCandidate = new MoveCandidacy(oSourcePosition, oCandidatePosition, oRule);
		
		// Following Asset validates the existence of Pawn prior execution of the Rule.
		String oExpectedPieceString  = oSourcePosition.getPiece().getName();
		String oActualPieceString = "PawnWhite";

		assertEquals(oExpectedPieceString, oActualPieceString);

		IMove oMove = m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidate);

		// Following Asset validates the Pawn piece has moved after execution of the Rule.
		IPieceAgent oExpectedPiece  = oSourcePosition.getPiece();
		IPieceAgent oActualPiece = null;

		assertEquals(oExpectedPiece, oActualPiece);
	}

	@Test
	void testTryExecuteRule_MOVE_CAPTURE_BY_INF() { 
		IPositionAgent oSourcePosition = m_oBoard.getPositionAgent("d2");
		IPositionAgent oCandidatePosition = m_oBoard.getPositionAgent("d7");

		IRuleAgent oRule= (IRuleAgent)m_oBoard.createRule();		
		oRule.getRuleData().setRuleType(RuleType.MOVE_AND_CAPTURE);
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.IGNORE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setName("MOVE");
		oRule.getRuleData().setCustomName("");
				
		IMoveCandidacy oMoveCandidate = new MoveCandidacy(oSourcePosition, oCandidatePosition, oRule);
		
		// Following Asset validates the existence of Pawn prior execution of the Rule.
		String oExpectedPieceString  = oSourcePosition.getPiece().getName();
		String oActualPieceString = "PawnWhite";

		assertEquals(oExpectedPieceString, oActualPieceString);

		IMove oMove = m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidate);

		// Following Asset validates the Pawn piece has moved after execution of the Rule.
		IPieceAgent oExpectedPiece  = oSourcePosition.getPiece();
		IPieceAgent oActualPiece = null;

		assertEquals(oExpectedPiece, oActualPiece);
	}
}
