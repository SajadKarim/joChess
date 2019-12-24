package jchess.ruleengine;

import java.util.Map;

import org.javatuples.Pair;

import com.google.inject.Inject;

import jchess.common.IBoardAgent;
import jchess.common.IMove;
import jchess.common.IMoveCandidacy;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;
import jchess.gamelogic.Move;
import jchess.gui.IGUIHandle;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

public class ExtendedRuleEngine extends DefaultRuleEngine {	
	@Inject
	public ExtendedRuleEngine(IRuleProcessor oRuleProcessor, IGUIHandle oGUIHandler, IAppLogger oLogger) {
		super(oRuleProcessor, oGUIHandler, oLogger);
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating ExtendedRuleEngine.", "ExtendedRuleEngine", "ExtendedRuleEngine");
	}

	public Map<String, IMoveCandidacy> tryEvaluateAllRules(IBoardAgent oBoard, IPositionAgent oSelectedPosition) {	
		m_oLogger.writeLog(LogLevel.INFO, String.format("Evaluating rules attaches to the Position [%s].", oSelectedPosition.toLog()), "tryEvaluateAllRules", "ExtendedRuleEngine");

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
		m_oLogger.writeLog(LogLevel.INFO, "Finalizing selected move candidate.", "tryExecuteRule", "ExtendedRuleEngine");

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
		m_oLogger.writeLog(LogLevel.INFO, "Finalizing selected custom move candidate.", "tryExecuteCustomRules", "ExtendedRuleEngine");

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
