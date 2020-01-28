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
import jchess.common.IBoardAgent;
import jchess.common.IBoardFactory;
import jchess.common.ICacheManager;
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
import jchess.gamelogic.MoveCandidate;
import jchess.util.AppLogger;
import jchess.util.IAppLogger;

/**
 * This test class validates rule processing logic and it concentrates solely in moving * positions away from its source.
 * Note: [*] in rule referres to continuation of rule until it reaches the end of board or encounters a piece.
 * 
 * @author 	Sajad Karim
 * @since	25 Dec 2019
 */

class DefaultRuleProcessorTest2_2PlayerBoard {
	static IBoardAgent m_oBoard;
	static DefaultRuleProcessor m_oRuleProcessor;
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

    m_oRuleEngine = new DefaultRuleEngine(new DefaultRuleProcessor(oLogger), new GUIManagerTest(oLogger), oLogger);
		oCacheManager.loadBoardFromFile("DefaultRuleProcessorTest", "2PlayerBoard.xml");
		m_oBoard = oCacheManager.getBoard("DefaultRuleProcessorTest");

		m_oRuleProcessor = new DefaultRuleProcessor(oLogger);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * This method verifies manoeuvre strategy for file and rank and moves in upwards direction by *.
	 */
	@Test
	void testTryEvaluateAllRules_FileAndRankStrategy() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTH_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
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

		int nExpectedValuesInMap = 2;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		String[] arPositionsToValidate = {"d5", "d6"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	/**
	 * This method verifies manoeuvre strategy for 'blinker' and moves in all the diagonal directions by *.
	 */
	@Test
	void testTryEvaluateAllRules_BlinkerStrategy() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_DIAGNOAL_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
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

		int nExpectedValuesInMap = 6;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);

