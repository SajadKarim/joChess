package jchess.ruleengine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jchess.cache.CacheManager;
import jchess.cache.ICacheManager;
import jchess.common.IBoardAgent;
import jchess.common.IBoardFactory;
import jchess.common.IPolygon;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;
import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;
import jchess.gamelogic.BoardAgentFactory;
import jchess.gamelogic.PieceAgent;
import jchess.util.AppLogger;
import jchess.util.IAppLogger;

class DefaultRuleProcessorTest_3PlayersBoard {
	static Map<IRuleAgent, List<Pair<String, String[]>>> m_mpData;

	static IBoardAgent m_oBoard;
	static DefaultRuleProcessor m_oRuleProcessor;
	static IBoardFactory m_oBoardFactory = new BoardAgentFactory();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		m_mpData = new 	HashMap<IRuleAgent, List<Pair<String, String[]>>>();
		
		populateDataToValidateUpwardPositions();
	}

	static void populateDataToValidateUpwardPositions() {
		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTH_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		
		List lst = new LinkedList<Pair<String, String[]>>();
		lst.add(new Pair("a1", new String[]{"a2","a3","a4","a5","a6","a7","a8"} ));
		
		m_mpData.put(oRule, lst);
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

		for (Map.Entry<String,IPositionAgent> entry : m_oBoard.getAllPositionAgents().entrySet()) {
    		IPositionAgent oPosition = entry.getValue();
    		if( oPosition.getPiece() != null) {
    			oPosition.setPiece(null);
    		}
    	}

		m_oRuleProcessor = new DefaultRuleProcessor(oLogger);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testTryEvaluateAllRules() {
	}

	@Test
	void testTryFindPossibleCandidateMovePositions() {
		
	}

	@Test
	void testTryFindCandidateMovesForBlinkerStrategy() {
	}

	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy() {
	}

	@Test
	void testCheckForPositionMoveCandidacyAndContinuity() {
	}

}
