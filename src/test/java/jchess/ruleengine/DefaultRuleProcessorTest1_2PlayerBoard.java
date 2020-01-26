package jchess.ruleengine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;
import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;
import jchess.gamelogic.BoardAgentFactory;
import jchess.util.AppLogger;
import jchess.util.IAppLogger;

/**
 * This test class validates rule processing logic and it concentrates solely in moving one position away from its source.
 * 
 * @author 	Sajad Karim
 * @since	25 Dec 2019
 */

class DefaultRuleProcessorTest1_2PlayerBoard {
	static IBoardAgent m_oBoard;
	static DefaultRuleProcessor m_oRuleProcessor;
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
		
		oCacheManager.loadBoardFromFile("DefaultRuleProcessorTest", "2PlayerBoard.xml");
		m_oBoard = oCacheManager.getBoard("DefaultRuleProcessorTest");

		m_oRuleProcessor = new DefaultRuleProcessor(oLogger);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testTryEvaluateAllRules_FileAndRankStrategy() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTH_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		IPieceAgent oPiece = (IPieceAgent) m_oBoardFactory.createPiece();
		oPiece.getPieceData().setFamily("");
		oPiece.getPieceData().setImagePath("");
		oPiece.getPieceData().setName("Test_Piece");
		oPiece.getPieceData().addRule(oRule);
		oPiece.setPlayer(oPlayer);
		
		IPositionAgent oPosition = m_oBoard.getPositionAgent("d4");
		oPosition.setPiece(oPiece);
		oPiece.setPosition(oPosition);
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryEvaluateAllRules(m_oBoard, oPiece, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("d5");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("d5").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryEvaluateAllRules_BlinkerStrategy() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_DIAGNOAL_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.IGNORE);
		oRule.getRuleData().setRank(Rank.IGNORE);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		IPieceAgent oPiece = (IPieceAgent) m_oBoardFactory.createPiece();
		oPiece.getPieceData().setFamily("");
		oPiece.getPieceData().setImagePath("");
		oPiece.getPieceData().setName("Test_Piece");
		oPiece.getPieceData().addRule(oRule);
		oPiece.setPlayer(oPlayer);
		
		IPositionAgent oPosition = m_oBoard.getPositionAgent("d4");
		oPosition.setPiece(oPiece);
		oPiece.setPosition(oPosition);
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryEvaluateAllRules(m_oBoard, oPiece, mpCandidatePositions);

		int nExpectedValuesInMap = 4;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);

		String[] arPositionsToValidate = {"c5", "c3", "e3", "e5"};
		for (String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
}

	@Test
	void testTryFindCandidateMovesForBlinkerStrategy_MOVE_NORTH_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTH_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("d3");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForBlinkerStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("d5");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("d5").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForBlinkerStrategy_MOVE_NORTHWEST_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTHWEST_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.BACKWARD);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("e3");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForBlinkerStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("c5");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("c5").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForBlinkerStrategy_MOVE_WEST_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_WEST_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.BACKWARD);
		oRule.getRuleData().setRank(Rank.SAME);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("e4");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForBlinkerStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("c4");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("c4").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForBlinkerStrategy_MOVE_SOUTHWEST_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_SOUTHWEST_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.BACKWARD);
		oRule.getRuleData().setRank(Rank.BACKWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("e5");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForBlinkerStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("c3");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("c3").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForBlinkerStrategy_MOVE_SOUTH_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_SOUTH_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.BACKWARD);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("d5");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForBlinkerStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("d3");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("d3").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForBlinkerStrategy_MOVE_SOUTHEAST_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_SOUTHEAST_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.FORWARD);
		oRule.getRuleData().setRank(Rank.BACKWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("c5");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForBlinkerStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("e3");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("e3").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForBlinkerStrategy_MOVE_EAST_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_EAST_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.FORWARD);
		oRule.getRuleData().setRank(Rank.SAME);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("c4");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForBlinkerStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("e4");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("e4").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForBlinkerStrategy_MOVE_NORTHEAST_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTHEAST_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.FORWARD);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("c3");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForBlinkerStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("e5");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("e5").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}
	
	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_NORTH_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTH_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForFileAndRankStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("d5");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("d5").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_NORTHWEST_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTHWEST_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.BACKWARD);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForFileAndRankStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("c5");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("c5").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_WEST_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_WEST_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.BACKWARD);
		oRule.getRuleData().setRank(Rank.SAME);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForFileAndRankStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("c4");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("c4").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_SOUTHWEST_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_SOUTHWEST_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.BACKWARD);
		oRule.getRuleData().setRank(Rank.BACKWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForFileAndRankStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("c3");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("c3").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_SOUTH_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_SOUTH_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.BACKWARD);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForFileAndRankStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("d3");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("d3").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_SOUTHEAST_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_SOUTHEAST_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.FORWARD);
		oRule.getRuleData().setRank(Rank.BACKWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForFileAndRankStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("e3");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("e3").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_EAST_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_EAST_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.FORWARD);
		oRule.getRuleData().setRank(Rank.SAME);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForFileAndRankStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("e4");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("e4").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_NORTHEAST_BY_1() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTHEAST_BY_1");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.FORWARD);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindCandidateMovesForFileAndRankStrategy(oRule, oCurrentPosition, oLastPosition, oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("e5");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("e5").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	@Test
	void testCheckForPositionMoveCandidacyAndContinuity() {
	}
}
