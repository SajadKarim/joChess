package jchess.ruleengine;

import java.util.Map;

import org.javatuples.Pair;

import jchess.common.IBoardAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;

public class ExtendedRuleEngine extends DefaultRuleEngine {
	
	public ExtendedRuleEngine(IRuleProcessor oRuleProcessor) {
		super(oRuleProcessor);
	}

	public Map<String,Pair<IPositionAgent, IRuleAgent>> tryEvaluateAllRules(IBoardAgent oBoard, IPositionAgent oPosition) {	
		Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidateMovePositions = super.tryEvaluateAllRules(oBoard, oPosition);
		
		switch(oPosition.getPiece().getName()) {
		case "PawnWhite":
		case "PawnBlack":
		case "PawnRed": {
			PawnRulesProcessor.tryPawnPromotionRule(oBoard, oPosition, mpCandidateMovePositions);

			PawnRulesProcessor.tryPawnFirstMoveException(m_oRuleProcessor, oBoard, oPosition, mpCandidateMovePositions);
		}
			break;
		default:
			break;
		}
		
		return mpCandidateMovePositions;
	}

	public void tryExecuteRule(IBoardAgent oBoard, IPositionAgent oSourcePosition, Pair<IPositionAgent, IRuleAgent> oDestinationPositionAndRule) {
		super.tryExecuteRule(oBoard, oSourcePosition, oDestinationPositionAndRule);
		switch( oDestinationPositionAndRule.getValue1().getRuleType()) {
			case CUSTOM: {
				tryExecuteCustomRules(oBoard, oSourcePosition, oDestinationPositionAndRule);
			}
			default:
				break;
		}
	}

	private void tryExecuteCustomRules(IBoardAgent oBoard, IPositionAgent oSourcePosition, Pair<IPositionAgent, IRuleAgent> oDestinationPositionAndRule) {
		switch( oDestinationPositionAndRule.getValue1().getCustomName()) {
		case "PAWN_MOVE_PROMOTION":
		case "PAWN_CAPTURE_PROMOTION":{
			PawnRulesProcessor.tryExecutePawnPromotionRule(oBoard, m_oGUIHandler, oSourcePosition, oDestinationPositionAndRule);
		}
			break;
		case "MOVE[PAWN_FIRST_MOVE_EXCEPTION]":
			PawnRulesProcessor.tryExecutePawnFirstMoveException(oSourcePosition, oDestinationPositionAndRule);
			break;
		default:
			break;
		}
	}

}
