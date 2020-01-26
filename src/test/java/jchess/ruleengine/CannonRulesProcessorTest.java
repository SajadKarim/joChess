package jchess.ruleengine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jchess.cache.CacheManager;
import jchess.cache.ICacheManager;
import jchess.common.IBoardActivity;
import jchess.common.IBoardAgent;
import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;
import jchess.gamelogic.MoveCandidate;
import jchess.util.AppLogger;
import jchess.util.IAppLogger;

class CannonRulesProcessorTest {
	static IAppLogger m_oLogger;
	static IBoardAgent m_oBoard;
	static IRuleProcessor m_oRuleProcessor;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		m_oLogger = new AppLogger();
		m_oRuleProcessor = new ThreePlayersHexBoardRuleProcessor(m_oLogger);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ICacheManager oCacheManager = new CacheManager(m_oLogger);
		
		oCacheManager.loadBoardFromFile("CannonRulesProcessorTest", "3PlayerBoard.xml");
		m_oBoard = oCacheManager.getBoard("CannonRulesProcessorTest");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testTryExecuteBombAndPromotionRule_CannonAtA2() {
		IPieceAgent oPieceToMove = m_oBoard.getPositionAgent("a2").getPiece();
		IPositionAgent oSourcePosition = m_oBoard.getPositionAgent("a2");
		IPositionAgent oCandidatePosition = (IPositionAgent)m_oBoard.getPosition("a6");		
		IMoveCandidate oMoveCandidate = new MoveCandidate((IRuleAgent)(oPieceToMove.getRule("CannonL_1_1")), oPieceToMove, oSourcePosition, oCandidatePosition);
		
		assertEquals(oSourcePosition.getPiece().getName(), "CannonWhiteL");

		IBoardActivity oActivity = CannonRulesProcessor.tryExecuteBombAndPromotionRule(m_oBoard, oMoveCandidate);

		assertEquals(oSourcePosition.getPiece().getName(), "CannonWhiteL");
		
		oActivity = CannonRulesProcessor.tryExecuteBombAndPromotionRule(m_oBoard, oMoveCandidate);
		
		assertEquals(oSourcePosition.getPiece().getName(), "PawnWhite");
	}

	@Test
	void testTryExecuteBombAndPromotionRule_CannonAtH2() {
		IPieceAgent oPieceToMove = m_oBoard.getPositionAgent("h2").getPiece();
		IPositionAgent oSourcePosition = m_oBoard.getPositionAgent("h2");
		IPositionAgent oCandidatePosition = (IPositionAgent)m_oBoard.getPosition("h10");		
		IMoveCandidate oMoveCandidate = new MoveCandidate((IRuleAgent)(oPieceToMove.getRule("CannonR_1_1")), oPieceToMove, oSourcePosition, oCandidatePosition);
		
		assertEquals(oSourcePosition.getPiece().getName(), "CannonWhiteR");

		IBoardActivity oActivity = CannonRulesProcessor.tryExecuteBombAndPromotionRule(m_oBoard, oMoveCandidate);

		assertEquals(oSourcePosition.getPiece().getName(), "CannonWhiteR");
		
		oActivity = CannonRulesProcessor.tryExecuteBombAndPromotionRule(m_oBoard, oMoveCandidate);
		
		assertEquals(oSourcePosition.getPiece().getName(), "PawnWhite");
	}
}