		String[] arPositionsToValidate = {"b6", "c5", "c3", "e3", "e5", "f6"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	/**
	 * This method verifies manoeuvre strategy for 'blinker' and moves in north direction by *.
	 */
	@Test
	void tryFindPossibleCandidateMovePositions_MOVE_NORTH_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTH_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("d3");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 2;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		String[] arPositionsToValidate = {"d5", "d6"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	@Test
	void tryFindPossibleCandidateMovePositions_MOVE_NORTHWEST_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTHWEST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.BACKWARD);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("e3");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 2;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		String[] arPositionsToValidate = {"c5", "b6"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	/**
	 * This method verifies manoeuvre strategy for 'blinker' and moves in west direction by *.
	 */
	@Test
	void tryFindPossibleCandidateMovePositions_MOVE_WEST_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_WEST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.BACKWARD);
		oRule.getRuleData().setRank(Rank.SAME);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("e4");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 3;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		String[] arPositionsToValidate = {"a4", "b4", "c4"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	/**
	 * This method verifies manoeuvre strategy for 'blinker' and moves in south-west direction by *.
	 */
	@Test
	void tryFindPossibleCandidateMovePositions_MOVE_SOUTHWEST_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_SOUTHWEST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.BACKWARD);
		oRule.getRuleData().setRank(Rank.BACKWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("e5");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("c3");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("c3").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	/**
	 * This method verifies manoeuvre strategy for 'blinker' and moves in south direction by *.
	 */
	@Test
	void tryFindPossibleCandidateMovePositions_MOVE_SOUTH_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_SOUTH_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.BACKWARD);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("d5");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("d3");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("d3").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	/**
	 * This method verifies manoeuvre strategy for 'blinker' and moves in south-east direction by *.
	 */
	@Test
	void tryFindPossibleCandidateMovePositions_MOVE_SOUTHEAST_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_SOUTHEAST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.FORWARD);
		oRule.getRuleData().setRank(Rank.BACKWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("c5");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("e3");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("e3").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	/**
	 * This method verifies manoeuvre strategy for 'blinker' and moves in east direction by *.
	 */
	@Test
	void tryFindPossibleCandidateMovePositions_MOVE_EAST_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_EAST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.FORWARD);
		oRule.getRuleData().setRank(Rank.SAME);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("c4");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 4;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		String[] arPositionsToValidate = {"e4", "f4", "g4", "h4"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	/**
	 * This method verifies manoeuvre strategy for 'blinker' and moves in north-east direction by *.
	 */
	@Test
	void tryFindPossibleCandidateMovePositions_MOVE_NORTHEAST_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTHEAST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.FORWARD);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = m_oBoard.getPositionAgent("c3");
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 2;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);

		String[] arPositionsToValidate = {"e5", "f6"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}
	
	/**
	 * This method verifies manoeuvre strategy for 'flie and rank' and moves in north direction by *.
	 */
	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_NORTH_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTH_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 2;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		String[] arPositionsToValidate = {"d5", "d6"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	/**
	 * This method verifies manoeuvre strategy for 'flie and rank' and moves in north west direction by *.
	 */
	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_NORTHWEST_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTHWEST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.BACKWARD);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 2;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		String[] arPositionsToValidate = {"c5", "b6"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	/**
	 * This method verifies manoeuvre strategy for 'flie and rank' and moves in west direction by *.
	 */
	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_WEST_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_WEST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.BACKWARD);
		oRule.getRuleData().setRank(Rank.SAME);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 3;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		String[] arPositionsToValidate = {"a4", "b4", "c4"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	/**
	 * This method verifies manoeuvre strategy for 'flie and rank' and moves in south west direction by *.
	 */
	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_SOUTHWEST_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_SOUTHWEST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.BACKWARD);
		oRule.getRuleData().setRank(Rank.BACKWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("c3");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("c3").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	/**
	 * This method verifies manoeuvre strategy for 'flie and rank' and moves in south direction by *.
	 */
	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_SOUTH_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_SOUTH_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.BACKWARD);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("d3");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("d3").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	/**
	 * This method verifies manoeuvre strategy for 'flie and rank' and south-east in north direction by *.
	 */
	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_SOUTHEAST_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_SOUTHEAST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.FORWARD);
		oRule.getRuleData().setRank(Rank.BACKWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 1;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent("e3");
		IPositionAgent oActualPositionInMap = mpCandidatePositions.get("e3").getCandidatePosition();
		
		assertEquals(oExpectedPositionInMap, oActualPositionInMap);
	}

	/**
	 * This method verifies manoeuvre strategy for 'flie and rank' and moves in east direction by *.
	 */
	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_EAST_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_EAST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.FORWARD);
		oRule.getRuleData().setRank(Rank.SAME);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 4;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		String[] arPositionsToValidate = {"e4", "f4", "g4", "h4"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}

	/**
	 * This method verifies manoeuvre strategy for 'blinker' and moves in north-east direction by *.
	 */
	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_MOVE_NORTHEAST_BY_INF() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTHEAST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.VERTEX);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRule.getRuleData().setFile(File.FORWARD);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.SAME);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		IPositionAgent oCurrentPosition = m_oBoard.getPositionAgent("d4");
		IPositionAgent oLastPosition = null;
		
		IPlayerAgent oPlayer = m_oBoard.getPlayerAgent("P1");

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		qData.add(new RuleProcessorData(oRule, oCurrentPosition, oLastPosition));
		
		Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
		
		m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oCurrentPosition.getPiece(), oCurrentPosition, oPlayer, qData, mpCandidatePositions);

		int nExpectedValuesInMap = 2;
		int nActualValuesInMap = mpCandidatePositions.size();
		
		assertEquals(nExpectedValuesInMap, nActualValuesInMap);
		
		String[] arPositionsToValidate = {"e5", "f6"};
		for( String stPositionId : arPositionsToValidate) {
			IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
			IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
			
			assertEquals(oExpectedPositionInMap, oActualPositionInMap);
		}
	}
	/**
	 * Check if the King is in Check position by
	 * default board without any move.
	 */
	@Test
	void testCheckIfPlayerEndengered_defaultBoard() {
		
		//Check Player Endergered when the game start
		
		IPlayerAgent oPlayerAgent = m_oBoard.getPlayerAgent("P1");
		
		IPlayerAgent oExpected = null;
		IPlayerAgent oActual = m_oRuleProcessor.tryCheckIfPlayerEndengered(m_oBoard, oPlayerAgent);
		
		assertEquals(oExpected,oActual);
	}
	/**
	 * Check if the King is in Check position by
	 * Delete every piece of Player 2 except for King
	 * Move Player 2's King near Rival Pawn
	 * Use Player 1's Pawn to check
	 */
	@Test
	void testCheckIfPlayerEndengered_RivalPawn_King_checkPosition()
	{
		//Get the Player 2
		IPlayerAgent oPlayerAgent = m_oBoard.getPlayerAgent("P2");
		//Get the Player 1
		IPlayerAgent oRivalPlayerAgent = m_oBoard.getPlayerAgent("P1");
		
		// Other than king at e8, Setting pieces of Player 2 as null.
		m_oBoard.getPositionAgent("a8").setPiece(null);
		m_oBoard.getPositionAgent("b8").setPiece(null);
		m_oBoard.getPositionAgent("c8").setPiece(null);
		m_oBoard.getPositionAgent("d8").setPiece(null);
		m_oBoard.getPositionAgent("f8").setPiece(null);
		m_oBoard.getPositionAgent("g8").setPiece(null);
		m_oBoard.getPositionAgent("h8").setPiece(null);
		m_oBoard.getPositionAgent("a7").setPiece(null);
		m_oBoard.getPositionAgent("b7").setPiece(null);
		m_oBoard.getPositionAgent("c7").setPiece(null);
		m_oBoard.getPositionAgent("d7").setPiece(null);
		m_oBoard.getPositionAgent("e7").setPiece(null);
		m_oBoard.getPositionAgent("f7").setPiece(null);
		m_oBoard.getPositionAgent("g7").setPiece(null);
		m_oBoard.getPositionAgent("h7").setPiece(null);
		
		// Setting Player 2 king from e8 to e3
		IPositionAgent oSourcePositionOfKing = m_oBoard.getPositionAgent("e8");
		IPositionAgent oDestinationPositionOfKing = m_oBoard.getPositionAgent("e3");

		IRuleAgent oRuleKing= (IRuleAgent)m_oBoardFactory.createRule();		
		oRuleKing.getRuleData().setRuleType(RuleType.MOVE);
		oRuleKing.getRuleData().setDirection(Direction.EDGE);
		oRuleKing.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRuleKing.getRuleData().setFile(File.SAME);
		oRuleKing.getRuleData().setRank(Rank.FORWARD);
		oRuleKing.getRuleData().setFamily(Family.IGNORE);
		oRuleKing.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRuleKing.getRuleData().setName("MOVE");
		oRuleKing.getRuleData().setCustomName("");

		IMoveCandidate oMoveCandidateKing = new MoveCandidate(oRuleKing, oSourcePositionOfKing.getPiece(), oSourcePositionOfKing, oDestinationPositionOfKing);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidateKing);
		
		IPlayerAgent oExpected = oRivalPlayerAgent;
		IPlayerAgent oActual = m_oRuleProcessor.tryCheckIfPlayerEndengered(m_oBoard, oPlayerAgent);
		System.out.println(oActual);
		
		assertEquals(oExpected,oActual);
		
	}
	/**
	 * Check if the King is in Check position by
	 * Remove every piece of Player 2 except for King
	 * Remove Player 1's Pawn at d2
	 * Move Player 2's King near Rival Queen
	 * Use Player 1's Pawn to check
	 */
	@Test
	void testCheckIfPlayerEndengered_RivalQueen_King_checkPosition()
	{
		//Get the Player 2
		IPlayerAgent oPlayerAgent = m_oBoard.getPlayerAgent("P2");
		//Get the Player 1
		IPlayerAgent oRivalPlayerAgent = m_oBoard.getPlayerAgent("P1");
		
		// Other than king at e8, Setting pieces of Player 2 as null.
		m_oBoard.getPositionAgent("a8").setPiece(null);
		m_oBoard.getPositionAgent("b8").setPiece(null);
		m_oBoard.getPositionAgent("c8").setPiece(null);
		m_oBoard.getPositionAgent("d8").setPiece(null);
		m_oBoard.getPositionAgent("f8").setPiece(null);
		m_oBoard.getPositionAgent("g8").setPiece(null);
		m_oBoard.getPositionAgent("h8").setPiece(null);
		m_oBoard.getPositionAgent("a7").setPiece(null);
		m_oBoard.getPositionAgent("b7").setPiece(null);
		m_oBoard.getPositionAgent("c7").setPiece(null);
		m_oBoard.getPositionAgent("d7").setPiece(null);
		m_oBoard.getPositionAgent("e7").setPiece(null);
		m_oBoard.getPositionAgent("f7").setPiece(null);
		m_oBoard.getPositionAgent("g7").setPiece(null);
		m_oBoard.getPositionAgent("h7").setPiece(null);
		
		//Setting Player 2's pawn at d2 to null
		m_oBoard.getPositionAgent("d2").setPiece(null);
		
		// Setting Player 2 king from e8 to d7
		IPositionAgent oSourcePositionOfKing = m_oBoard.getPositionAgent("e8");
		IPositionAgent oDestinationPositionOfKing = m_oBoard.getPositionAgent("d2");

		IRuleAgent oRuleKing= (IRuleAgent)m_oBoardFactory.createRule();		
		oRuleKing.getRuleData().setRuleType(RuleType.MOVE);
		oRuleKing.getRuleData().setDirection(Direction.EDGE);
		oRuleKing.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRuleKing.getRuleData().setFile(File.SAME);
		oRuleKing.getRuleData().setRank(Rank.FORWARD);
		oRuleKing.getRuleData().setFamily(Family.IGNORE);
		oRuleKing.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRuleKing.getRuleData().setName("MOVE");
		oRuleKing.getRuleData().setCustomName("");

		IMoveCandidate oMoveCandidateKing = new MoveCandidate(oRuleKing, oSourcePositionOfKing.getPiece(), oSourcePositionOfKing, oDestinationPositionOfKing);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidateKing);
		
		IPlayerAgent oExpected = oRivalPlayerAgent;
		IPlayerAgent oActual = m_oRuleProcessor.tryCheckIfPlayerEndengered(m_oBoard, oPlayerAgent);
		System.out.println(oActual);
		
		assertEquals(oExpected,oActual);
		
	}
	/**
	 * Check if the King is in Check position by
	 * Remove every piece of Player 2 except for King
	 * Move Player 2's King to a safe position
	 */
	@Test
	void testCheckIfPlayerEndengered_King_notInCheckPosition()
	{
		//Get the Player 2
		IPlayerAgent oPlayerAgent = m_oBoard.getPlayerAgent("P2");
		//Get the Player 1
		IPlayerAgent oRivalPlayerAgent = m_oBoard.getPlayerAgent("P1");
		
		// Other than king at e8, Setting pieces of Player 2 as null.
		m_oBoard.getPositionAgent("a8").setPiece(null);
		m_oBoard.getPositionAgent("b8").setPiece(null);
		m_oBoard.getPositionAgent("c8").setPiece(null);
		m_oBoard.getPositionAgent("d8").setPiece(null);
		m_oBoard.getPositionAgent("f8").setPiece(null);
		m_oBoard.getPositionAgent("g8").setPiece(null);
		m_oBoard.getPositionAgent("h8").setPiece(null);
		m_oBoard.getPositionAgent("a7").setPiece(null);
		m_oBoard.getPositionAgent("b7").setPiece(null);
		m_oBoard.getPositionAgent("c7").setPiece(null);
		m_oBoard.getPositionAgent("d7").setPiece(null);
		m_oBoard.getPositionAgent("e7").setPiece(null);
		m_oBoard.getPositionAgent("f7").setPiece(null);
		m_oBoard.getPositionAgent("g7").setPiece(null);
		m_oBoard.getPositionAgent("h7").setPiece(null);
			
		// Setting Player 2 king from e8 to d7
		IPositionAgent oSourcePositionOfKing = m_oBoard.getPositionAgent("e8");
		IPositionAgent oDestinationPositionOfKing = m_oBoard.getPositionAgent("e4");

		IRuleAgent oRuleKing= (IRuleAgent)m_oBoardFactory.createRule();		
		oRuleKing.getRuleData().setRuleType(RuleType.MOVE);
		oRuleKing.getRuleData().setDirection(Direction.EDGE);
		oRuleKing.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRuleKing.getRuleData().setFile(File.SAME);
		oRuleKing.getRuleData().setRank(Rank.FORWARD);
		oRuleKing.getRuleData().setFamily(Family.IGNORE);
		oRuleKing.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRuleKing.getRuleData().setName("MOVE");
		oRuleKing.getRuleData().setCustomName("");

		IMoveCandidate oMoveCandidateKing = new MoveCandidate(oRuleKing, oSourcePositionOfKing.getPiece(), oSourcePositionOfKing, oDestinationPositionOfKing);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidateKing);
		
		IPlayerAgent oExpected = null;
		IPlayerAgent oActual = m_oRuleProcessor.tryCheckIfPlayerEndengered(m_oBoard, oPlayerAgent);
		System.out.println(oActual);
		
		assertEquals(oExpected,oActual);
		
	}
	
	
	@Test
	void testCheckForPositionMoveCandidacyAndContinuity() {
	}
	
	@Test
	/*
	 * Checking Stalemate by 
	 * deleteing all pieces of Player 2 except 1 pawn but keeping it trapped.
	 * Moving the King of Player 2 to such a positon that its not a check but cannot move.
	 * here we are using the Queen of Player 1 to achieve that situation.
	 */
	void testCheckStalemate() {
		
		// Other than pawn at e7 and king at e8, Setting pieces of Player 2 as null.
		m_oBoard.getPositionAgent("a8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("b8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("c8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("d8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("f8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("g8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("h8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("a7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("b7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("c7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("d7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("f7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("g7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("h7").getPiece().setPosition(null);

		m_oBoard.getPositionAgent("a8").setPiece(null);
		m_oBoard.getPositionAgent("b8").setPiece(null);
		m_oBoard.getPositionAgent("c8").setPiece(null);
		m_oBoard.getPositionAgent("d8").setPiece(null);
		m_oBoard.getPositionAgent("f8").setPiece(null);
		m_oBoard.getPositionAgent("g8").setPiece(null);
		m_oBoard.getPositionAgent("h8").setPiece(null);
		m_oBoard.getPositionAgent("a7").setPiece(null);
		m_oBoard.getPositionAgent("b7").setPiece(null);
		m_oBoard.getPositionAgent("c7").setPiece(null);
		m_oBoard.getPositionAgent("d7").setPiece(null);
		m_oBoard.getPositionAgent("f7").setPiece(null);
		m_oBoard.getPositionAgent("g7").setPiece(null);
		m_oBoard.getPositionAgent("h7").setPiece(null);
				
		// Setting Player 1 Pawn from e2 to e6
		IPositionAgent oSourcePositionOfPawn = m_oBoard.getPositionAgent("e2");
		IPositionAgent oDestinationPositionOfPawn = m_oBoard.getPositionAgent("e6");
		
		IRuleAgent oRulepawn= (IRuleAgent)m_oBoardFactory.createRule();		
		oRulepawn.getRuleData().setRuleType(RuleType.MOVE);
		oRulepawn.getRuleData().setDirection(Direction.EDGE);
		oRulepawn.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRulepawn.getRuleData().setFile(File.SAME);
		oRulepawn.getRuleData().setRank(Rank.FORWARD);
		oRulepawn.getRuleData().setFamily(Family.IGNORE);
		oRulepawn.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRulepawn.getRuleData().setName("MOVE");
		oRulepawn.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidatePawn = new MoveCandidate(oRulepawn, oSourcePositionOfPawn.getPiece(), oSourcePositionOfPawn, oDestinationPositionOfPawn);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidatePawn);
		
		// Setting Player 2 king from e8 to h8
		IPositionAgent oSourcePositionOfKing = m_oBoard.getPositionAgent("e8");
		IPositionAgent oDestinationPositionOfKing = m_oBoard.getPositionAgent("h8");
		
		IRuleAgent oRuleKing= (IRuleAgent)m_oBoardFactory.createRule();		
		oRuleKing.getRuleData().setRuleType(RuleType.MOVE);
		oRuleKing.getRuleData().setDirection(Direction.EDGE);
		oRuleKing.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRuleKing.getRuleData().setFile(File.SAME);
		oRuleKing.getRuleData().setRank(Rank.FORWARD);
		oRuleKing.getRuleData().setFamily(Family.IGNORE);
		oRuleKing.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRuleKing.getRuleData().setName("MOVE");
		oRuleKing.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidateKing = new MoveCandidate(oRuleKing, oSourcePositionOfKing.getPiece(), oSourcePositionOfKing, oDestinationPositionOfKing);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidateKing);
		
		// Setting Player 1 queen from d1 to g6
		IPositionAgent oSourcePositionOfQueen = m_oBoard.getPositionAgent("d1");
		IPositionAgent oDestinationPositionOfQueen = m_oBoard.getPositionAgent("g6");
		
		IRuleAgent oRuleQueen= (IRuleAgent)m_oBoardFactory.createRule();		
		oRuleQueen.getRuleData().setRuleType(RuleType.MOVE);
		oRuleQueen.getRuleData().setDirection(Direction.VERTEX);
		oRuleQueen.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRuleQueen.getRuleData().setFile(File.SAME);
		oRuleQueen.getRuleData().setRank(Rank.FORWARD);
		oRuleQueen.getRuleData().setFamily(Family.IGNORE);
		oRuleQueen.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRuleQueen.getRuleData().setName("MOVE");
		oRuleQueen.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidateQueen = new MoveCandidate(oRuleQueen, oSourcePositionOfQueen.getPiece(), oSourcePositionOfQueen, oDestinationPositionOfQueen);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidateQueen);
		
		Boolean bExpected = true;
		Boolean bActual = m_oRuleProcessor.checkStalemate(m_oBoard, oDestinationPositionOfKing.getPiece().getPlayer());

		assertEquals(bExpected, bActual);
		
	}
	
	@Test
	/*
	 * Checking Stalemate by 
	 * deleteing all pieces of Player 2 except 1 pawn but keeping it trapped.
	 * Moving the King of Player 2 to such a positon that it can make more than 1 movements.
	 * here we are using the Queen of Player 1 to achieve that situation.
	 * In this case its not a stalemate as the king can move.
	 */
	void testCheckStalemateWhenKingCanMove() {
		
		// Other than pawn at e7 and king at e8, Setting pieces of Player 2 as null.
		m_oBoard.getPositionAgent("a8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("b8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("c8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("d8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("f8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("g8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("h8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("a7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("b7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("c7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("d7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("f7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("g7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("h7").getPiece().setPosition(null);

		m_oBoard.getPositionAgent("a8").setPiece(null);
		m_oBoard.getPositionAgent("b8").setPiece(null);
		m_oBoard.getPositionAgent("c8").setPiece(null);
		m_oBoard.getPositionAgent("d8").setPiece(null);
		m_oBoard.getPositionAgent("f8").setPiece(null);
		m_oBoard.getPositionAgent("g8").setPiece(null);
		m_oBoard.getPositionAgent("h8").setPiece(null);
		m_oBoard.getPositionAgent("a7").setPiece(null);
		m_oBoard.getPositionAgent("b7").setPiece(null);
		m_oBoard.getPositionAgent("c7").setPiece(null);
		m_oBoard.getPositionAgent("d7").setPiece(null);
		m_oBoard.getPositionAgent("f7").setPiece(null);
		m_oBoard.getPositionAgent("g7").setPiece(null);
		m_oBoard.getPositionAgent("h7").setPiece(null);
				
		// Setting Player 1 Pawn from e2 to e6
		IPositionAgent oSourcePositionOfPawn = m_oBoard.getPositionAgent("e2");
		IPositionAgent oDestinationPositionOfPawn = m_oBoard.getPositionAgent("e6");
		
		IRuleAgent oRulepawn= (IRuleAgent)m_oBoardFactory.createRule();		
		oRulepawn.getRuleData().setRuleType(RuleType.MOVE);
		oRulepawn.getRuleData().setDirection(Direction.EDGE);
		oRulepawn.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRulepawn.getRuleData().setFile(File.SAME);
		oRulepawn.getRuleData().setRank(Rank.FORWARD);
		oRulepawn.getRuleData().setFamily(Family.IGNORE);
		oRulepawn.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRulepawn.getRuleData().setName("MOVE");
		oRulepawn.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidatePawn = new MoveCandidate(oRulepawn, oSourcePositionOfPawn.getPiece(), oSourcePositionOfPawn, oDestinationPositionOfPawn);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidatePawn);
		
		// Setting Player 2 king from e8 to h8
		IPositionAgent oSourcePositionOfKing = m_oBoard.getPositionAgent("e8");
		IPositionAgent oDestinationPositionOfKing = m_oBoard.getPositionAgent("h8");
		
		IRuleAgent oRuleKing= (IRuleAgent)m_oBoardFactory.createRule();		
		oRuleKing.getRuleData().setRuleType(RuleType.MOVE);
		oRuleKing.getRuleData().setDirection(Direction.EDGE);
		oRuleKing.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRuleKing.getRuleData().setFile(File.SAME);
		oRuleKing.getRuleData().setRank(Rank.FORWARD);
		oRuleKing.getRuleData().setFamily(Family.IGNORE);
		oRuleKing.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRuleKing.getRuleData().setName("MOVE");
		oRuleKing.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidateKing = new MoveCandidate(oRuleKing, oSourcePositionOfKing.getPiece(), oSourcePositionOfKing, oDestinationPositionOfKing);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidateKing);
		
		// Setting Player 1 queen from d1 to g6
		IPositionAgent oSourcePositionOfQueen = m_oBoard.getPositionAgent("d1");
		IPositionAgent oDestinationPositionOfQueen = m_oBoard.getPositionAgent("g5");
		
		IRuleAgent oRuleQueen= (IRuleAgent)m_oBoardFactory.createRule();		
		oRuleQueen.getRuleData().setRuleType(RuleType.MOVE);
		oRuleQueen.getRuleData().setDirection(Direction.VERTEX);
		oRuleQueen.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRuleQueen.getRuleData().setFile(File.SAME);
		oRuleQueen.getRuleData().setRank(Rank.FORWARD);
		oRuleQueen.getRuleData().setFamily(Family.IGNORE);
		oRuleQueen.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRuleQueen.getRuleData().setName("MOVE");
		oRuleQueen.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidateQueen = new MoveCandidate(oRuleQueen, oSourcePositionOfQueen.getPiece(), oSourcePositionOfQueen, oDestinationPositionOfQueen);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidateQueen);
		
		Boolean bExpected = false;
		Boolean bActual = m_oRuleProcessor.checkStalemate(m_oBoard, oDestinationPositionOfKing.getPiece().getPlayer());

		assertEquals(bExpected, bActual);
		
	}
	
	@Test
	/*
	 * Checking Stalemate by 
	 * deleteing all pieces of Player 2 except 1 pawn, here pawn has movement and is not trapped.
	 * Moving the King of Player 2 to such a positon that it cannot make a movement.
	 * here we are using the Queen of Player 1 to achieve that situation.
	 * In this case its not a stalemate as the pawn can move.
	 */
	void testCheckStalemateWhenKingCantMove() {
		
		// Other than pawn at e7 and king at e8, Setting pieces of Player 2 as null.
		m_oBoard.getPositionAgent("a8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("b8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("c8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("d8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("f8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("g8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("h8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("a7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("b7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("c7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("d7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("f7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("g7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("h7").getPiece().setPosition(null);

		m_oBoard.getPositionAgent("a8").setPiece(null);
		m_oBoard.getPositionAgent("b8").setPiece(null);
		m_oBoard.getPositionAgent("c8").setPiece(null);
		m_oBoard.getPositionAgent("d8").setPiece(null);
		m_oBoard.getPositionAgent("f8").setPiece(null);
		m_oBoard.getPositionAgent("g8").setPiece(null);
		m_oBoard.getPositionAgent("h8").setPiece(null);
		m_oBoard.getPositionAgent("a7").setPiece(null);
		m_oBoard.getPositionAgent("b7").setPiece(null);
		m_oBoard.getPositionAgent("c7").setPiece(null);
		m_oBoard.getPositionAgent("d7").setPiece(null);
		m_oBoard.getPositionAgent("f7").setPiece(null);
		m_oBoard.getPositionAgent("g7").setPiece(null);
		m_oBoard.getPositionAgent("h7").setPiece(null);
				
		// Setting Player 1 Pawn from e2 to e6
		IPositionAgent oSourcePositionOfPawn = m_oBoard.getPositionAgent("e2");
		IPositionAgent oDestinationPositionOfPawn = m_oBoard.getPositionAgent("e5");
		
		IRuleAgent oRulepawn= (IRuleAgent)m_oBoardFactory.createRule();		
		oRulepawn.getRuleData().setRuleType(RuleType.MOVE);
		oRulepawn.getRuleData().setDirection(Direction.EDGE);
		oRulepawn.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRulepawn.getRuleData().setFile(File.SAME);
		oRulepawn.getRuleData().setRank(Rank.FORWARD);
		oRulepawn.getRuleData().setFamily(Family.IGNORE);
		oRulepawn.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRulepawn.getRuleData().setName("MOVE");
		oRulepawn.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidatePawn = new MoveCandidate(oRulepawn, oSourcePositionOfPawn.getPiece(), oSourcePositionOfPawn, oDestinationPositionOfPawn);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidatePawn);
		
		// Setting Player 2 king from e8 to h8
		IPositionAgent oSourcePositionOfKing = m_oBoard.getPositionAgent("e8");
		IPositionAgent oDestinationPositionOfKing = m_oBoard.getPositionAgent("h8");
		
		IRuleAgent oRuleKing= (IRuleAgent)m_oBoardFactory.createRule();		
		oRuleKing.getRuleData().setRuleType(RuleType.MOVE);
		oRuleKing.getRuleData().setDirection(Direction.EDGE);
		oRuleKing.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRuleKing.getRuleData().setFile(File.SAME);
		oRuleKing.getRuleData().setRank(Rank.FORWARD);
		oRuleKing.getRuleData().setFamily(Family.IGNORE);
		oRuleKing.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRuleKing.getRuleData().setName("MOVE");
		oRuleKing.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidateKing = new MoveCandidate(oRuleKing, oSourcePositionOfKing.getPiece(), oSourcePositionOfKing, oDestinationPositionOfKing);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidateKing);
		
		// Setting Player 1 queen from d1 to g6
		IPositionAgent oSourcePositionOfQueen = m_oBoard.getPositionAgent("d1");
		IPositionAgent oDestinationPositionOfQueen = m_oBoard.getPositionAgent("g6");
		
		IRuleAgent oRuleQueen= (IRuleAgent)m_oBoardFactory.createRule();		
		oRuleQueen.getRuleData().setRuleType(RuleType.MOVE);
		oRuleQueen.getRuleData().setDirection(Direction.VERTEX);
		oRuleQueen.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRuleQueen.getRuleData().setFile(File.SAME);
		oRuleQueen.getRuleData().setRank(Rank.FORWARD);
		oRuleQueen.getRuleData().setFamily(Family.IGNORE);
		oRuleQueen.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRuleQueen.getRuleData().setName("MOVE");
		oRuleQueen.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidateQueen = new MoveCandidate(oRuleQueen, oSourcePositionOfQueen.getPiece(), oSourcePositionOfQueen, oDestinationPositionOfQueen);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidateQueen);
		
		Boolean bExpected = false;
		Boolean bActual = m_oRuleProcessor.checkStalemate(m_oBoard, oDestinationPositionOfKing.getPiece().getPlayer());

		assertEquals(bExpected, bActual);
		
	}
}
