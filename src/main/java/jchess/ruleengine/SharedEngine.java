package jchess.ruleengine;

import java.util.Map;

import org.javatuples.Pair;

import jchess.common.IBoardAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRule;
import jchess.common.IRuleAgent;
import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

/**
 * 
 * Helper class to make run custom Rules.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

class SharedEngine {
	static void tryPawnFirstMove(IBoardAgent oBoard, IPositionAgent oPosition, Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidateMovePositions) {
		if( oPosition.getPiece().hasPieceAlreadyMadeMove())
			return;
		
		IRule oRule = oBoard.createRule();		
		oRule.getRuleData().setRuleType(RuleType.MOVE_TRANSIENT);
		oRule.getRuleData().setDirection(Direction.EDGE);
		oRule.getRuleData().setMaxRecurrenceCount(1);
		oRule.getRuleData().setFile(File.SAME);
		oRule.getRuleData().setRank(Rank.FORWARD);
		oRule.getRuleData().setFamily(Family.IGNORE);
		oRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oRule.getRuleData().setName("PawnFirstMoveException");
		
		IRule oInnerRule= oBoard.createRule();		
		oInnerRule.getRuleData().setRuleType(RuleType.MOVE);
		oInnerRule.getRuleData().setDirection(Direction.EDGE);
		oInnerRule.getRuleData().setMaxRecurrenceCount(1);
		oInnerRule.getRuleData().setFile(File.SAME);
		oInnerRule.getRuleData().setRank(Rank.FORWARD);
		oInnerRule.getRuleData().setFamily(Family.IGNORE);
		oInnerRule.getRuleData().setManoeuvreStrategy(Manoeuvre.BLINKER);
		oInnerRule.getRuleData().setName("PawnFirstMoveException_InnerRule");
		
		oRule.getRuleData().addRule(oInnerRule);
		((IRuleAgent)oRule).reset();
		
		DefaultRuleEngine oProcessor = new DefaultRuleEngine();
		oProcessor.tryFindPossibleCandidateMovePositions(oPosition.getPiece().getPlayer(), oPosition.getPiece(), oPosition, (IRuleAgent)oRule, mpCandidateMovePositions);
	}
}
