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
 * This class tests 3 Players Chess-board.
 * 
 * @author	Sajad Karim
 * @since	10 Jan 2019
 */

class ThreePlayersHexBoardRuleProcessorTest {
	static IBoardAgent m_oBoard;
	static DefaultRuleProcessor m_oRuleProcessor;
	static IBoardFactory m_oBoardFactory = new BoardAgentFactory();
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
		
		oCacheManager.loadBoardFromFile("ThreePlayersHexBoardRuleProcessorTest", "3PlayerBoard.xml");
		m_oBoard = oCacheManager.getBoard("ThreePlayersHexBoardRuleProcessorTest");

		m_oRuleEngine = new DefaultRuleEngine(new DefaultRuleProcessor(oLogger), new GUIManagerTest(oLogger), oLogger);
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
	
	@Test
	/*
	 * Checking Stalemate by 
	 * deleteing all pieces of Player 2 except 1 pawn but keeping it trapped.
	 * Moving the King of Player 2 to such a positon that its not a check but cannot move.
	 * here we are using the Queen of Player 1 to achieve that situation.
	 */
	void testCheckStalemate() {
		
		// Other than pawn at b7 and king at i8, Setting pieces of Player 2 as null.
		m_oBoard.getPositionAgent("a8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("b8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("c8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("d8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("j8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("k8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("l8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("a7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("c7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("d7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("i7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("j7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("k7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("l7").getPiece().setPosition(null);

		m_oBoard.getPositionAgent("a8").setPiece(null);
		m_oBoard.getPositionAgent("b8").setPiece(null);
		m_oBoard.getPositionAgent("c8").setPiece(null);
		m_oBoard.getPositionAgent("d8").setPiece(null);
		m_oBoard.getPositionAgent("j8").setPiece(null);
		m_oBoard.getPositionAgent("k8").setPiece(null);
		m_oBoard.getPositionAgent("l8").setPiece(null);
		m_oBoard.getPositionAgent("a7").setPiece(null);
		m_oBoard.getPositionAgent("c7").setPiece(null);
		m_oBoard.getPositionAgent("d7").setPiece(null);
		m_oBoard.getPositionAgent("i7").setPiece(null);
		m_oBoard.getPositionAgent("j7").setPiece(null);
		m_oBoard.getPositionAgent("k7").setPiece(null);
		m_oBoard.getPositionAgent("l7").setPiece(null);
				
		// Setting Player 1 Pawn from b2 to b4
		IPositionAgent oSourcePositionOfPawnWhite = m_oBoard.getPositionAgent("b2");
		IPositionAgent oDestinationPositionOfPawnWhite = m_oBoard.getPositionAgent("b4");
		
		IRuleAgent oRulepawnWhite= (IRuleAgent)m_oBoardFactory.createRule();		
		oRulepawnWhite.getRuleData().setRuleType(RuleType.MOVE);
		oRulepawnWhite.getRuleData().setDirection(Direction.EDGE);
		oRulepawnWhite.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRulepawnWhite.getRuleData().setFile(File.SAME);
		oRulepawnWhite.getRuleData().setRank(Rank.FORWARD);
		oRulepawnWhite.getRuleData().setFamily(Family.IGNORE);
		oRulepawnWhite.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRulepawnWhite.getRuleData().setName("MOVE");
		oRulepawnWhite.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidatePawnWhite = new MoveCandidate(oRulepawnWhite, oSourcePositionOfPawnWhite.getPiece(), oSourcePositionOfPawnWhite, oDestinationPositionOfPawnWhite);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidatePawnWhite);
		
		// Setting Player 2 Pawn from b7 to b5
		IPositionAgent oSourcePositionOfPawnBlack = m_oBoard.getPositionAgent("b7");
		IPositionAgent oDestinationPositionOfPawnBlack = m_oBoard.getPositionAgent("b5");
		
		IRuleAgent oRulepawnBlack= (IRuleAgent)m_oBoardFactory.createRule();		
		oRulepawnBlack.getRuleData().setRuleType(RuleType.MOVE);
		oRulepawnBlack.getRuleData().setDirection(Direction.EDGE);
		oRulepawnBlack.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRulepawnBlack.getRuleData().setFile(File.SAME);
		oRulepawnBlack.getRuleData().setRank(Rank.FORWARD);
		oRulepawnBlack.getRuleData().setFamily(Family.IGNORE);
		oRulepawnBlack.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRulepawnBlack.getRuleData().setName("MOVE");
		oRulepawnBlack.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidatePawnBlack = new MoveCandidate(oRulepawnWhite, oSourcePositionOfPawnBlack.getPiece(), oSourcePositionOfPawnBlack, oDestinationPositionOfPawnBlack);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidatePawnBlack);
				
		// Setting Player 2 king from i8 to l8
		IPositionAgent oSourcePositionOfKing = m_oBoard.getPositionAgent("i8");
		IPositionAgent oDestinationPositionOfKing = m_oBoard.getPositionAgent("l8");
		
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
		
		// Setting Player 1 queen from d1 to j7
		IPositionAgent oSourcePositionOfQueen = m_oBoard.getPositionAgent("d1");
		IPositionAgent oDestinationPositionOfQueen = m_oBoard.getPositionAgent("j7");
		
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
		
		// Other than pawn at b7 and king at i8, Setting pieces of Player 2 as null.
		m_oBoard.getPositionAgent("a8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("b8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("c8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("d8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("j8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("k8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("l8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("a7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("c7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("d7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("i7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("j7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("k7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("l7").getPiece().setPosition(null);

		m_oBoard.getPositionAgent("a8").setPiece(null);
		m_oBoard.getPositionAgent("b8").setPiece(null);
		m_oBoard.getPositionAgent("c8").setPiece(null);
		m_oBoard.getPositionAgent("d8").setPiece(null);
		m_oBoard.getPositionAgent("j8").setPiece(null);
		m_oBoard.getPositionAgent("k8").setPiece(null);
		m_oBoard.getPositionAgent("l8").setPiece(null);
		m_oBoard.getPositionAgent("a7").setPiece(null);
		m_oBoard.getPositionAgent("c7").setPiece(null);
		m_oBoard.getPositionAgent("d7").setPiece(null);
		m_oBoard.getPositionAgent("i7").setPiece(null);
		m_oBoard.getPositionAgent("j7").setPiece(null);
		m_oBoard.getPositionAgent("k7").setPiece(null);
		m_oBoard.getPositionAgent("l7").setPiece(null);
				
		// Setting Player 1 Pawn from b2 to b4
		IPositionAgent oSourcePositionOfPawnWhite = m_oBoard.getPositionAgent("b2");
		IPositionAgent oDestinationPositionOfPawnWhite = m_oBoard.getPositionAgent("b4");
		
		IRuleAgent oRulepawnWhite= (IRuleAgent)m_oBoardFactory.createRule();		
		oRulepawnWhite.getRuleData().setRuleType(RuleType.MOVE);
		oRulepawnWhite.getRuleData().setDirection(Direction.EDGE);
		oRulepawnWhite.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRulepawnWhite.getRuleData().setFile(File.SAME);
		oRulepawnWhite.getRuleData().setRank(Rank.FORWARD);
		oRulepawnWhite.getRuleData().setFamily(Family.IGNORE);
		oRulepawnWhite.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRulepawnWhite.getRuleData().setName("MOVE");
		oRulepawnWhite.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidatePawnWhite = new MoveCandidate(oRulepawnWhite, oSourcePositionOfPawnWhite.getPiece(), oSourcePositionOfPawnWhite, oDestinationPositionOfPawnWhite);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidatePawnWhite);
		
		// Setting Player 2 Pawn from b7 to b5
		IPositionAgent oSourcePositionOfPawnBlack = m_oBoard.getPositionAgent("b7");
		IPositionAgent oDestinationPositionOfPawnBlack = m_oBoard.getPositionAgent("b5");
		
		IRuleAgent oRulepawnBlack= (IRuleAgent)m_oBoardFactory.createRule();		
		oRulepawnBlack.getRuleData().setRuleType(RuleType.MOVE);
		oRulepawnBlack.getRuleData().setDirection(Direction.EDGE);
		oRulepawnBlack.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRulepawnBlack.getRuleData().setFile(File.SAME);
		oRulepawnBlack.getRuleData().setRank(Rank.FORWARD);
		oRulepawnBlack.getRuleData().setFamily(Family.IGNORE);
		oRulepawnBlack.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRulepawnBlack.getRuleData().setName("MOVE");
		oRulepawnBlack.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidatePawnBlack = new MoveCandidate(oRulepawnWhite, oSourcePositionOfPawnBlack.getPiece(), oSourcePositionOfPawnBlack, oDestinationPositionOfPawnBlack);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidatePawnBlack);
				
		// Setting Player 2 king from i8 to l8
		IPositionAgent oSourcePositionOfKing = m_oBoard.getPositionAgent("i8");
		IPositionAgent oDestinationPositionOfKing = m_oBoard.getPositionAgent("l8");
		
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
		
		// Setting Player 1 queen from d1 to k5
		IPositionAgent oSourcePositionOfQueen = m_oBoard.getPositionAgent("d1");
		IPositionAgent oDestinationPositionOfQueen = m_oBoard.getPositionAgent("k5");
		
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
		
		// Other than pawn at b7 and king at i8, Setting pieces of Player 2 as null.
		m_oBoard.getPositionAgent("a8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("b8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("c8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("d8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("j8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("k8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("l8").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("a7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("c7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("d7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("i7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("j7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("k7").getPiece().setPosition(null);
		m_oBoard.getPositionAgent("l7").getPiece().setPosition(null);

		m_oBoard.getPositionAgent("a8").setPiece(null);
		m_oBoard.getPositionAgent("b8").setPiece(null);
		m_oBoard.getPositionAgent("c8").setPiece(null);
		m_oBoard.getPositionAgent("d8").setPiece(null);
		m_oBoard.getPositionAgent("j8").setPiece(null);
		m_oBoard.getPositionAgent("k8").setPiece(null);
		m_oBoard.getPositionAgent("l8").setPiece(null);
		m_oBoard.getPositionAgent("a7").setPiece(null);
		m_oBoard.getPositionAgent("c7").setPiece(null);
		m_oBoard.getPositionAgent("d7").setPiece(null);
		m_oBoard.getPositionAgent("i7").setPiece(null);
		m_oBoard.getPositionAgent("j7").setPiece(null);
		m_oBoard.getPositionAgent("k7").setPiece(null);
		m_oBoard.getPositionAgent("l7").setPiece(null);
				
		// Setting Player 1 Pawn from b2 to b4
		IPositionAgent oSourcePositionOfPawnWhite = m_oBoard.getPositionAgent("b2");
		IPositionAgent oDestinationPositionOfPawnWhite = m_oBoard.getPositionAgent("b4");
		
		IRuleAgent oRulepawnWhite= (IRuleAgent)m_oBoardFactory.createRule();		
		oRulepawnWhite.getRuleData().setRuleType(RuleType.MOVE);
		oRulepawnWhite.getRuleData().setDirection(Direction.EDGE);
		oRulepawnWhite.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRulepawnWhite.getRuleData().setFile(File.SAME);
		oRulepawnWhite.getRuleData().setRank(Rank.FORWARD);
		oRulepawnWhite.getRuleData().setFamily(Family.IGNORE);
		oRulepawnWhite.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRulepawnWhite.getRuleData().setName("MOVE");
		oRulepawnWhite.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidatePawnWhite = new MoveCandidate(oRulepawnWhite, oSourcePositionOfPawnWhite.getPiece(), oSourcePositionOfPawnWhite, oDestinationPositionOfPawnWhite);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidatePawnWhite);
		
		// Setting Player 2 Pawn from b7 to b5
		IPositionAgent oSourcePositionOfPawnBlack = m_oBoard.getPositionAgent("b7");
		IPositionAgent oDestinationPositionOfPawnBlack = m_oBoard.getPositionAgent("b4");
		
		IRuleAgent oRulepawnBlack= (IRuleAgent)m_oBoardFactory.createRule();		
		oRulepawnBlack.getRuleData().setRuleType(RuleType.MOVE);
		oRulepawnBlack.getRuleData().setDirection(Direction.EDGE);
		oRulepawnBlack.getRuleData().setMaxRecurrenceCount(Integer.MAX_VALUE);
		oRulepawnBlack.getRuleData().setFile(File.SAME);
		oRulepawnBlack.getRuleData().setRank(Rank.FORWARD);
		oRulepawnBlack.getRuleData().setFamily(Family.IGNORE);
		oRulepawnBlack.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRulepawnBlack.getRuleData().setName("MOVE");
		oRulepawnBlack.getRuleData().setCustomName("");
		
		IMoveCandidate oMoveCandidatePawnBlack = new MoveCandidate(oRulepawnWhite, oSourcePositionOfPawnBlack.getPiece(), oSourcePositionOfPawnBlack, oDestinationPositionOfPawnBlack);
		m_oRuleEngine.tryExecuteRule(m_oBoard, oMoveCandidatePawnBlack);
				
		// Setting Player 2 king from i8 to l8
		IPositionAgent oSourcePositionOfKing = m_oBoard.getPositionAgent("i8");
		IPositionAgent oDestinationPositionOfKing = m_oBoard.getPositionAgent("l8");
		
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
		
		// Setting Player 1 queen from d1 to j7
		IPositionAgent oSourcePositionOfQueen = m_oBoard.getPositionAgent("d1");
		IPositionAgent oDestinationPositionOfQueen = m_oBoard.getPositionAgent("j7");
		
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
