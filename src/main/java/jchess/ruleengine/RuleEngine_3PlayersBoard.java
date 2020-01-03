package jchess.ruleengine;

import java.util.Map;

import com.google.inject.Inject;

import jchess.common.IBoardActivity;
import jchess.common.IBoardAgent;
import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;
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
	public Map<String, IMoveCandidate> tryEvaluateAllRules(IBoardAgent oBoard, IPieceAgent oPiece) {		
		Map<String, IMoveCandidate> mpCandidateMovePositions = super.tryEvaluateAllRules(oBoard, oPiece);
		
		tryMakePiecePeculiarMoves(oBoard, oPiece, mpCandidateMovePositions);
		
		return mpCandidateMovePositions;
	}
	@Override
	public IBoardActivity tryExecuteRule(IBoardAgent oBoard, IMoveCandidate oDestinationPositionAndRule) {
		return super.tryExecuteRule(oBoard, oDestinationPositionAndRule);
	}
	
	private void tryMakePiecePeculiarMoves(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidateMovePositions) {
	}
}
