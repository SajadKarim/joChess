package jchess.ruleengine;

import java.util.Map;

import com.google.inject.Inject;

import jchess.common.IBoardAgent;
import jchess.common.IMove;
import jchess.common.IMoveCandidacy;
import jchess.common.IPositionAgent;
import jchess.gui.IGUIHandle;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

/**
 * This class provides functionality to process extended (custom) rules.
 * It evaluates the possible move candidates using the Rules acceptable in XML.
 * It also facilitate in executing the Rules.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class ExtendedRuleEngine extends DefaultRuleEngine {	
	@Inject
	public ExtendedRuleEngine(IRuleProcessor oRuleProcessor, IGUIHandle oGUIHandler, IAppLogger oLogger) {
		super(oRuleProcessor, oGUIHandler, oLogger);
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating ExtendedRuleEngine.", "ExtendedRuleEngine", "ExtendedRuleEngine");
	}

	/**
	 * This method is an extension to one defined in DefaultRuleEngine.
	 * It entertains custom Rules and adds further move candidates in addition to those that DefaultRuleEngine provides.
	 */
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
			m_oLogger.writeLog(LogLevel.ERROR, "Invalid Rule Type.", "tryEvaluateAllRules", "ExtendedRuleEngine");
		}
		
		return mpCandidateMovePositions;
	}

	/**
	 * This method executes custom (user defined) rules.
	 */	
	public IMove tryExecuteRule(IBoardAgent oBoard, IMoveCandidacy oMoveCandidate) {
		m_oLogger.writeLog(LogLevel.INFO, "Finalizing selected move candidate.", "tryExecuteRule", "ExtendedRuleEngine");

		IMove oMove = super.tryExecuteRule(oBoard, oMoveCandidate);
		switch( oMoveCandidate.getRule().getRuleType()) {
			case CUSTOM: {
				oMove = tryExecuteCustomRules(oBoard, oMoveCandidate);
			}
			default:
				m_oLogger.writeLog(LogLevel.ERROR, "Invalid Rule Type.", "tryExecuteRule", "ExtendedRuleEngine");
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
			m_oLogger.writeLog(LogLevel.ERROR, "Invalid Rule Type.", "tryExecuteCustomRules", "ExtendedRuleEngine");
		}
		
		return oMove;
	}

}
