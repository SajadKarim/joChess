package jchess.ruleengine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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

class DefaultRuleProcessorTest_3PlayersBoard {
	static IBoardAgent m_oBoard;
	static DefaultRuleProcessor m_oRuleProcessor;
	static IBoardFactory m_oBoardFactory = new BoardAgentFactory();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		IAppLogger oLogger = new AppLogger();
		ICacheManager oCacheManager = new CacheManager(oLogger);
		
		oCacheManager.loadBoardFromFile("DefaultRuleProcessorTest", "3PlayerBoard.xml");
		m_oBoard = oCacheManager.getBoard("DefaultRuleProcessorTest");

		for (Map.Entry<String, IPositionAgent> entry : m_oBoard.getAllPositionAgents().entrySet()) {
    		IPositionAgent oPosition = entry.getValue();
    		if (oPosition.getPiece() != null) {
    			oPosition.setPiece(null);
    		}
    	}

		m_oRuleProcessor = new DefaultRuleProcessor(oLogger);
	}

	static List<Pair<String, String[]>> getDataForUpwardPositions() {
		List<Pair<String, String[]>> lstData = new LinkedList<Pair<String, String[]>>();
		
		lstData.add(new Pair("a1", new String[]{"a2", "a3", "a4", "a5", "a6", "a7", "a8"}));
		lstData.add(new Pair("a2", new String[]{"a3", "a4", "a5", "a6", "a7", "a8"}));
		lstData.add(new Pair("a3", new String[]{"a4", "a5", "a6", "a7", "a8"}));
		lstData.add(new Pair("a4", new String[]{"a5", "a6", "a7", "a8"}));
		lstData.add(new Pair("a5", new String[]{"a6", "a7", "a8"}));
		lstData.add(new Pair("a6", new String[]{"a7", "a8"}));
		lstData.add(new Pair("a7", new String[]{"a8"}));
		lstData.add(new Pair("a8", new String[]{}));
		
        lstData.add(new Pair("b1", new String[] {"b2", "b3", "b4", "b5", "b6", "b7", "b8" }));
        lstData.add(new Pair("b2", new String[] {"b3", "b4", "b5", "b6", "b7", "b8" }));
        lstData.add(new Pair("b3", new String[] {"b4", "b5", "b6", "b7", "b8" }));
        lstData.add(new Pair("b4", new String[] {"b5", "b6", "b7", "b8" }));
        lstData.add(new Pair("b5", new String[] {"b6", "b7", "b8" }));
        lstData.add(new Pair("b6", new String[] {"b7", "b8" }));
        lstData.add(new Pair("b7", new String[] {"b8" }));
        lstData.add(new Pair("b8", new String[] {}));

        lstData.add(new Pair("c1", new String[] {"c2", "c3", "c4", "c5", "c6", "c7", "c8" }));
        lstData.add(new Pair("c2", new String[] {"c3", "c4", "c5", "c6", "c7", "c8" }));
        lstData.add(new Pair("c3", new String[] {"c4", "c5", "c6", "c7", "c8" }));
        lstData.add(new Pair("c4", new String[] {"c5", "c6", "c7", "c8" }));
        lstData.add(new Pair("c5", new String[] {"c6", "c7", "c8" }));
        lstData.add(new Pair("c6", new String[] {"c7", "c8" }));
        lstData.add(new Pair("c7", new String[] {"c8" }));
        lstData.add(new Pair("c8", new String[] {}));

        lstData.add(new Pair("d1", new String[] {"d2", "d3", "d4", "d5", "d6", "d7", "d8" }));
        lstData.add(new Pair("d2", new String[] {"d3", "d4", "d5", "d6", "d7", "d8" }));
        lstData.add(new Pair("d3", new String[] {"d4", "d5", "d6", "d7", "d8" }));
        lstData.add(new Pair("d4", new String[] {"d5", "d6", "d7", "d8" }));
        lstData.add(new Pair("d5", new String[] {"d6", "d7", "d8" }));
        lstData.add(new Pair("d6", new String[] {"d7", "d8" }));
        lstData.add(new Pair("d7", new String[] {"d8" }));
        lstData.add(new Pair("d8", new String[] {}));

        lstData.add(new Pair("e1", new String[] {"e2", "e3", "e4", "e9", "e10", "e11", "e12" }));
        lstData.add(new Pair("e2", new String[] {"e3", "e4", "e9", "e10", "e11", "e12" }));
        lstData.add(new Pair("e3", new String[] {"e4", "e9", "e10", "e11", "e12" }));
        lstData.add(new Pair("e4", new String[] {"e9", "e10", "e11", "e12" }));
        lstData.add(new Pair("e9", new String[] {"e10", "e11", "e12" }));
        lstData.add(new Pair("e10", new String[] {"e11", "e12" }));
        lstData.add(new Pair("e11", new String[] {"e12" }));
        lstData.add(new Pair("e12", new String[] {}));

        lstData.add(new Pair("e1", new String[] {"e2", "e3", "e4", "e9", "e10", "e11", "e12" }));
        lstData.add(new Pair("e2", new String[] {"e3", "e4", "e9", "e10", "e11", "e12" }));
        lstData.add(new Pair("e3", new String[] {"e4", "e9", "e10", "e11", "e12" }));
        lstData.add(new Pair("e4", new String[] {"e9", "e10", "e11", "e12" }));
        lstData.add(new Pair("e9", new String[] {"e10", "e11", "e12" }));
        lstData.add(new Pair("e10", new String[] {"e11", "e12" }));
        lstData.add(new Pair("e11", new String[] {"e12" }));
        lstData.add(new Pair("e12", new String[] {}));

        lstData.add(new Pair("f1", new String[] {"f2", "f3", "f4", "f9", "f10", "f11", "f12" }));
        lstData.add(new Pair("f2", new String[] {"f3", "f4", "f9", "f10", "f11", "f12" }));
        lstData.add(new Pair("f3", new String[] {"f4", "f9", "f10", "f11", "f12" }));
        lstData.add(new Pair("f4", new String[] {"f9", "f10", "f11", "f12" }));
        lstData.add(new Pair("f9", new String[] {"f10", "f11", "f12" }));
        lstData.add(new Pair("f10", new String[] {"f11", "f12" }));
        lstData.add(new Pair("f11", new String[] {"f12" }));
        lstData.add(new Pair("f12", new String[] {}));

        lstData.add(new Pair("g1", new String[] {"g2", "g3", "g4", "g9", "g10", "g11", "g12" }));
        lstData.add(new Pair("g2", new String[] {"g3", "g4", "g9", "g10", "g11", "g12" }));
        lstData.add(new Pair("g3", new String[] {"g4", "g9", "g10", "g11", "g12" }));
        lstData.add(new Pair("g4", new String[] {"g9", "g10", "g11", "g12" }));
        lstData.add(new Pair("g9", new String[] {"g10", "g11", "g12" }));
        lstData.add(new Pair("g10", new String[] {"g11", "g12" }));
        lstData.add(new Pair("g11", new String[] {"g12" }));
        lstData.add(new Pair("g12", new String[] {}));

        lstData.add(new Pair("h1", new String[] {"h2", "h3", "h4", "h9", "h10", "h11", "h12" }));
        lstData.add(new Pair("h2", new String[] {"h3", "h4", "h9", "h10", "h11", "h12" }));
        lstData.add(new Pair("h3", new String[] {"h4", "h9", "h10", "h11", "h12" }));
        lstData.add(new Pair("h4", new String[] {"h9", "h10", "h11", "h12" }));
        lstData.add(new Pair("h9", new String[] {"h10", "h11", "h12" }));
        lstData.add(new Pair("h10", new String[] {"h11", "h12" }));
        lstData.add(new Pair("h11", new String[] {"h12" }));
        lstData.add(new Pair("h12", new String[] {}));

        lstData.add(new Pair("i5", new String[] {"i12", "i11", "i10", "i9", "i6", "i7", "i8" }));
        lstData.add(new Pair("i6", new String[] {"i7", "i8" }));
        lstData.add(new Pair("i7", new String[] {"i8" }));
        lstData.add(new Pair("i8", new String[] {}));
        lstData.add(new Pair("i9", new String[] {"i12", "i11", "i10" }));
        lstData.add(new Pair("i10", new String[] {"i12", "i11" }));
        lstData.add(new Pair("i11", new String[] {"i12" }));
        lstData.add(new Pair("i12", new String[] {}));

        lstData.add(new Pair("j5", new String[] {"j12", "j11", "j10", "j9", "j6", "j7", "j8" }));
        lstData.add(new Pair("j6", new String[] {"j7", "j8" }));
        lstData.add(new Pair("j7", new String[] {"j8" }));
        lstData.add(new Pair("j8", new String[] {}));
        lstData.add(new Pair("j9", new String[] {"j12", "j11", "j10" }));
        lstData.add(new Pair("j10", new String[] {"j12", "j11" }));
        lstData.add(new Pair("j11", new String[] {"j12" }));
        lstData.add(new Pair("j12", new String[] {}));

        lstData.add(new Pair("k5", new String[] {"k12", "k11", "k10", "k9", "k6", "k7", "k8" }));
        lstData.add(new Pair("k6", new String[] {"k7", "k8" }));
        lstData.add(new Pair("k7", new String[] {"k8" }));
        lstData.add(new Pair("k8", new String[] {}));
        lstData.add(new Pair("k9", new String[] {"k12", "k11", "k10" }));
        lstData.add(new Pair("k10", new String[] {"k12", "k11" }));
        lstData.add(new Pair("k11", new String[] {"k12" }));
        lstData.add(new Pair("k12", new String[] {}));

        lstData.add(new Pair("l5", new String[] {"l12", "l11", "l10", "l9", "l6", "l7", "l8" }));
        lstData.add(new Pair("l6", new String[] {"l7", "l8" }));
        lstData.add(new Pair("l7", new String[] {"l8" }));
        lstData.add(new Pair("l8", new String[] {}));
        lstData.add(new Pair("l9", new String[] {"l12", "l11", "l10" }));
        lstData.add(new Pair("l10", new String[] {"l12", "l11" }));
        lstData.add(new Pair("l11", new String[] {"l12" }));
        lstData.add(new Pair("l12", new String[] {}));

		return lstData;
	}

	static List<Pair<String, String[]>> getDataForDownwardPositions() {
		List<Pair<String, String[]>> lstData = new LinkedList<Pair<String, String[]>>();
		
		lstData.add(new Pair("a8", new String[]{"a7", "a6", "a5", "a4", "a3", "a2", "a1"}));
		lstData.add(new Pair("a7", new String[]{"a6", "a5", "a4", "a3", "a2", "a1"}));
		lstData.add(new Pair("a6", new String[]{"a5", "a4", "a3", "a2", "a1"}));
		lstData.add(new Pair("a5", new String[]{"a4", "a3", "a2", "a1"}));
		lstData.add(new Pair("a4", new String[]{"a3", "a2", "a1"}));
		lstData.add(new Pair("a3", new String[]{"a2", "a1"}));
		lstData.add(new Pair("a2", new String[]{"a1"}));
		lstData.add(new Pair("a1", new String[]{}));
		
        lstData.add(new Pair("b8", new String[] {"b7", "b6", "b5", "b4", "b3", "b2", "b1" }));
        lstData.add(new Pair("b7", new String[] {"b6", "b5", "b4", "b3", "b2", "b1" }));
        lstData.add(new Pair("b6", new String[] {"b5", "b4", "b3", "b2", "b1" }));
        lstData.add(new Pair("b5", new String[] {"b4", "b3", "b2", "b1" }));
        lstData.add(new Pair("b4", new String[] {"b3", "b2", "b1" }));
        lstData.add(new Pair("b3", new String[] {"b2", "b1" }));
        lstData.add(new Pair("b2", new String[] {"b1" }));
        lstData.add(new Pair("b1", new String[] {}));

        lstData.add(new Pair("c8", new String[] {"c7", "c6", "c5", "c4", "c3", "c2", "c1" }));
        lstData.add(new Pair("c7", new String[] {"c6", "c5", "c4", "c3", "c2", "c1" }));
        lstData.add(new Pair("c6", new String[] {"c5", "c4", "c3", "c2", "c1" }));
        lstData.add(new Pair("c5", new String[] {"c4", "c3", "c2", "c1" }));
        lstData.add(new Pair("c4", new String[] {"c3", "c2", "c1" }));
        lstData.add(new Pair("c3", new String[] {"c2", "c1" }));
        lstData.add(new Pair("c2", new String[] {"c1" }));
        lstData.add(new Pair("c1", new String[] {}));

        lstData.add(new Pair("d8", new String[] {"d7", "d6", "d5", "d4", "d3", "d2", "d1" }));
        lstData.add(new Pair("d7", new String[] {"d6", "d5", "d4", "d3", "d2", "d1" }));
        lstData.add(new Pair("d6", new String[] {"d5", "d4", "d3", "d2", "d1" }));
        lstData.add(new Pair("d5", new String[] {"d4", "d3", "d2", "d1" }));
        lstData.add(new Pair("d4", new String[] {"d3", "d2", "d1" }));
        lstData.add(new Pair("d3", new String[] {"d2", "d1" }));
        lstData.add(new Pair("d2", new String[] {"d1" }));
        lstData.add(new Pair("d1", new String[] {}));

        lstData.add(new Pair("e12", new String[] {"e11", "e10", "e9", "e4", "e3", "e2", "e1" }));
        lstData.add(new Pair("e11", new String[] {"e10", "e9", "e4", "e3", "e2", "e1" }));
        lstData.add(new Pair("e10", new String[] {"e9", "e4", "e3", "e2", "e1" }));
        lstData.add(new Pair("e9", new String[] {"e4", "e3", "e2", "e1" }));
        lstData.add(new Pair("e4", new String[] {"e3", "e2", "e1" }));
        lstData.add(new Pair("e3", new String[] {"e2", "e1" }));
        lstData.add(new Pair("e2", new String[] {"e1" }));
        lstData.add(new Pair("e1", new String[] {}));

        lstData.add(new Pair("f12", new String[] {"f11", "f10", "f9", "f4", "f3", "f2", "f1" }));
        lstData.add(new Pair("f11", new String[] {"f10", "f9", "f4", "f3", "f2", "f1" }));
        lstData.add(new Pair("f10", new String[] {"f9", "f4", "f3", "f2", "f1" }));
        lstData.add(new Pair("f9", new String[] {"f4", "f3", "f2", "f1" }));
        lstData.add(new Pair("f4", new String[] {"f3", "f2", "f1" }));
        lstData.add(new Pair("f3", new String[] {"f2", "f1" }));
        lstData.add(new Pair("f2", new String[] {"f1" }));
        lstData.add(new Pair("f1", new String[] {}));

        lstData.add(new Pair("g12", new String[] {"g11", "g10", "g9", "g4", "g3", "g2", "g1" }));
        lstData.add(new Pair("g11", new String[] {"g10", "g9", "g4", "g3", "g2", "g1" }));
        lstData.add(new Pair("g10", new String[] {"g9", "g4", "g3", "g2", "g1" }));
        lstData.add(new Pair("g9", new String[] {"g4", "g3", "g2", "g1" }));
        lstData.add(new Pair("g4", new String[] {"g3", "g2", "g1" }));
        lstData.add(new Pair("g3", new String[] {"g2", "g1" }));
        lstData.add(new Pair("g2", new String[] {"g1" }));
        lstData.add(new Pair("g1", new String[] {}));

        lstData.add(new Pair("h12", new String[] {"h11", "h10", "h9", "h4", "h3", "h2", "h1" }));
        lstData.add(new Pair("h11", new String[] {"h10", "h9", "h4", "h3", "h2", "h1" }));
        lstData.add(new Pair("h10", new String[] {"h9", "h4", "h3", "h2", "h1" }));
        lstData.add(new Pair("h9", new String[] {"h4", "h3", "h2", "h1" }));
        lstData.add(new Pair("h4", new String[] {"h3", "h2", "h1" }));
        lstData.add(new Pair("h3", new String[] {"h2", "h1" }));
        lstData.add(new Pair("h2", new String[] {"h1" }));
        lstData.add(new Pair("h1", new String[] {}));

        // TODO: add above downward conditions for position have file 'i','j','k','l' as well.
        
		return lstData;
	}

	static List<Pair<String, String[]>> getDataForRightPositions() {
		List<Pair<String, String[]>> lstData = new LinkedList<Pair<String, String[]>>();
		
        lstData.add(new Pair("a1", new String[] {"b1", "c1", "d1", "e1", "f1", "g1", "h1" }));
        lstData.add(new Pair("b1", new String[] {"c1", "d1", "e1", "f1", "g1", "h1" }));
        lstData.add(new Pair("c1", new String[] {"d1", "e1", "f1", "g1", "h1" }));
        lstData.add(new Pair("d1", new String[] {"e1", "f1", "g1", "h1" }));
        lstData.add(new Pair("e1", new String[] {"f1", "g1", "h1" }));
        lstData.add(new Pair("f1", new String[] {"g1", "h1" }));
        lstData.add(new Pair("g1", new String[] {"h1" }));
        lstData.add(new Pair("h1", new String[] {}));

        lstData.add(new Pair("a2", new String[] {"b2", "c2", "d2", "e2", "f2", "g2", "h2" }));
        lstData.add(new Pair("b2", new String[] {"c2", "d2", "e2", "f2", "g2", "h2" }));
        lstData.add(new Pair("c2", new String[] {"d2", "e2", "f2", "g2", "h2" }));
        lstData.add(new Pair("d2", new String[] {"e2", "f2", "g2", "h2" }));
        lstData.add(new Pair("e2", new String[] {"f2", "g2", "h2" }));
        lstData.add(new Pair("f2", new String[] {"g2", "h2" }));
        lstData.add(new Pair("g2", new String[] {"h2" }));
        lstData.add(new Pair("h2", new String[] {}));

        lstData.add(new Pair("a3", new String[] {"b3", "c3", "d3", "e3", "f3", "g3", "h3" }));
        lstData.add(new Pair("b3", new String[] {"c3", "d3", "e3", "f3", "g3", "h3" }));
        lstData.add(new Pair("c3", new String[] {"d3", "e3", "f3", "g3", "h3" }));
        lstData.add(new Pair("d3", new String[] {"e3", "f3", "g3", "h3" }));
        lstData.add(new Pair("e3", new String[] {"f3", "g3", "h3" }));
        lstData.add(new Pair("f3", new String[] {"g3", "h3" }));
        lstData.add(new Pair("g3", new String[] {"h3" }));
        lstData.add(new Pair("h3", new String[] {}));

        lstData.add(new Pair("a4", new String[] {"b4", "c4", "d4", "e4", "f4", "g4", "h4" }));
        lstData.add(new Pair("b4", new String[] {"c4", "d4", "e4", "f4", "g4", "h4" }));
        lstData.add(new Pair("c4", new String[] {"d4", "e4", "f4", "g4", "h4" }));
        lstData.add(new Pair("d4", new String[] {"e4", "f4", "g4", "h4" }));
        lstData.add(new Pair("e4", new String[] {"f4", "g4", "h4" }));
        lstData.add(new Pair("f4", new String[] {"g4", "h4" }));
        lstData.add(new Pair("g4", new String[] {"h4" }));
        lstData.add(new Pair("h4", new String[] {}));

        lstData.add(new Pair("a5", new String[] {"b5", "c5", "d5", "i5", "j5", "k5", "l5" }));
        lstData.add(new Pair("b5", new String[] {"c5", "d5", "i5", "j5", "k5", "l5" }));
        lstData.add(new Pair("c5", new String[] {"d5", "i5", "j5", "k5", "l5" }));
        lstData.add(new Pair("d5", new String[] {"i5", "j5", "k5", "l5" }));
        lstData.add(new Pair("i5", new String[] {"j5", "k5", "l5" }));
        lstData.add(new Pair("j5", new String[] {"k5", "l5" }));
        lstData.add(new Pair("k5", new String[] {"l5" }));
        lstData.add(new Pair("l5", new String[] {}));

        lstData.add(new Pair("a6", new String[] {"b6", "c6", "d6", "i6", "j6", "k6", "l6" }));
        lstData.add(new Pair("b6", new String[] {"c6", "d6", "i6", "j6", "k6", "l6" }));
        lstData.add(new Pair("c6", new String[] {"d6", "i6", "j6", "k6", "l6" }));
        lstData.add(new Pair("d6", new String[] {"i6", "j6", "k6", "l6" }));
        lstData.add(new Pair("i6", new String[] {"j6", "k6", "l6" }));
        lstData.add(new Pair("j6", new String[] {"k6", "l6" }));
        lstData.add(new Pair("k6", new String[] {"l6" }));
        lstData.add(new Pair("l6", new String[] {}));

        lstData.add(new Pair("a7", new String[] {"b7", "c7", "d7", "i7", "j7", "k7", "l7" }));
        lstData.add(new Pair("b7", new String[] {"c7", "d7", "i7", "j7", "k7", "l7" }));
        lstData.add(new Pair("c7", new String[] {"d7", "i7", "j7", "k7", "l7" }));
        lstData.add(new Pair("d7", new String[] {"i7", "j7", "k7", "l7" }));
        lstData.add(new Pair("i7", new String[] {"j7", "k7", "l7" }));
        lstData.add(new Pair("j7", new String[] {"k7", "l7" }));
        lstData.add(new Pair("k7", new String[] {"l7" }));
        lstData.add(new Pair("l7", new String[] {}));

        lstData.add(new Pair("a8", new String[] {"b8", "c8", "d8", "i8", "j8", "k8", "l8" }));
        lstData.add(new Pair("b8", new String[] {"c8", "d8", "i8", "j8", "k8", "l8" }));
        lstData.add(new Pair("c8", new String[] {"d8", "i8", "j8", "k8", "l8" }));
        lstData.add(new Pair("d8", new String[] {"i8", "j8", "k8", "l8" }));
        lstData.add(new Pair("i8", new String[] {"j8", "k8", "l8" }));
        lstData.add(new Pair("j8", new String[] {"k8", "l8" }));
        lstData.add(new Pair("k8", new String[] {"l8" }));
        lstData.add(new Pair("l8", new String[] {}));

        // TODO: add above units for e, f, g, and h.
        
		return lstData;
	}

	static List<Pair<String, String[]>> getDataForLeftPositions() {
		List<Pair<String, String[]>> lstData = new LinkedList<Pair<String, String[]>>();
		
        lstData.add(new Pair("h1", new String[] {"a1", "b1", "c1", "d1", "e1", "f1", "g1" }));
        lstData.add(new Pair("g1", new String[] {"a1", "b1", "c1", "d1", "e1", "f1" }));
        lstData.add(new Pair("f1", new String[] {"a1", "b1", "c1", "d1", "e1" }));
        lstData.add(new Pair("e1", new String[] {"a1", "b1", "c1", "d1" }));
        lstData.add(new Pair("d1", new String[] {"a1", "b1", "c1" }));
        lstData.add(new Pair("c1", new String[] {"a1", "b1" }));
        lstData.add(new Pair("b1", new String[] {"a1" }));
        lstData.add(new Pair("a1", new String[] {}));

        lstData.add(new Pair("h2", new String[] {"a2", "b2", "c2", "d2", "e2", "f2", "g2" }));
        lstData.add(new Pair("g2", new String[] {"a2", "b2", "c2", "d2", "e2", "f2" }));
        lstData.add(new Pair("f2", new String[] {"a2", "b2", "c2", "d2", "e2" }));
        lstData.add(new Pair("e2", new String[] {"a2", "b2", "c2", "d2" }));
        lstData.add(new Pair("d2", new String[] {"a2", "b2", "c2" }));
        lstData.add(new Pair("c2", new String[] {"a2", "b2" }));
        lstData.add(new Pair("b2", new String[] {"a2" }));
        lstData.add(new Pair("a2", new String[] {}));

        lstData.add(new Pair("h3", new String[] {"a3", "b3", "c3", "d3", "e3", "f3", "g3" }));
        lstData.add(new Pair("g3", new String[] {"a3", "b3", "c3", "d3", "e3", "f3" }));
        lstData.add(new Pair("f3", new String[] {"a3", "b3", "c3", "d3", "e3" }));
        lstData.add(new Pair("e3", new String[] {"a3", "b3", "c3", "d3" }));
        lstData.add(new Pair("d3", new String[] {"a3", "b3", "c3" }));
        lstData.add(new Pair("c3", new String[] {"a3", "b3" }));
        lstData.add(new Pair("b3", new String[] {"a3" }));
        lstData.add(new Pair("a3", new String[] {}));

        lstData.add(new Pair("h4", new String[] {"a4", "b4", "c4", "d4", "e4", "f4", "g4" }));
        lstData.add(new Pair("g4", new String[] {"a4", "b4", "c4", "d4", "e4", "f4" }));
        lstData.add(new Pair("f4", new String[] {"a4", "b4", "c4", "d4", "e4" }));
        lstData.add(new Pair("e4", new String[] {"a4", "b4", "c4", "d4" }));
        lstData.add(new Pair("d4", new String[] {"a4", "b4", "c4" }));
        lstData.add(new Pair("c4", new String[] {"a4", "b4" }));
        lstData.add(new Pair("b4", new String[] {"a4" }));
        lstData.add(new Pair("a4", new String[] {}));

        // TODO: add above units for remainig cases.
        
		return lstData;
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		IAppLogger oLogger = new AppLogger();
		ICacheManager oCacheManager = new CacheManager(oLogger);
		
		oCacheManager.loadBoardFromFile("DefaultRuleProcessorTest", "3PlayerBoard.xml");
		m_oBoard = oCacheManager.getBoard("DefaultRuleProcessorTest");

		for (Map.Entry<String, IPositionAgent> entry : m_oBoard.getAllPositionAgents().entrySet()) {
    		IPositionAgent oPosition = entry.getValue();
    		if (oPosition.getPiece() != null) {
    			oPosition.setPiece(null);
    		}
    	}

		m_oRuleProcessor = new DefaultRuleProcessor(oLogger);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_UpwardMovements() {
		List<Pair<String, String[]>> lstData = getDataForUpwardPositions();

		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_NORTH_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(9999);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		Iterator<Pair<String, String[]>> it = lstData.iterator();
		while (it.hasNext()) {
			Pair<String, String[]> oPair = it.next();
			
			IPositionAgent oPosition = (IPositionAgent)m_oBoard.getPosition(oPair.getValue0());
			IPlayerAgent oPlayer = (IPlayerAgent)m_oBoard.getPlayer("P1");
			
			IPieceAgent oPiece = (IPieceAgent)m_oBoard.getUnlinkedPiece("PawnWhite");
			
			Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
    		qData.add(new RuleProcessorData(oRule, oPosition, null));

			Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
			m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oPiece, oPosition, oPlayer, qData, mpCandidatePositions);

			assertEquals(mpCandidatePositions.size(), oPair.getValue1().length);

			for (String stPositionId : oPair.getValue1()) {
				IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
				IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
				
				assertEquals(oExpectedPositionInMap, oActualPositionInMap);
			}
		}
	}

	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_DownwardMovements() {
		List<Pair<String, String[]>> lstData = getDataForDownwardPositions();

		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_SOUTH_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(9999);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.BACKWARD);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		Iterator<Pair<String, String[]>> it = lstData.iterator();
		while (it.hasNext()) {
			Pair<String, String[]> oPair = it.next();
			
			IPositionAgent oPosition = (IPositionAgent)m_oBoard.getPosition(oPair.getValue0());
			IPlayerAgent oPlayer = (IPlayerAgent)m_oBoard.getPlayer("P1");
			
			IPieceAgent oPiece = (IPieceAgent)m_oBoard.getUnlinkedPiece("PawnWhite");
			
			Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
    		qData.add(new RuleProcessorData(oRule, oPosition, null));

			Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
			m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oPiece, oPosition, oPlayer, qData, mpCandidatePositions);

			assertEquals(mpCandidatePositions.size(), oPair.getValue1().length);

			for (String stPositionId : oPair.getValue1()) {
				IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
				IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
				
				assertEquals(oExpectedPositionInMap, oActualPositionInMap);
			}
		}
	}

	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_RightMovements() {
		List<Pair<String, String[]>> lstData = getDataForRightPositions();

		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_EAST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(9999);
		oRule.getRuleData().setFile(File.FORWARD);
		oRule.getRuleData().setRank(Rank.SAME);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		Iterator<Pair<String, String[]>> it = lstData.iterator();
		while (it.hasNext()) {
			Pair<String, String[]> oPair = it.next();
			
			IPositionAgent oPosition = (IPositionAgent)m_oBoard.getPosition(oPair.getValue0());
			IPlayerAgent oPlayer = (IPlayerAgent)m_oBoard.getPlayer("P1");
			
			IPieceAgent oPiece = (IPieceAgent)m_oBoard.getUnlinkedPiece("PawnWhite");
			
			Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
    		qData.add(new RuleProcessorData(oRule, oPosition, null));

			Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
			m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oPiece, oPosition, oPlayer, qData, mpCandidatePositions);

			assertEquals(mpCandidatePositions.size(), oPair.getValue1().length);

			for (String stPositionId : oPair.getValue1()) {
				IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
				IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
				
				assertEquals(oExpectedPositionInMap, oActualPositionInMap);
			}
		}
	}

	@Test
	void testTryFindCandidateMovesForFileAndRankStrategy_LeftMovements() {
		List<Pair<String, String[]>> lstData = getDataForLeftPositions();

		IRuleAgent oRule = (IRuleAgent)m_oBoardFactory.createRule();
		oRule.getRuleData().setName("MOVE_EAST_BY_*");
		oRule.getRuleData().setCustomName("");
		oRule.getRuleData().setRuleType(RuleType.MOVE);		
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.FILE_AND_RANK);
		oRule.getRuleData().setMaxRecurrenceCount(9999);
		oRule.getRuleData().setFile(File.BACKWARD);
		oRule.getRuleData().setRank(Rank.SAME);
		oRule.getRuleData().setFamily(Family.DIFFERENT);
		oRule.getRuleData().setLifespan(Integer.MAX_VALUE);

		Iterator<Pair<String, String[]>> it = lstData.iterator();
		while (it.hasNext()) {
			Pair<String, String[]> oPair = it.next();
			
			IPositionAgent oPosition = (IPositionAgent)m_oBoard.getPosition(oPair.getValue0());
			IPlayerAgent oPlayer = (IPlayerAgent)m_oBoard.getPlayer("P1");
			
			IPieceAgent oPiece = (IPieceAgent)m_oBoard.getUnlinkedPiece("PawnWhite");
			
			Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
    		qData.add(new RuleProcessorData(oRule, oPosition, null));

			Map<String, IMoveCandidate> mpCandidatePositions = new HashMap<String, IMoveCandidate>();
			m_oRuleProcessor.tryFindPossibleCandidateMovePositions(oPiece, oPosition, oPlayer, qData, mpCandidatePositions);

			assertEquals(mpCandidatePositions.size(), oPair.getValue1().length);

			for (String stPositionId : oPair.getValue1()) {
				IPositionAgent oExpectedPositionInMap = m_oBoard.getPositionAgent(stPositionId);
				IPositionAgent oActualPositionInMap = mpCandidatePositions.get(stPositionId).getCandidatePosition();
				
				assertEquals(oExpectedPositionInMap, oActualPositionInMap);
			}
		}
	}
}
