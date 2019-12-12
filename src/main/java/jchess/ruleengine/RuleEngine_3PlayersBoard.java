package jchess.ruleengine;

import java.util.Map;

import org.javatuples.Pair;

import jchess.common.IBoardAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;

/**
 * Extended logic to execute a Rule for 3 Player board.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

class RuleEngine_3PlayersBoard extends DefaultRuleEngine {
	@Override
	public Map<String,Pair<IPositionAgent, IRuleAgent>> tryFindPossibleCandidateMovePositions(IBoardAgent oBoard, IPositionAgent oPosition) {		
		Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidateMovePositions = super.tryFindPossibleCandidateMovePositions(oBoard, oPosition);
		
		tryMakePiecePeculiarMoves(oBoard, oPosition, mpCandidateMovePositions);
		
		return mpCandidateMovePositions;
	}
	@Override
	public void tryMakeMove(IPositionAgent oSourcePosition, Pair<IPositionAgent, IRuleAgent> oDestinationPositionAndRule) {
		super.tryMakeMove(oSourcePosition, oDestinationPositionAndRule);
		
		if( oDestinationPositionAndRule.getValue0().getPiece() != null) {
			if( oDestinationPositionAndRule.getValue0().getPiece().getName().startsWith("Pawn")) {
				if(!oDestinationPositionAndRule.getValue0().getPiece().hasPieceAlreadyMadeMove() ) { 
					oDestinationPositionAndRule.getValue0().getPiece().recordPeiceFirstMove();
				}
			}
		}
	}
	
	private void tryMakePiecePeculiarMoves(IBoardAgent oBoard, IPositionAgent oPosition, Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidateMovePositions) {
		if( oPosition.getPiece() != null) {
			if( oPosition.getPiece().getName().startsWith("Pawn")) {
				if(!oPosition.getPiece().hasPieceAlreadyMadeMove() ) { 
					SharedEngine.tryPawnFirstMove(oBoard, oPosition, mpCandidateMovePositions);
				}
			}
		}
	}
}
