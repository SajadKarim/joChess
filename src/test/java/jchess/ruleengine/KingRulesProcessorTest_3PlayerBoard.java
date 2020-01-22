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


}
