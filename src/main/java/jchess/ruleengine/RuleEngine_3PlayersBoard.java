package jchess.ruleengine;

import java.util.Map;

import org.javatuples.Pair;

import com.google.inject.Inject;

import jchess.common.IBoardAgent;
import jchess.common.IMove;
import jchess.common.IMoveCandidacy;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;
import jchess.gui.IGUIHandle;
import jchess.util.IAppLogger;

/**
 * Extended logic to execute a Rule for 3 Player board.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

class RuleEngine_3PlayersBoard extends ExtendedRuleEngine {	
	@Inject
	public RuleEngine_3PlayersBoard(IRuleProcessor oRuleProcessor, IGUIHandle oGUIHandler, IAppLogger oLogger) {
		super(oRuleProcessor, oGUIHandler, oLogger);
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
