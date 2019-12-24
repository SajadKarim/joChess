package jchess.ruleengine;

import java.util.Map;

import org.javatuples.Pair;

import jchess.common.IBoardAgent;
import jchess.common.IMove;
import jchess.common.IMoveCandidacy;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;

/**
 * Extended logic to execute a Rule for 3 Player board.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

class RuleEngine_3PlayersBoard extends ExtendedRuleEngine {
	
	public RuleEngine_3PlayersBoard(IRuleProcessor oRuleProcessor) {
		super(oRuleProcessor);
	}
	
	@Override
	public Map<String, IMoveCandidacy> tryEvaluateAllRules(IBoardAgent oBoard, IPositionAgent oPosition) {		
		Map<String, IMoveCandidacy> mpCandidateMovePositions = super.tryEvaluateAllRules(oBoard, oPosition);
		
		tryMakePiecePeculiarMoves(oBoard, oPosition, mpCandidateMovePositions);
		
		return mpCandidateMovePositions;
	}
	@Override
	public IMove tryExecuteRule(IBoardAgent oBoard, IMoveCandidacy oDestinationPositionAndRule) {
		return super.tryExecuteRule(oBoard, oDestinationPositionAndRule);
	}
	
	private void tryMakePiecePeculiarMoves(IBoardAgent oBoard, IPositionAgent oPosition, Map<String, IMoveCandidacy> mpCandidateMovePositions) {
	}
}
