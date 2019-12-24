package jchess.ruleengine;

import java.util.Map;

import org.javatuples.Pair;

import jchess.common.IBoardAgent;
import jchess.common.IMove;
import jchess.common.IMoveCandidacy;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;
import jchess.gamelogic.Move;

public class ExtendedRuleEngine extends DefaultRuleEngine {
	
	public ExtendedRuleEngine(IRuleProcessor oRuleProcessor) {
		super(oRuleProcessor);
	}

	public Map<String, IMoveCandidacy> tryEvaluateAllRules(IBoardAgent oBoard, IPositionAgent oSelectedPosition) {	
		Map<String, IMoveCandidacy> mpCandidateMovePositions = super.tryEvaluateAllRules(oBoard, oSelectedPosition);
		
		switch(oSelectedPosition.getPiece().getName()) {
		case "PawnWhite":
		case "PawnBlack":
		case "PawnRed": {
			PawnRulesProcessor.tryPawnPromotionRule(oBoard, oSelectedPosition, mpCandidateMovePositions);

			PawnRulesProcessor.tryPawnFirstMoveException(m_oRuleProcessor, oBoard, oSelectedPosition, mpCandidateMovePositions);
		}
			break;
		default:
			break;
		}
		
		return mpCandidateMovePositions;
	}

	public IMove tryExecuteRule(IBoardAgent oBoard, IMoveCandidacy oMoveCandidate) {
		IMove oMove = super.tryExecuteRule(oBoard, oMoveCandidate);
		switch( oMoveCandidate.getRule().getRuleType()) {
			case CUSTOM: {
				oMove = tryExecuteCustomRules(oBoard, oMoveCandidate);
			}
			default:
				break;
		}
		
		return oMove;
	}

	private IMove tryExecuteCustomRules(IBoardAgent oBoard, IMoveCandidacy oMoveCandidate) {
		IMove oMove = null;
		 
		switch( oMoveCandidate.getRule().getCustomName()) {
		case "MOVE_AND_CAPTURE[PAWN_PROMOTION]": 
			oMove = PawnRulesProcessor.tryExecutePawnPromotionRule(oBoard, m_oGUIHandler, oMoveCandidate);			
			break;
		case "MOVE[PAWN_FIRST_MOVE_EXCEPTION]":
			oMove = PawnRulesProcessor.tryExecutePawnFirstMoveException(oMoveCandidate);
			break;
		default:
			break;
		}
		
		return oMove;
	}

}
